package com.hb.file.dispatch.service;

import com.hb.file.core.utils.StoreUtil;

public interface PlatformStreamService {

    StoreUtil getStoreUtil(String platformId);

}
