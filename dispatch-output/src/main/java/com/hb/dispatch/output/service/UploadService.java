package com.hb.dispatch.output.service;

import com.hb.file.core.exception.UploadException;

/**
 * @author zhaochengshui
 * @description 上传服务类
 * @date 2023/7/10
 */
public interface UploadService {
    /**
     * 上传
     *
     * @param localPath   本地路径
     * @param remotePath 远程路径
     * @throws UploadException 上传异常类
     */
    void doUpload(String localPath, String remotePath) throws UploadException;
}
