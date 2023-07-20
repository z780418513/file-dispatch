package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class DisableChannelRequest {
    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;
}
