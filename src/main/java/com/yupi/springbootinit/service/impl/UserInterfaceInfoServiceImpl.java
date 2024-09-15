package com.yupi.springbootinit.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.mapper.UserInterfaceInfoMapper;
import com.yupi.springbootinit.mapper.UserInterfaceInfoMapper;
import com.yupi.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.model.vo.InterfaceInfoVO;
import com.yupi.springbootinit.model.vo.UserInterfaceInfoVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.UserInterfaceInfoService;
import com.yupi.springbootinit.service.UserInterfaceInfoService;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;

import com.yupi.springbootinit.service.UserService;
import com.yupi.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MinJianHe
 * @description 针对表【user_interface_info(用户接口关系表)】的数据库操作Service实现
 * @createDate 2024-09-14 23:06:41
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Resource
    UserService userService;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = userInterfaceInfo.getId();

        Integer status = userInterfaceInfo.getStatus();
        Long userId = userInterfaceInfo.getUserId();
        Date createTime = userInterfaceInfo.getCreateTime();
        Date updateTime = userInterfaceInfo.getUpdateTime();
        Integer isDelete = userInterfaceInfo.getIsDelete();

        // 创建时，参数不能为空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户/API方法 不存在");
            }
        }

        if (userInterfaceInfo.getLeftnnum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数小于0");
        }
    }

    @Override
    public QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {
        Long id = userInterfaceInfoQueryRequest.getId();
        Integer status = userInterfaceInfoQueryRequest.getStatus();
        Long userId = userInterfaceInfoQueryRequest.getUserId();
        Integer leftnnum = userInterfaceInfoQueryRequest.getLeftnnum();
        int current = userInterfaceInfoQueryRequest.getCurrent();
        Long interfaceInfoId = userInterfaceInfoQueryRequest.getInterfaceInfoId();
        int pageSize = userInterfaceInfoQueryRequest.getPageSize();
        String sortField = userInterfaceInfoQueryRequest.getSortField();
        String sortOrder = userInterfaceInfoQueryRequest.getSortOrder();

        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (userInterfaceInfoQueryRequest == null) {
            return queryWrapper;
        }

//        if (CollUtil.isNotEmpty(tagList)) {
//            for (String tag : tagList) {
//                queryWrapper.like("tags", "\"" + tag + "\"");
//            }
//        }
//        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(interfaceInfoId), "interfaceInfoId", interfaceInfoId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(leftnnum), "leftnnum", leftnnum);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public UserInterfaceInfoVO getUserInterfaceInfoVO(UserInterfaceInfo userInterfaceInfo, HttpServletRequest request) {
        UserInterfaceInfoVO userInterfaceInfoVO = UserInterfaceInfoVO.objToVo(userInterfaceInfo);
        long interfaceInfoId = userInterfaceInfo.getId();
        // 1. 关联查询用户信息
        Long userId = userInterfaceInfo.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        userInterfaceInfoVO.setUser(userVO);

        return userInterfaceInfoVO;

    }

    @Override
    public Page<UserInterfaceInfoVO> getUserInterfaceInfoVOPage(Page<UserInterfaceInfo> userInterfaceInfoPage, HttpServletRequest request) {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoPage.getRecords();
        Page<UserInterfaceInfoVO> userInterfaceInfoVOPage = new Page<>(userInterfaceInfoPage.getCurrent(), userInterfaceInfoPage.getSize(), userInterfaceInfoPage.getTotal());
        if (CollUtil.isEmpty(userInterfaceInfoList)) {
            return userInterfaceInfoVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = userInterfaceInfoList.stream().map(UserInterfaceInfo::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 已登录，获取用户点赞、收藏状态
        Map<Long, Boolean> userInterfaceInfoIdHasThumbMap = new HashMap<>();
        Map<Long, Boolean> userInterfaceInfoIdHasFavourMap = new HashMap<>();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            Set<Long> userInterfaceInfoIdSet = userInterfaceInfoList.stream().map(UserInterfaceInfo::getId).collect(Collectors.toSet());
            loginUser = userService.getLoginUser(request);
        }
        // 填充信息
        List<UserInterfaceInfoVO> userInterfaceInfoVOList = userInterfaceInfoList.stream().map(userInterfaceInfo -> {
            UserInterfaceInfoVO userInterfaceInfoVO = UserInterfaceInfoVO.objToVo(userInterfaceInfo);
            Long userId = userInterfaceInfo.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            userInterfaceInfoVO.setUser(userService.getUserVO(user));
//            userInterfaceInfoVO.setHasThumb(userInterfaceInfoIdHasThumbMap.getOrDefault(userInterfaceInfo.getId(), false));
//            userInterfaceInfoVO.setHasFavour(userInterfaceInfoIdHasFavourMap.getOrDefault(userInterfaceInfo.getId(), false));
            return userInterfaceInfoVO;
        }).collect(Collectors.toList());
        userInterfaceInfoVOPage.setRecords(userInterfaceInfoVOList);
        return userInterfaceInfoVOPage;
    }
}





