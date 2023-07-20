package com.hb.dispatch.input;

import com.hb.file.core.factory.FtpClientFactory;
import com.hb.file.core.factory.ObsClientFactory;
import com.hb.file.core.utils.FtpUtil;
import com.hb.file.core.utils.ObsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhaochengshui
 * @description
 * @date 2023/7/12
 */
@SpringBootTest
public class ObsUtilTest {

    @Test
    public void download() throws IOException {
        ObsClientFactory factory = new ObsClientFactory("T4P26LNWACCCTWKH9L09", "9fq21UgU9vc74P2LZKLsDWWax2445jxmgI0exw2Q",
                "obs.cn-east-3.myhuaweicloud.com", null);
        ObsUtil obsUtil = new ObsUtil(factory, "aa-test-bc");
        obsUtil.download("/b/dd/bg.jpeg", "/Users/hanbaolaoba/Desktop/aa.jpg", 1024);
    }

    @Test
    public void upload() throws IOException {
        ObsClientFactory factory = new ObsClientFactory("T4P26LNWACCCTWKH9L09", "9fq21UgU9vc74P2LZKLsDWWax2445jxmgI0exw2Q",
                "obs.cn-east-3.myhuaweicloud.com", null);
        ObsUtil obsUtil = new ObsUtil(factory, "aa-test-bc");
        obsUtil.upload("/b/dd/bg.jpeg", "/Users/hanbaolaoba/Desktop/aa.jpg");
    }

    @Test
    public void download2() throws IOException {
        ObsClientFactory factory = new ObsClientFactory("T4P26LNWACCCTWKH9L09", "9fq21UgU9vc74P2LZKLsDWWax2445jxmgI0exw2Q",
                "obs.cn-east-3.myhuaweicloud.com", null);
        ObsUtil obsUtil = new ObsUtil(factory, "aa-test-bc");
        FtpClientFactory factory2 = new FtpClientFactory("172.19.0.8", 21, "ftpuser", "d9rQyyedLqXiKCUz");
        FtpUtil ftpUtil = new FtpUtil(factory2);

        InputStream fis = obsUtil.download("b/dd/2.txt");
        ftpUtil.uploadByInputStream("/c/44/2.txt", fis, -1, 1024, null);
    }
}
