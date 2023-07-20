package com.hb.file.core.factory;

import com.hb.file.core.exception.ClientCreateException;
import com.hb.file.core.ftp.FtpConfiguration;
import com.hb.file.core.properties.FtpProperties;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Objects;

/**
 * FtpClient工厂类
 *
 * @author hanbaolaoba
 */
public class FtpClientFactory {

    /**
     * host
     */
    private final String host;
    /**
     * port
     */
    private final int port;
    /**
     * username
     */
    private final String username;
    /**
     * password
     */
    private final String password;
    /**
     * FtpClientProperties
     */
    @Nullable
    private FtpConfiguration configuration;

    public FtpClientFactory(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public FtpClientFactory(FtpProperties ftpProperties) {
        this.host = ftpProperties.getHost();
        this.port = ftpProperties.getPort();
        this.username = ftpProperties.getUsername();
        this.password = ftpProperties.getPassword();
        this.configuration = ftpProperties.getConfiguration();
    }


    public FTPClient createClient() throws ClientCreateException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);

            if (!ftpClient.login(username, password)) {
                throw new ClientCreateException("FTP登陆失败");
            }

            configClient(ftpClient, configuration);

            return ftpClient;
        } catch (IOException e) {
            throw new ClientCreateException("FTP连接失败, Msg: " + e.getMessage());
        }
    }

    /**
     * 配置ftpClient
     *
     * @param ftpClient
     * @param properties
     */
    public void configClient(FTPClient ftpClient, @Nullable FtpConfiguration properties) {
        if (Objects.nonNull(properties)) {
            // TODO
        }
    }

    public void setProperties(FtpConfiguration configuration) {
        this.configuration = configuration;
    }
}
