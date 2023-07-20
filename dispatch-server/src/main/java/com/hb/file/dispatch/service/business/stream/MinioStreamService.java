package com.hb.file.dispatch.service.business.stream;

import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.enums.PlatformEnum;
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
    public StoreUtil getStoreUtil(String channel) {
        return getMinioUtil(channel);
    }

    private MinioUtil getMinioUtil(String channel) {
        MinioProperties properties = getConfig(channel);
        MinioClientFactory factory = new MinioClientFactory(properties);
        return new MinioUtil(factory, properties.getBucketName());
    }

    private MinioProperties getConfig(String channel) {
        Map<String, String> config = getConfig(channel, PlatformEnum.MINIO);
        if (Objects.isNull(config)) {
            throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_NOT_EXIST);
        }
        MinioProperties properties = new MinioProperties();
        BeanUtils.copyProperties(config, properties);
        return properties;
    }
}
