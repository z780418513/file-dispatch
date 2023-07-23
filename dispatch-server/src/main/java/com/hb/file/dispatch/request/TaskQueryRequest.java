package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskQueryRequest {
    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;
    @NotNull
    @Range(max = 100)
    private Integer status;
    @NotNull
    @Range(max = 100)
    private Integer current;
    @NotNull
    @Range(max = 100)
    private Integer pageSize;
}
