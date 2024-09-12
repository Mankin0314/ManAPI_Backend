package com.yupi.springbootinit.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帖子视图
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class InterfaceInfoVO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人信息
     */
    private UserVO user;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 包装类转对象
     *
     * @param InterfaceInfoVO
     * @return
     */
    public static InterfaceInfo voToObj(InterfaceInfoVO InterfaceInfoVO) {
        if (InterfaceInfoVO == null) {
            return null;
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoVO, InterfaceInfo);
//        List<String> tagList = InterfaceInfoVO.getTagList();
//        InterfaceInfo.setTags(JSONUtil.toJsonStr(tagList));
        return InterfaceInfo;
    }

    /**
     * 对象转包装类
     *
     * @param InterfaceInfo
     * @return
     */
    public static InterfaceInfoVO objToVo(InterfaceInfo InterfaceInfo) {
        if (InterfaceInfo == null) {
            return null;
        }
        InterfaceInfoVO InterfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(InterfaceInfo, InterfaceInfoVO);
//        InterfaceInfoVO.setTagList(JSONUtil.toList(InterfaceInfo.getTags(), String.class));
        return InterfaceInfoVO;
    }
}
