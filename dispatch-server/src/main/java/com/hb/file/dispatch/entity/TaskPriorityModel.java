package com.hb.file.dispatch.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务优先级表
 * </p> 任务优先级
 * 按priority最高的group组,优先下载 相同priority 按创建时间正序
 *
 * @author hanbaolaoba
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("task_priority")
public class TaskPriorityModel extends BaseModel {

    /**
     * 渠道
     */
    private String channel;
    /**
     * 任务优先级
     */
    private Integer priority;

    /**
     * 分组，可以对应资产ID等其他
     */
    @TableField("`group`")
    private String group;

    /**
     * 分组中的任务顺序
     */
    private Integer groupOrder;

    /**
     * 任务流水号，唯一
     */
    private String streamingNo;

    private Integer status;

}
