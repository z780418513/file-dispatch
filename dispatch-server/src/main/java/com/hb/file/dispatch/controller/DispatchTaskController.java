package com.hb.file.dispatch.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hb.file.core.commons.PageUtils;
import com.hb.file.core.commons.Result;
import com.hb.file.dispatch.entity.DispatchTaskModel;
import com.hb.file.dispatch.request.DispatchTaskAddRequest;
import com.hb.file.dispatch.service.DispatchTaskService;
import com.hb.file.dispatch.service.business.StreamCopyTaskService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/task")
@Validated
@Slf4j
public class DispatchTaskController {
    @Resource
    private StreamCopyTaskService streamCopyTaskService;
    @Resource
    private DispatchTaskService dispatchTaskService;

    @PostMapping("/add")
    public Result addDispatchTask(@RequestBody @Validated DispatchTaskAddRequest request) {
        log.info("Add DispatchTask receive request: {}", request);
        streamCopyTaskService.initTask(request);
        return Result.success();
    }


    @GetMapping("/page")
    public Result queryDispatchTask(@NotBlank @Length(min = 1, max = 64) String channel,
                                    @Range(max = 100) Integer status,
                                    @NotNull @Range(max = 100) Integer current,
                                    @NotNull @Range(max = 100) Integer size) {
        IPage<DispatchTaskModel> page =
                dispatchTaskService.queryDispatchTask(channel, status, current, size);
        return Result.success(PageUtils.convertPageBean(page));
    }
}
