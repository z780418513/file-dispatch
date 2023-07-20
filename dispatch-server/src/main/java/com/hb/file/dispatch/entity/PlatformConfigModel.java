package com.hb.file.dispatch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "platform_config")
public class PlatformConfigModel extends BaseModel {

    private String channel;
    private Integer platform;
    private String parameter;
    private String value;


}
