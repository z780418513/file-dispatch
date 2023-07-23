package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class DispatchTaskAddRequest {
    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;

    /**
     * 输入渠道
     */
    @NotBlank
    @Length(min = 1, max = 64)
    private String inputPlatformId;
    /**
     * 输出渠道
     */
    @NotBlank
    @Length(min = 1, max = 64)
    private String outputPlatformId;

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
