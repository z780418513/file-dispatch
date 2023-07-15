package com.hb.dispatch.input.service;

import com.hb.file.core.exception.DownloadException;

/**
 * @author zhaochengshui
 * @description 下载服务类
 * @date 2023/7/10
 */
public interface DownloadService {

    /**
     * 下载文件
     *
     * @param remotePath 远程地址
     * @param localPah   本地路径
     * @throws DownloadException 下载异常
     */
    void doDownload(String remotePath, String localPah) throws DownloadException;

}
