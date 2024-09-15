package com.yupi.springbootinit.model.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 帖子视图
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserInterfaceInfoVO implements Serializable {
    /**
     * 调查用户id
     */
    private Long userId;

    /**
     * 调查接口id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftnnum;

    /**
     * 接口状态0是默认，1是开启
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private UserVO user;
    /**
     * 包装类转对象
     *
     * @param
     * @return
     */
    public static UserInterfaceInfo voToObj(UserInterfaceInfoVO userInterfaceInfoVO) {
        if (userInterfaceInfoVO == null) {
            return null;
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoVO, userInterfaceInfo);
//        List<String> tagList = InterfaceInfoVO.getTagList();
//        InterfaceInfo.setTags(JSONUtil.toJsonStr(tagList));
        return userInterfaceInfo;
    }

    /**
     * 对象转包装类
     *
     * @param
     * @return
     */
    public static UserInterfaceInfoVO objToVo(UserInterfaceInfo userInterfaceInfo) {
        if (userInterfaceInfo == null) {
            return null;
        }
        UserInterfaceInfoVO userInterfaceInfoVO = new UserInterfaceInfoVO();
        BeanUtils.copyProperties(userInterfaceInfo, userInterfaceInfoVO);
//        InterfaceInfoVO.setTagList(JSONUtil.toList(InterfaceInfo.getTags(), String.class));
        return userInterfaceInfoVO;
    }
}
