package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ChannelAddRequest {
    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;

    @NotBlank
    @Length(min = 1, max = 64)
    private String channelName;
}
