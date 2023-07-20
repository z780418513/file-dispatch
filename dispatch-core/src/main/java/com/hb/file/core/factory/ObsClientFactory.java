package com.hb.file.core.factory;

import com.hb.file.core.exception.ClientCreateException;
import com.hb.file.core.properties.ObsProperties;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * @author zhaochengshui
 * @description
 * @date 2023/7/10
 */
public class ObsClientFactory {

    /**
     * AK
     */
    private final String accessKey;
    /**
     * SK
     */
    private final String secretKey;
    /**
     * endPoint
     */
    private final String endPoint;
    /**
     * 配置信息
     */
    @Nullable
    private final ObsConfiguration config;

    public ObsClientFactory(String accessKey, String secretKey, String endPoint, @Nullable ObsConfiguration config) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.endPoint = endPoint;
        this.config = config;
    }

    public ObsClientFactory(ObsProperties obsProperties) {
        this.accessKey = obsProperties.getAccessKey();
        this.secretKey = obsProperties.getSecretKey();
        this.endPoint = obsProperties.getEndPoint();
        this.config = obsProperties.getConfig();
    }

    public ObsClient createClient() throws ClientCreateException {
        if (Objects.nonNull(config)) {
            config.setEndPoint(endPoint);
            return new ObsClient(accessKey, secretKey, null, config);
        } else {
            return new ObsClient(accessKey, secretKey, endPoint);
        }
    }


}
