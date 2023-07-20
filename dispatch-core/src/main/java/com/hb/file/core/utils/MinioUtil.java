package com.hb.file.core.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.hb.file.core.exception.DownloadException;
import com.hb.file.core.exception.UploadException;
import com.hb.file.core.factory.MinioClientFactory;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * minio工具类
 * </p> 参考 https://www.bookstack.cn/read/MinioCookbookZH/22.md
 *
 * @author hanbaolaoba
 */
public class MinioUtil implements StoreUtil {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioUtil(MinioClientFactory minioClientFactory, String bucketName) {
        this.minioClient = minioClientFactory.createClient();
        this.bucketName = bucketName;
    }

    public void upload(String objectKey, String localPath) throws UploadException {
        FileInputStream fis = null;
        try {
            File localFile = new File(localPath);
            fis = new FileInputStream(localFile);
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(objectKey)
                    .stream(fis, localFile.length(), -1)
                    .build();
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            throw new UploadException("Minio Upload fail, " + e.getMessage());
        } finally {
            IoUtil.close(fis);
        }
    }


    public void download(String objectKey, String localPath, int bufferSize) throws DownloadException {
        GetObjectResponse gos = null;
        FileOutputStream fos = null;
        try {
            GetObjectArgs objectArgs = GetObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(objectKey)
                    .build();
            gos = minioClient.getObject(objectArgs);

            // mkdir parent dir
            FileUtil.mkdir(new File(localPath).getParent());
            fos = new FileOutputStream(localPath);
            IoUtil.copy(gos, fos, bufferSize);
        } catch (Exception e) {
            throw new DownloadException("Minio Download fail, Msg: " + e.getMessage());
        } finally {
            IoUtil.close(gos);
            IoUtil.close(fos);
        }


    }

    @Override
    public InputStream downloadInputStream(String objectKey) throws Exception {
        GetObjectArgs objectArgs = GetObjectArgs.builder()
                .bucket(this.bucketName)
                .object(objectKey)
                .build();
        return minioClient.getObject(objectArgs);
    }

    @Override
    public void uploadInputStream(String target, InputStream in) throws Exception {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(target)
                    .stream(in, -1, 1024 * 1024 * 50)
                    .build();
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            throw new UploadException("Minio Upload fail, " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
