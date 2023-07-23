package com.hb.file.dispatch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("channel_platform_rel")
public class ChannelPlatformRelModel extends BaseModel {
    /**
     * 渠道
     */
    private String channel;

    /**
     * 平台
     */
    private Integer platform;

    /**
     * 平台名
     */
    private String platformName;

    /**
     * 平台id
     */
    private String platformId;

}
