package com.hb.file.core.factory;

import com.hb.file.core.exception.ClientCreateException;
import io.minio.MinioClient;

/**
 * minioClient工厂类
 * @author hanbaolaoba
 */
public class MinioClientFactory {

    /**
     * endpoint
     */
    private final String endpoint;
    /**
     * accessKey
     */
    private final String accessKey;
    /**
     * secretKey
     */
    private final String secretKey;

    public MinioClientFactory(String endpoint, String accessKey, String secretKey) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public MinioClient createClient() throws ClientCreateException {
        try {
            return MinioClient.builder()
                    .endpoint(this.endpoint)
                    .credentials(this.accessKey, this.secretKey)
                    .build();
        } catch (Exception e) {
            throw new ClientCreateException("Minio create client fail, Error: " + e.getMessage());
        }
    }
}
