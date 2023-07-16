package com.hb.file.core.factory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.hb.file.core.exception.ClientCreateException;
import org.springframework.lang.Nullable;

/**
 * ossClient工厂类
 *
 * @author hanbaolaoba
 */
public class OssClientFactory {

    /**
     * endpoint
     */
    private final String endpoint;
    /**
     * accessKeyId
     */
    private final String accessKeyId;
    /**
     * accessKeySecret
     */
    private final String accessKeySecret;
    /**
     * STS安全令牌SecurityToken
     */
    @Nullable
    private final String securityToken;

    public OssClientFactory(String endpoint, String accessKeyId, String accessKeySecret, @Nullable String securityToken) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
    }

    public OSSClient createClient() throws ClientCreateException {
        try {
            // 使用代码嵌入的STS临时访问密钥和安全令牌配置访问凭证。
            CredentialsProvider credentialsProvider =
                    new DefaultCredentialProvider(accessKeyId, accessKeySecret, securityToken);
            return (OSSClient) new OSSClientBuilder().build(endpoint, credentialsProvider);
        } catch (Exception e) {
            throw new ClientCreateException("OSS create client fail, Error: " + e.getMessage());
        }
    }


}
