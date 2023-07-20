package com.hb.file.core.utils;

import java.io.InputStream;
import java.io.OutputStream;

public interface StoreUtil {

    InputStream getDownloadInputStream(String source) throws Exception;

    OutputStream getUploadOutputStream(String target) throws Exception;

    /**
     * 销毁客户端
     */
    void destroy();
}
