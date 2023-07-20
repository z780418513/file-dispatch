package com.hb.file.core.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.hb.file.core.exception.ClientCreateException;
import com.hb.file.core.exception.DownloadException;
import com.hb.file.core.exception.UploadException;
import com.hb.file.core.factory.ObsClientFactory;
import com.obs.services.ObsClient;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import com.obs.services.model.ProgressListener;
import com.obs.services.model.PutObjectRequest;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.*;
import java.util.Objects;

/**
 * obs工具类<p/>
 * bio操作 RepeatableRequestEntity
 *
 * @author zhaochengshui
 * @description
 * @date 2023/7/10
 */
public class ObsUtil implements StoreUtil {
    private static final Logger logger = LoggerFactory.getLogger(ObsUtil.class);

    /**
     * obs客户端
     */
    private final ObsClient obsClient;

    /**
     * bucket桶
     */
    private final String bucket;
    /**
     * 拦截器,可以获取进度相关信息
     */
    @Nullable
    private ProgressListener progressListener;

    public ObsUtil(ObsClient obsClient, String bucket) {
        this.obsClient = obsClient;
        this.bucket = bucket;
    }

    public ObsUtil(ObsClientFactory factory, String bucket) throws ClientCreateException {
        this.obsClient = initClientByFactory(factory);
        this.bucket = bucket;
    }

    private ObsClient initClientByFactory(ObsClientFactory factory) throws ClientCreateException {
        return factory.createClient();
    }

    /**
     * 上传
     *
     * @param remotePath 远程路径
     * @param localPath  本地路径
     */
    public void upload(String remotePath, String localPath) throws UploadException {
        PutObjectRequest request;
        try (FileInputStream fis = new FileInputStream(localPath)) {

            request = uploadRequest(remotePath, fis);
            this.obsClient.putObject(request);
        } catch (Exception e) {
            throw new UploadException("OBS Upload fail, Msg: " + e.getMessage());
        } finally {
            closeClient();
        }
    }


    /**
     * 下载文件
     *
     * @param objectKey  objectKey
     * @param localPath  本地文件路径
     * @param bufferSize 缓冲区
     * @throws DownloadException 下载异常
     */
    public void download(String objectKey, String localPath, int bufferSize) throws DownloadException {
        InputStream is = null;
        try (FileOutputStream fos = new FileOutputStream(localPath)) {

            GetObjectRequest request = downloadRequest(objectKey);
            ObsObject object = this.obsClient.getObject(request);
            is = object.getObjectContent();

            // mkdir parent dir
            FileUtil.mkdir(new File(localPath).getParent());

            IoUtil.copy(is, fos, bufferSize);
        } catch (Exception e) {
            throw new DownloadException("OBS Download fail, Msg: " + e.getMessage());
        } finally {
            IoUtil.close(is);
        }


    }

    private PutObjectRequest uploadRequest(String objectKey, InputStream inputStream) {
        PutObjectRequest request = new PutObjectRequest(this.bucket, objectKey, inputStream);
        // add  progressListener
        if (Objects.nonNull(this.progressListener)) {
            request.setProgressListener(this.progressListener);
            request.setProgressInterval(1024 * 1024L);
        }
        return request;
    }

    private GetObjectRequest downloadRequest(String objectKey) {
        GetObjectRequest request = new GetObjectRequest(this.bucket, objectKey);
        // add progressListener
        if (Objects.nonNull(this.progressListener)) {
            request.setProgressListener(this.progressListener);
            request.setProgressInterval(1024 * 1024L);
        }
        return request;
    }

    /**
     * 关闭连接
     */
    private void closeClient() {
        try {
            this.obsClient.close();
        } catch (IOException ignored) {
            // do nothing
        }
    }

    public void setProgressListener(@Nullable ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public InputStream downloadInputStream(String objectKey) {
        try {
            GetObjectRequest request = downloadRequest(objectKey);
            ObsObject object = this.obsClient.getObject(request);
            return object.getObjectContent();
        } catch (Exception e) {
            throw new DownloadException("OBS Download fail, Msg: " + e.getMessage());
        }
    }

    @Override
    public void uploadInputStream(String remotePath, InputStream is) throws Exception {
        try {
            PutObjectRequest request = uploadRequest(remotePath, is);
            this.obsClient.putObject(request);
        } catch (Exception e) {
            throw new UploadException("OBS Upload fail, Msg: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        closeClient();
    }
}
