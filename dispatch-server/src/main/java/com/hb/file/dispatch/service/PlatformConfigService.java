package com.hb.file.dispatch.service;


import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

public interface PlatformConfigService {

    boolean addConfigs(String channel, Integer platform, Map<String, Object> configs);

    @Nullable
    HashMap<String, String> queryPlatformConfig(String channel, Integer platform);

    boolean removePlatformConfig(String channel, Integer platform);
}
