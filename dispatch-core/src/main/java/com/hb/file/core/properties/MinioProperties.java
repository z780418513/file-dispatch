package com.hb.file.core.properties;

import lombok.Data;

@Data
public class MinioProperties {

    /**
     * endpoint
     */
    private String endpoint;
    /**
     * accessKey
     */
    private String accessKey;
    /**
     * secretKey
     */
    private String secretKey;

    /**
     * bucketName
     */
    private String bucketName;
}
