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

    // ----------------- base -----------------
    /**
     * endpoint
     */
    @NotBlank
    private final String endpoint;
    /**
     * accessKeyId
     */
    @NotBlank
    private final String accessKeyId;
    /**
     * accessKeySecret
     */
    @NotBlank
    private final String accessKeySecret;


    /**
     * bucketName
     */
    @NotBlank
    private final String bucketName;


    // TODO
    // ----------------- other -----------------

}
