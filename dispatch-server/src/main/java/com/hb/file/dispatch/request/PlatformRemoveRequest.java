package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PlatformRemoveRequest {

    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;

    @NotNull
    @Range(min = 1, max = 10)
    private Integer platform;
}
