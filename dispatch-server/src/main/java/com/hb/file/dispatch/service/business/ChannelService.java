package com.hb.file.dispatch.service.business;

import com.hb.file.dispatch.entity.ChannelPlatformRelModel;
import com.hb.file.dispatch.service.ChannelConfigService;
import com.hb.file.dispatch.service.ChannelPlatformRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ChannelService {
    @Resource
    private ChannelPlatformRelService channelPlatformRelService;
    @Resource
    private ChannelConfigService channelConfigService;
    @Resource
    private ChannelSupportService channelSupportService;

    public boolean delChannel(String channel) {
        if (!channelConfigService.delChannel(channel)) {
            return false;
        }
        channelSupportService.disableChannelSupport(channel);
        for (ChannelPlatformRelModel rel : channelPlatformRelService.queryByChannel(channel, null)) {
            channelPlatformRelService.remove(channel, rel.getPlatform(), rel.getPlatformId());
        }
        return true;
    }
}
