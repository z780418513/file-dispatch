package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PlatformMinioAddRequest {

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
    private String endpoint;
    /**
     * accessKey
     */
    @NotBlank
    private String accessKey;

    /**
     * secretKey
     */
    @NotBlank
    private String secretKey;

    @NotBlank
    private String bucketName;


    // TODO
    // ----------------- other -----------------

}
