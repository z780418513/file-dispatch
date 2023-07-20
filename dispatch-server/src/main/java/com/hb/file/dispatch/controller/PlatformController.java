package com.hb.file.dispatch.controller;

import cn.hutool.core.bean.BeanUtil;
import com.hb.file.core.commons.Result;
import com.hb.file.core.enums.PlatformEnum;
import com.hb.file.dispatch.request.*;
import com.hb.file.dispatch.service.PlatformConfigService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/platform")
@Slf4j
public class PlatformController {
    @Resource
    private PlatformConfigService platformConfigService;

    @PostMapping("/add/config/obs")
    public Result addObsConfig(@RequestBody @Validated PlatformObsAddRequest request) {
        log.info("Add Platform OBS Config receive request: {}", request);
        Map<String, Object> params = BeanUtil.beanToMap(request, false, true);

        if (platformConfigService.addConfigs(request.getChannel(), PlatformEnum.OBS.getPlatformType(), params)) {
            return Result.success("添加成功", true);
        }
        return Result.error("添加失败", false);
    }


    @PostMapping("/add/config/oss")
    public Result addOssConfig(@RequestBody @Validated PlatformOssAddRequest request) {
        log.info("Add Platform OSS Config receive request: {}", request);
        Map<String, Object> params = BeanUtil.beanToMap(request, false, true);

        if (platformConfigService.addConfigs(request.getChannel(), PlatformEnum.OSS.getPlatformType(), params)) {
            return Result.success("添加成功", true);
        }
        return Result.error("添加失败", false);
    }


    @PostMapping("/add/config/ftp")
    public Result addFtpConfig(@RequestBody @Validated PlatformFtpAddRequest request) {
        log.info("Add Platform FTP Config receive request: {}", request);
        Map<String, Object> params = BeanUtil.beanToMap(request, false, true);

        if (platformConfigService.addConfigs(request.getChannel(), PlatformEnum.FTP.getPlatformType(), params)) {
            return Result.success("添加成功", true);
        }
        return Result.error("添加失败", false);
    }


    @PostMapping("/add/config/minio")
    public Result addMinioConfig(@RequestBody @Validated PlatformMinioAddRequest request) {
        log.info("Add Platform Minio Config receive request: {}", request);
        Map<String, Object> params = BeanUtil.beanToMap(request, false, true);

        if (platformConfigService.addConfigs(request.getChannel(), PlatformEnum.MINIO.getPlatformType(), params)) {
            return Result.success("添加成功", true);
        }
        return Result.error("添加失败", false);
    }


    @GetMapping("/config/info")
    public Result getConfigByPlatform(@NotBlank @Length(min = 1, max = 64)
                                      String channel,
                                      @NotNull @Length(min = 1, max = 64)
                                      Integer platform) {
        log.info("Get Config By Platform receive channel: {}, platform: {}", channel, platform);

        return Result.success("查询成功", platformConfigService.queryPlatformConfig(channel, platform));
    }

    @PostMapping("/config/remove")
    public Result removePlatformConfig(@RequestBody @Validated PlatformRemoveRequest request) {
        log.info("Remove Platform Config receive request: {}", request);

        if (platformConfigService.removePlatformConfig(request.getChannel(), request.getPlatform())) {
            return Result.success("删除成功", true);
        }
        return Result.error("删除失败", false);
    }
}
