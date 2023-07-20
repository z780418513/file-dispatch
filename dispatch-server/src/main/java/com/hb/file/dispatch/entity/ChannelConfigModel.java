package com.hb.file.dispatch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("channel_config")
public class ChannelConfigModel extends BaseModel {
    private String channel;
    private String channelName;
    private Boolean enable;
}
