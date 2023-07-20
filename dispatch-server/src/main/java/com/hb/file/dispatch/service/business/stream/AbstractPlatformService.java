package com.hb.file.dispatch.service.business.stream;

import com.hb.file.core.enums.PlatformEnum;
import com.hb.file.dispatch.service.PlatformConfigService;
import com.hb.file.dispatch.service.PlatformStreamService;
import org.springframework.lang.Nullable;

import java.util.Map;

public abstract class AbstractPlatformService implements PlatformStreamService {

    private PlatformConfigService platformConfigService;

    public void setPlatformConfigService(PlatformConfigService platformConfigService) {
        this.platformConfigService = platformConfigService;
    }

    @Nullable
    public Map<String, String> getConfig(String channel, PlatformEnum platformEnum) {
        return platformConfigService.queryPlatformConfig(channel, platformEnum.getPlatformType());
    }

}
