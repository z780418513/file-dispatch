package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DispatchTaskAddRequest {
    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;

    /**
     * 输入渠道
     */
    @NotNull
    @Range(min = 1, max = 10)
    private Integer inputPlatform;
    /**
     * 输出渠道
     */
    @NotNull
    @Range(min = 1, max = 10)
    private Integer outputPlatform;

    // 源文件地址
    @NotBlank
    private String sourceUrl;

    /**
     * 源文件md5值
     */
    private String md5;

    // 上传后的文件位置
    @NotBlank
    private String uploadDir;


    // 强制注入
    private boolean forcedInjection;
}
