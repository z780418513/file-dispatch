package com.hb.file.core.enums;

import lombok.Getter;

@Getter
public enum BusInessExceptionEnum {
    // 自定义异常 2000-3000
    CHANNEL_IS_ENABLE(2000, "渠道已启用"),
    CHANNEL_NOT_SUPPORT(2001, "不支持该渠道"),
    CHANNEL_NOT_EXIST(2002, "渠道不存在或没启用"),

    PLATFORM_CONFIG_IS_EXIST(2100, "平台配置已存在"),
    PLATFORM_CONFIG_NOT_EXIST(2101, "平台配置未存在"),
    PLATFORM_NOT_SUPPORT(2102, "不支持该平台"),
    PLATFORM_NOT_ENABLE(2103, "平台配置未启用"),
    ;


    private final Integer code;
    private final String desc;

    BusInessExceptionEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
