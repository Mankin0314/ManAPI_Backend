package com.yupi.springbootinit.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;



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

    @TableLogic
    private Integer isDelete;

}