package com.hb.file.core.enums;


import lombok.Getter;

@Getter
public enum TaskStatusEnum {
    WAITING(1),
    SUCCESS(2),
    FAIL(3);
    private int status;

    TaskStatusEnum(int status) {
        this.status = status;
    }
}
