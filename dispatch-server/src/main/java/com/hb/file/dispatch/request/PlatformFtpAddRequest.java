package com.hb.file.dispatch.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PlatformFtpAddRequest {
    /**
     * 渠道
     */
    @NotBlank
    @Length(min = 1, max = 64)
    private String channel;

    @NotBlank
    @Length(min = 1, max = 64)
    private String platformName;

    // ----------------- base -----------------
    /**
     * host
     */
    @NotBlank
    private String host;
    /**
     * port
     */
    @NotNull
    private int port;
    /**
     * username
     */
    @NotBlank
    private String username;
    /**
     * password
     */
    @NotBlank
    private String password;


    // TODO
    // ----------------- other -----------------

}
