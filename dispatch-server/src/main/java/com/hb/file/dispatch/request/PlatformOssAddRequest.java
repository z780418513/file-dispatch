package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PlatformOssAddRequest {
    /**
     * 渠道
     */
    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;

    @NotBlank
    @Length(min = 1, max = 64)
    private String platformName;

    // ----------------- base -----------------
    /**
     * endpoint
     */
    @NotBlank
    private String endpoint;
    /**
     * accessKeyId
     */
    @NotBlank
    private String accessKeyId;
    /**
     * accessKeySecret
     */
    @NotBlank
    private String accessKeySecret;


    /**
     * bucketName
     */
    @NotBlank
    private String bucketName;


    // TODO
    // ----------------- other -----------------

}
