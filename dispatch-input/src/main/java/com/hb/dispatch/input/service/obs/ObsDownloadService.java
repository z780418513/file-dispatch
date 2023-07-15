package com.hb.dispatch.input.service.obs;


import com.hb.dispatch.input.service.DownloadService;
import com.hb.file.core.exception.DownloadException;
import com.hb.file.core.utils.ObsUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaochengshui
 * @description 华为obs下载服务
 * @date 2023/7/10
 */
@Slf4j
public class ObsDownloadService implements DownloadService {

    private ObsUtil obsUtil;


    @Override
    public void doDownload(String remotePath, String localPah) throws DownloadException {
        if (checkBigFile()) {
            downloadBigFile(remotePath, localPah);
        } else {
            download(remotePath, localPah);
        }
    }

    private void download(String objectKey, String localPath) {
        // to do download
        try {
            obsUtil.download(objectKey, localPath, 1024);
        } catch (Exception e) {
            log.warn("Do Obs Download Fail, Error: {} ", e.getMessage());
            throw new DownloadException("下载失败");
        }
    }

    private void downloadBigFile(String objectKey, String localPath) {
        // to do download
        try {
            obsUtil.download(objectKey, localPath, 1024 * 1024);
        } catch (Exception e) {
            log.warn("Do Obs Download Fail, Error: {} ", e.getMessage());
            throw new DownloadException("下载失败");
        }
    }

    private boolean checkBigFile() {
        return false;
    }


}
