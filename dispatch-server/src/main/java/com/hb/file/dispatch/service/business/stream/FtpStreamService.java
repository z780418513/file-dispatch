package com.hb.file.dispatch.service.business.stream;

import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.enums.PlatformEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.core.factory.FtpClientFactory;
import com.hb.file.core.properties.FtpProperties;
import com.hb.file.core.utils.FtpUtil;
import com.hb.file.core.utils.StoreUtil;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.Objects;

public class FtpStreamService extends AbstractPlatformService {

    @Override
    public StoreUtil getStoreUtil(String channel) {
        return getFtpUtil(channel);
    }

    private FtpUtil getFtpUtil(String channel) {
        FtpProperties properties = getConfig(channel);
        FtpClientFactory factory = new FtpClientFactory(properties);
        return new FtpUtil(factory);
    }

    private FtpProperties getConfig(String channel) {
        Map<String, String> config = getConfig(channel, PlatformEnum.FTP);
        if (Objects.isNull(config)) {
            throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_NOT_EXIST);
        }
        FtpProperties properties = new FtpProperties();
        BeanUtils.copyProperties(config, properties);
        return properties;
    }
}