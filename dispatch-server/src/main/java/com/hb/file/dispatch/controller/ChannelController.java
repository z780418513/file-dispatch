package com.hb.file.dispatch.controller;

import com.hb.file.core.commons.Result;
import com.hb.file.dispatch.entity.ChannelConfigModel;
import com.hb.file.dispatch.request.ChannelAddRequest;
import com.hb.file.dispatch.request.DisableChannelRequest;
import com.hb.file.dispatch.service.ChannelConfigService;
import com.hb.file.dispatch.service.business.ChannelSupportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/channel")
@Validated
@Slf4j
public class ChannelController {

    @Resource
    private ChannelConfigService channelConfigService;
    @Resource
    private ChannelSupportService channelSupportService;

    @PostMapping("/init")
    public Result initChannel(@RequestBody ChannelAddRequest request) {
        log.info("Init Channel receive request: {}", request);
        if (channelConfigService.initChannel(request.getChannel(), request.getChannelName())) {
            channelSupportService.enableChannelSupport(request.getChannel());
            return Result.success("初始化成功", true);
        }
        return Result.error("初始化失败", false);
    }

    @GetMapping("/config")
    public Result queryChannelConfig(@NotBlank(message = "channel不能为空") String channel) {
        log.info("Query Channel Config receive channel: {}", channel);
        ChannelConfigModel configModel = channelConfigService.queryByChannel(channel);
        return Result.success(configModel);
    }

    @PostMapping("/disable")
    public Result disableChannel(@RequestBody DisableChannelRequest request) {
        log.info("Disable Channel receive request: {}", request);
        if (channelConfigService.disableChannel(request.getChannel())) {
            return Result.success("禁用成功", true);
        } else {
            return Result.error("禁用失败", false);
        }
    }
}
