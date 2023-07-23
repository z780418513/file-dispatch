package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ChannelSwitchRequest {
    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;

    @NotNull
    private Boolean enable;
}
