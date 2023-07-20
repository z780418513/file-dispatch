package com.hb.file.core.utils;

import java.io.InputStream;

public interface StoreUtil {

    InputStream downloadInputStream(String source) throws Exception;

    void uploadInputStream(String target, InputStream in) throws Exception;

    /**
     * 销毁客户端
     */
    void destroy();
}
