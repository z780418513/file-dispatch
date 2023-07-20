package com.hb.file.dispatch.service.business;

import com.hb.file.core.commons.RedisConstant;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
@CacheConfig
public class ChannelSupportService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public boolean support(String channel) {
        return Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(RedisConstant.CHANNEL_SUPPORT_SET, channel));

    }

    public @Nullable Set<String> getAllChannel() {
        return stringRedisTemplate.opsForSet().members(RedisConstant.CHANNEL_SUPPORT_SET);
    }

    public void enableChannelSupport(String channel) {
        stringRedisTemplate.opsForSet().add(RedisConstant.CHANNEL_SUPPORT_SET, channel);
    }
}
