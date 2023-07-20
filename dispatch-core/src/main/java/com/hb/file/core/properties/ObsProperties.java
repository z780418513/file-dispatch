package com.hb.file.core.properties;

import com.obs.services.ObsConfiguration;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ObsProperties {
    /**
     * AK
     */
    private String accessKey;
    /**
     * SK
     */
    private String secretKey;
    /**
     * endPoint
     */
    private String endPoint;

    /**
     * bucket桶
     */
    private String bucket;


    /**
     * 配置信息
     */
    @Nullable
    private ObsConfiguration config;
}
