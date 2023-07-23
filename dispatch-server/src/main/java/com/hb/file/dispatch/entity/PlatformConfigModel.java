package com.hb.file.dispatch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "platform_config")
public class PlatformConfigModel extends BaseModel {

    /**
     * 平台id
     */
    private String platformId;
    /**
     * 参数
     */
    private String parameter;
    /**
     * 参数值
     */
    private String value;


}
