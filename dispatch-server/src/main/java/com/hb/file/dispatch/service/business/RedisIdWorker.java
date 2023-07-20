package com.hb.file.dispatch.service.business;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * redis ID生成器
 *
 * @author zhaochengshui
 */
@Component
public class RedisIdWorker {


    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 使用构造函数，或者使用 @Autowired\@Resource将StringRedisTemplate注入
     *
     * @param stringRedisTemplate
     */
    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 定义一个开始的时间戳
     */
    private static final long BEGIN_TIMESTAMP = 1640995200L;

    /**
     * 序列号的位数
     */
    private static final int COUNT_BITS = 32;


    public long nextId(String keyPrefix) {
        //1生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        //2.生成序列号
        //2.1获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //2.2自增长  每一天下的单换当天的key 这样方便统计当天的销售量，key也不会满
        Long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);

        //3.拼接返回
        return timestamp << COUNT_BITS | count;
    }

}
