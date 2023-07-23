package com.hb.file.dispatch.service.business.stream;

import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.core.factory.MinioClientFactory;
import com.hb.file.core.properties.MinioProperties;
import com.hb.file.core.utils.MinioUtil;
import com.hb.file.core.utils.StoreUtil;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.Objects;

public class MinioStreamService extends AbstractPlatformService {

    @Override
    public StoreUtil getStoreUtil(String platformId) {
        return getMinioUtil(platformId);
    }

    private MinioUtil getMinioUtil(String platformId) {
        MinioProperties properties = getConfig(platformId);
        MinioClientFactory factory = new MinioClientFactory(properties);
        return new MinioUtil(factory, properties.getBucketName());
    }

    private MinioProperties getConfig(String platformId) {
        Map<String, String> config = getConfigByPlatformId(platformId);
        if (Objects.isNull(config)) {
            throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_NOT_EXIST);
        }
        MinioProperties properties = new MinioProperties();
        BeanUtils.copyProperties(config, properties);
        return properties;
    }
}
