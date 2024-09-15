package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.mankin.client_sdk.client.OpenAPIClient;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.*;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.interfaceinfo.*;
import com.yupi.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.yupi.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoEditRequest;
import com.yupi.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.UserInterfaceInfoVO;
import com.yupi.springbootinit.service.UserInterfaceInfoService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/userInterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private OpenAPIClient openAPIClient;
    // region 增删改查

    /**
     * 创建
     *
     * @param userInterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest, HttpServletRequest request) {
        if (userInterfaceInfoAddRequest == null) {

            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
//        List<String> tags = userInterfaceInfoAddRequest.getTags();
//        if (tags != null) {
//            userInterfaceInfo.setTags(JSONUtil.toJsonStr(tags));
//        }
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        userInterfaceInfo.setUserId(loginUser.getId());
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newUserInterfaceInfoId = userInterfaceInfo.getId();
        return ResultUtils.success(newUserInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        System.out.println(userService.isAdmin(request));
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userInterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param userInterfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest) {
        if (userInterfaceInfoUpdateRequest == null || userInterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoUpdateRequest, userInterfaceInfo);
//        List<String> tags = userInterfaceInfoUpdateRequest.getTags();
//        if (tags != null) {
//            userInterfaceInfo.setTags(JSONUtil.toJsonStr(tags));
//        }
        // 参数校验
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, false);
        long id = userInterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserInterfaceInfoVO> getUserInterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(userInterfaceInfoService.getUserInterfaceInfoVO(userInterfaceInfo, request));
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param userInterfaceInfoQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceInfoByPage(@RequestBody UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {
        long current = userInterfaceInfoQueryRequest.getCurrent();
        long size = userInterfaceInfoQueryRequest.getPageSize();
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size),
                userInterfaceInfoService.getQueryWrapper(userInterfaceInfoQueryRequest));
        return ResultUtils.success(userInterfaceInfoPage);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param userInterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<UserInterfaceInfoVO>> listUserInterfaceInfoVOByPage(@RequestBody UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest,
                                                                         HttpServletRequest request) {
        long current = userInterfaceInfoQueryRequest.getCurrent();
        long size = userInterfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size),
                userInterfaceInfoService.getQueryWrapper(userInterfaceInfoQueryRequest));
        return ResultUtils.success(userInterfaceInfoService.getUserInterfaceInfoVOPage(userInterfaceInfoPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param userInterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<UserInterfaceInfoVO>> listMyUserInterfaceInfoVOByPage(@RequestBody UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest,
                                                                           HttpServletRequest request) {
        if (userInterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        userInterfaceInfoQueryRequest.setUserId(loginUser.getId());
        long current = userInterfaceInfoQueryRequest.getCurrent();
        long size = userInterfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size),
                userInterfaceInfoService.getQueryWrapper(userInterfaceInfoQueryRequest));
        return ResultUtils.success(userInterfaceInfoService.getUserInterfaceInfoVOPage(userInterfaceInfoPage, request));
    }

    // endregion

    /**
     * 分页搜索（从 ES 查询，封装类）
     *
     * @param userInterfaceInfoQueryRequest
     * @param request
     * @return
     */
/*    @PostMapping("/search/page/vo")
    public BaseResponse<Page<UserInterfaceInfoVO>> searchUserInterfaceInfoVOByPage(@RequestBody UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest,
                                                                           HttpServletRequest request) {
        long size = userInterfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.searchFromEs(userInterfaceInfoQueryRequest);
        return ResultUtils.success(userInterfaceInfoService.getUserInterfaceInfoVOPage(userInterfaceInfoPage, request));
    }*/

    /**
     * 编辑（用户）
     *
     * @param userInterfaceInfoEditRequest
     * @param request
     * @return
     */

    @PostMapping("/edit")
    public BaseResponse<Boolean> editUserInterfaceInfo(@RequestBody UserInterfaceInfoEditRequest userInterfaceInfoEditRequest, HttpServletRequest request) {
        if (userInterfaceInfoEditRequest == null || userInterfaceInfoEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoEditRequest, userInterfaceInfo);
//        List<String> tags = userInterfaceInfoEditRequest.getTags();
//        if (tags != null) {
//            userInterfaceInfo.setTags(JSONUtil.toJsonStr(tags));
//        }
        // 参数校验
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, false);
        User loginUser = userService.getLoginUser(request);
        long id = userInterfaceInfoEditRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldUserInterfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success(result);
    }




}
