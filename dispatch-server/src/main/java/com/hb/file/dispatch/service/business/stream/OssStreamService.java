package com.hb.file.dispatch.service.business.stream;

import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.core.factory.OssClientFactory;
import com.hb.file.core.properties.OssProperties;
import com.hb.file.core.utils.OssUtil;
import com.hb.file.core.utils.StoreUtil;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.Objects;

public class OssStreamService extends AbstractPlatformService {

    @Override
    public StoreUtil getStoreUtil(String platformId) {
        return getOssUtil(platformId);
    }

    private OssUtil getOssUtil(String platformId) {
        OssProperties properties = getConfig(platformId);
        OssClientFactory factory = new OssClientFactory(properties);
        return new OssUtil(factory, properties.getBucketName());
    }

    private OssProperties getConfig(String platformId) {
        Map<String, String> config = getConfigByPlatformId(platformId);
        if (Objects.isNull(config)) {
            throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_NOT_EXIST);
        }
        OssProperties ossProperties = new OssProperties();
        BeanUtils.copyProperties(config, ossProperties);
        return ossProperties;
    }
}
