package com.hb.dispatch.input;

import com.hb.file.core.factory.FtpClientFactory;
import com.hb.file.core.ftp.FtpStreamProgress;
import com.hb.file.core.utils.FtpUtil;
import org.junit.jupiter.api.Test;

public class FtpUtilTest {

    @Test
    public void upload() {
        FtpClientFactory ftpClientFactory = new FtpClientFactory("172.19.0.8", 21, "ftpuser", "d9rQyyedLqXiKCUz");
        FtpUtil ftpUtil = new FtpUtil(ftpClientFactory);
        FtpStreamProgress ftpStreamProgress = new FtpStreamProgress( 1024,lister->{
            double averageSpeed = lister.getAverageSpeed();
        });
        boolean upload = ftpUtil.upload("/b/dd/ss/7721/bg.jpg", "/Users/hanbaolaoba/Desktop/aa.jpg", 1024, ftpStreamProgress);
    }

    @Test
    public void download() {
        FtpClientFactory ftpClientFactory = new FtpClientFactory("172.19.0.8", 21, "ftpuser", "d9rQyyedLqXiKCUz");
        FtpUtil ftpUtil = new FtpUtil(ftpClientFactory);
        boolean upload = ftpUtil.download("/b/dd/ss/7721/bg.jpg", "/Users/hanbaolaoba/Desktop/cc/dd.jpg", 1024, null);
    }
}
