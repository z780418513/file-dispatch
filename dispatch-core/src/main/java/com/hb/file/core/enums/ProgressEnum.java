package com.hb.file.core.enums;

import lombok.Getter;

@Getter
public enum ProgressEnum {

    THREE_QUARTER(0.75),
    HALF(0.5),
    QUARTER(0.25);

    ProgressEnum(double progress) {
        this.progress = progress;
    }

    private final double progress;

}
