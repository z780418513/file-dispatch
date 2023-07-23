package com.hb.file.dispatch.service;


import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

public interface PlatformConfigService {

    boolean addConfigs(String platformId, Map<String, Object> configs);

    @Nullable
    HashMap<String, String> queryPlatformConfig(String platformId);

    boolean removePlatformConfig(String platformId);

}
