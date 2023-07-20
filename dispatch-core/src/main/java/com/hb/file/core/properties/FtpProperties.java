package com.hb.file.core.properties;

import com.hb.file.core.ftp.FtpConfiguration;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class FtpProperties {
    /**
     * host
     */
    private String host;
    /**
     * port
     */
    private int port;
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;

    /**
     * configuration
     */
    @Nullable
    private FtpConfiguration configuration;
}
