package com.hb.file.dispatch.controller;


import com.hb.file.core.commons.Result;
import com.hb.file.dispatch.request.DispatchTaskAddRequest;
import com.hb.file.dispatch.service.business.StreamCopyTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/task")
@Validated
@Slf4j
public class DispatchTaskController {
    @Resource
    private StreamCopyTaskService streamCopyTaskService;

    @PostMapping("/add")
    public Result addDispatchTask(@RequestBody @Validated DispatchTaskAddRequest request) {
        log.info("Add DispatchTask receive request: {}", request);
        streamCopyTaskService.initTask(request);
        return Result.success();
    }
}
