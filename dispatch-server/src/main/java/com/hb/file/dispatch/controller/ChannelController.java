package com.hb.file.dispatch.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hb.file.core.commons.PageUtils;
import com.hb.file.core.commons.Result;
import com.hb.file.dispatch.entity.ChannelConfigModel;
import com.hb.file.dispatch.request.ChannelAddRequest;
import com.hb.file.dispatch.request.ChannelSwitchRequest;
import com.hb.file.dispatch.request.DelChannelRequest;
import com.hb.file.dispatch.service.ChannelConfigService;
import com.hb.file.dispatch.service.business.ChannelService;
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
    @Resource
    private ChannelService channelService;

    @PostMapping("/init")
    public Result initChannel(@RequestBody @Validated ChannelAddRequest request) {
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

    @PostMapping("/enable")
    public Result enableChannel(@RequestBody ChannelSwitchRequest request) {
        log.info("Enable Channel receive request: {}", request);
        if (channelConfigService.enableChannel(request.getChannel(), request.getEnable())) {
            return Result.success("禁用成功", true);
        } else {
            return Result.error("禁用失败", false);
        }
    }

    @PostMapping("/del")
    public Result delChannel(@RequestBody DelChannelRequest request) {
        log.info("Del Channel receive request: {}", request);
        if (channelService.delChannel(request.getChannel())) {
            return Result.success("删除成功", true);
        } else {
            return Result.error("删除失败", false);
        }
    }

    @GetMapping("/getAll")
    public Result queryAllChannel(@RequestParam("current") Integer current,
                                  @RequestParam("size") Integer size) {
        log.info("Query All Channel receive current: {}, pageSize: {}", current, size);
        IPage<ChannelConfigModel> page =
                channelConfigService.selectPage(current, size);
        return Result.success("查询成功", PageUtils.convertPageBean(page));
    }

    @GetMapping("/get/list")
    public Result queryAllChannel() {
        return Result.success("查询成功", channelConfigService.queryAllSupportChannel());
    }
}
