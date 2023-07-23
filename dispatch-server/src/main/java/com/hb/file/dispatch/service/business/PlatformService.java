package com.hb.file.dispatch.service.business;

import com.hb.file.dispatch.service.ChannelPlatformRelService;
import com.hb.file.dispatch.service.PlatformConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class PlatformService {
    @Resource
    private PlatformConfigService platformConfigService;
    @Resource
    private ChannelPlatformRelService channelPlatformRelService;
    @Resource
    private RedisIdWorker redisIdWorker;

    @Transactional
    public boolean addConfigs(String channel, Integer platform, String platformName, Map<String, Object> configs) {
        String platformId = String.valueOf(redisIdWorker.nextId(channel + ":" + platform));
        channelPlatformRelService.saveOne(channel, platform, platformName, platformId);
        return platformConfigService.addConfigs(platformId, configs);
    }

    @Transactional
    public boolean removePlatformConfig(String channel, Integer platform, String platformId) {
        // 删除关联记录
        channelPlatformRelService.remove(channel, platform, platformId);
        // 删除
        return platformConfigService.removePlatformConfig(platformId);
    }


}
