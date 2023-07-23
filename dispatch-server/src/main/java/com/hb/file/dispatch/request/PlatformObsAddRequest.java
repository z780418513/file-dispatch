package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PlatformObsAddRequest {

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
     * AK
     */
    @NotBlank
    private String accessKey;
    /**
     * SK
     */
    @NotBlank
    private String secretKey;
    /**
     * endPoint
     */
    @NotBlank
    private String endPoint;

    /**
     * bucket桶
     */
    @NotBlank
    private String bucket;


    // TODO
    // ----------------- other -----------------

}
