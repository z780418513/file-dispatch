package com.hb.file.dispatch.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hb.file.core.commons.PageUtils;
import com.hb.file.core.commons.Result;
import com.hb.file.core.enums.PlatformEnum;
import com.hb.file.dispatch.entity.ChannelPlatformRelModel;
import com.hb.file.dispatch.request.*;
import com.hb.file.dispatch.service.ChannelPlatformRelService;
import com.hb.file.dispatch.service.PlatformConfigService;
import com.hb.file.dispatch.service.business.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/platform")
@Slf4j
public class PlatformController {
    @Resource
    private PlatformConfigService platformConfigService;
    @Resource
    private PlatformService platformService;
    @Resource
    private ChannelPlatformRelService channelPlatformRelService;

    @PostMapping("/add/config/obs")
    public Result addObsConfig(@RequestBody @Validated PlatformObsAddRequest request) {
        log.info("Add Platform OBS Config receive request: {}", request);
        Map<String, Object> params = BeanUtil.beanToMap(request, false, true);

        if (platformService.addConfigs(request.getChannel(), PlatformEnum.OBS.getPlatformType(),
                request.getPlatformName(), params)) {
            return Result.success("添加成功", true);
        }
        return Result.error("添加失败", false);
    }


    @PostMapping("/add/config/oss")
    public Result addOssConfig(@RequestBody @Validated PlatformOssAddRequest request) {
        log.info("Add Platform OSS Config receive request: {}", request);
        Map<String, Object> params = BeanUtil.beanToMap(request, false, true);

        if (platformService.addConfigs(request.getChannel(), PlatformEnum.OSS.getPlatformType(),
                request.getPlatformName(), params)) {
            return Result.success("添加成功", true);
        }
        return Result.error("添加失败", false);
    }


    @PostMapping("/add/config/ftp")
    public Result addFtpConfig(@RequestBody @Validated PlatformFtpAddRequest request) {
        log.info("Add Platform FTP Config receive request: {}", request);
        Map<String, Object> params = BeanUtil.beanToMap(request, false, true);

        if (platformService.addConfigs(request.getChannel(), PlatformEnum.FTP.getPlatformType(),
                request.getPlatformName(), params)) {
            return Result.success("添加成功", true);
        }
        return Result.error("添加失败", false);
    }


    @PostMapping("/add/config/minio")
    public Result addMinioConfig(@RequestBody @Validated PlatformMinioAddRequest request) {
        log.info("Add Platform Minio Config receive request: {}", request);
        Map<String, Object> params = BeanUtil.beanToMap(request, false, true);

        if (platformService.addConfigs(request.getChannel(), PlatformEnum.MINIO.getPlatformType(),
                request.getPlatformName(), params)) {
            return Result.success("添加成功", true);
        }
        return Result.error("添加失败", false);
    }


    @GetMapping("/config/info")
    public Result getConfigByPlatform(@NotBlank @Length(min = 1, max = 64) String platformId) {
        log.info("Get Config By Platform receive platformId: {}", platformId);

        return Result.success("查询成功", platformConfigService.queryPlatformConfig(platformId));
    }


    @PostMapping("/config/remove")
    public Result removePlatformConfig(@RequestBody @Validated PlatformRemoveRequest request) {
        log.info("Remove Platform Config receive request: {}", request);

        if (platformService.removePlatformConfig(request.getChannel(), request.getPlatform(), request.getPlatformId())) {
            return Result.success("删除成功", true);
        }
        return Result.error("删除失败", false);
    }

    @GetMapping("/query/page")
    public Result queryPageByChannel(@RequestParam("channel") String channel,
                                     @RequestParam("current") Long current,
                                     @RequestParam("size") Long size) {
        log.info("Query Page By Channel receive channel: {}, current: {}, size: {}", channel, current, size);
        IPage<ChannelPlatformRelModel> page = channelPlatformRelService.queryByChannel(channel, current, size);
        if (Objects.nonNull(page)) {
            return Result.success("查询成功", PageUtils.convertPageBean(page));
        }
        return Result.success();
    }

    @GetMapping("/query/list")
    public Result queryListByChannel(@RequestParam("channel") String channel) {
        log.info("Query List By Channel receive channel: {}", channel);
        return Result.success(channelPlatformRelService.queryByChannel(channel, null));
    }
}
