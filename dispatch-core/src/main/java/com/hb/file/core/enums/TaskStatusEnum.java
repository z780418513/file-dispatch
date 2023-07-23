package com.hb.file.core.enums;


import lombok.Getter;

@Getter
public enum TaskStatusEnum {
    WAITING(1),
    EXECUTORY(9),
    SUCCESS(2),
    FAIL(3);
    private final int status;

    TaskStatusEnum(int status) {
        this.status = status;
    }
}
