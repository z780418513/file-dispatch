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

    // ----------------- base -----------------
    /**
     * host
     */
    @NotBlank
    private final String host;
    /**
     * port
     */
    @NotNull
    private final int port;
    /**
     * username
     */
    @NotBlank
    private final String username;
    /**
     * password
     */
    @NotBlank
    private final String password;


    // TODO
    // ----------------- other -----------------

}
