package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.model.vo.InterfaceInfoVO;
import com.yupi.springbootinit.model.vo.UserInterfaceInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author MinJianHe
* @description 针对表【user_interface_info(用户接口关系表)】的数据库操作Service
* @createDate 2024-09-14 23:06:41
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest);

    UserInterfaceInfoVO getUserInterfaceInfoVO(UserInterfaceInfo userInterfaceInfo, HttpServletRequest request);

    Page<UserInterfaceInfoVO> getUserInterfaceInfoVOPage(Page<UserInterfaceInfo> userInterfaceInfoPage, HttpServletRequest request);

}
