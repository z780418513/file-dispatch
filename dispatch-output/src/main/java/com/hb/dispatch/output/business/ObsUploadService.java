package com.hb.dispatch.output.business;

import com.hb.dispatch.output.service.UploadService;
import com.hb.file.core.exception.UploadException;
import com.hb.file.core.utils.ObsUtil;

/**
 * @author zhaochengshui
 * @description 华为obs上传服务类
 * @date 2023/7/12
 */
public class ObsUploadService implements UploadService {

    private ObsUtil obsUtil;

    @Override
    public void doUpload(String localPath, String remotePath) throws UploadException {
        obsUtil.upload(remotePath, localPath);
    }
}
