package com.hb.file.core.exception;

import com.hb.file.core.enums.BusInessExceptionEnum;

public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(BusInessExceptionEnum exceptionEnum) {
        super(exceptionEnum.getDesc());
        this.code = exceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
