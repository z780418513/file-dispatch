package com.hb.file.core.enums;

import lombok.Getter;

@Getter
public enum PlatformEnum {
    /**
     * 华为OBS
     */
    OBS(1),
    /**
     * 阿里OSS
     */
    OSS(2),
    /**
     * 厂家FTP
     */
    FTP(3),
    /**
     * MINIO
     */
    MINIO(4);
    private final int platformType;

    PlatformEnum(int platformType) {
        this.platformType = platformType;
    }
}
