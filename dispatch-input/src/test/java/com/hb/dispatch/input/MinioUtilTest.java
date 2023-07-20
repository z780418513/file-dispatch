package com.hb.dispatch.input;

import com.hb.file.core.factory.MinioClientFactory;
import com.hb.file.core.utils.MinioUtil;
import org.junit.jupiter.api.Test;

public class MinioUtilTest {

    @Test
    public void upload() {
        MinioClientFactory factory = new MinioClientFactory("http://127.0.0.1:9000", "minioroot", "minioroot");
        MinioUtil minioUtil = new MinioUtil(factory, "aaa");
        minioUtil.upload("/a/b/d/dc.txt", "/Users/hanbaolaoba/Desktop/未命名 2.txt");
    }

    @Test
    public void download() {
        MinioClientFactory factory = new MinioClientFactory("http://127.0.0.1:9000", "minioroot", "minioroot");
        MinioUtil minioUtil = new MinioUtil(factory, "aaa");
        minioUtil.download("/a/b/d/dc.txt", "/Users/hanbaolaoba/Desktop/joj/sas.txt", 1024);
    }
}
