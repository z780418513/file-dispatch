package com.hb.file.core.properties;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class OssProperties {

    /**
     * endpoint
     */
    private String endpoint;
    /**
     * accessKeyId
     */
    private String accessKeyId;
    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * bucketName
     */
    private String bucketName;

    /**
     * STS安全令牌SecurityToken
     */
    @Nullable
    private String securityToken;

}
