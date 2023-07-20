package com.hb.file.core.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.hb.file.core.exception.ClientCreateException;
import com.hb.file.core.exception.DownloadException;
import com.hb.file.core.exception.UploadException;
import com.hb.file.core.factory.OssClientFactory;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class OssUtil implements StoreUtil {
    private static final Logger logger = LoggerFactory.getLogger(OssUtil.class);

    /**
     * ossClient
     */
    private final OSSClient ossClient;
    /**
     * bucketName
     */
    private final String bucketName;
    /**
     * 上传进度监听器
     */
    @Nullable
    private ProgressListener progressListener;

    public OssUtil(OssClientFactory ossClientFactory, String bucketName) throws ClientCreateException {
        this.bucketName = bucketName;
        this.ossClient = initClientByFactory(ossClientFactory);
    }

    public OssUtil(OSSClient ossClient, String bucketName) {
        this.ossClient = ossClient;
        this.bucketName = bucketName;
    }

    private OSSClient initClientByFactory(OssClientFactory ossClientFactory) throws ClientCreateException {
        return ossClientFactory.createClient();

    }


    public void upload(String objectName, String localPath) throws UploadException {
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(localPath));
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucketName, objectName, inputStream);
            if (Objects.nonNull(progressListener)) {
                putObjectRequest.withProgressListener(progressListener);
            }
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new UploadException("OSS Upload fail, Msg: " + e.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }

    public void download(String objectName, String localPath, int bufferSize) throws DownloadException {
        InputStream is = null;
        FileOutputStream fos = null;
        try {

            // request
            GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
            if (Objects.nonNull(progressListener)) {
                request.withProgressListener(progressListener);
            }
            OSSObject ossObject = ossClient.getObject(request);
            is = ossObject.getObjectContent();


            // mkdir parent dir
            FileUtil.mkdir(new File(localPath).getParent());
            fos = new FileOutputStream(localPath);
            IoUtil.copy(is, fos, bufferSize);
        } catch (Exception e) {
            throw new DownloadException("OSS Download fail, Msg: " + e.getMessage());
        } finally {
            IoUtil.close(is);
            IoUtil.close(fos);
            ossClient.shutdown();
        }
    }

    public InputStream downloadInputStream(String objectName) throws DownloadException {
        try {
            // request
            GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
            if (Objects.nonNull(progressListener)) {
                request.withProgressListener(progressListener);
            }
            OSSObject ossObject = ossClient.getObject(request);
            return ossObject.getObjectContent();
        } catch (Exception e) {
            throw new DownloadException("OSS Download fail, Msg: " + e.getMessage());
        }
    }


    @Override
    public InputStream getDownloadInputStream(String objectName) {
        return null;
    }

    @Override
    public OutputStream getUploadOutputStream(String target) {
        return null;
    }

    @Override
    public void destroy() {

    }
}
