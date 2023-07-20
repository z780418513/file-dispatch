package com.hb.file.dispatch.service.business.stream;

import cn.hutool.core.io.IoUtil;
import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.core.exception.CopyException;
import com.hb.file.core.utils.StoreUtil;
import com.hb.file.dispatch.service.PlatformConfigService;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class StreamCopyService {
    private final String channel;
    private final Integer inputPlatform;
    private final Integer outputPlatform;
    private final PlatformConfigService platformConfigService;

    public StreamCopyService(String channel, Integer inputPlatform, Integer outputPlatform, PlatformConfigService platformConfigService) {
        this.channel = channel;
        this.inputPlatform = inputPlatform;
        this.outputPlatform = outputPlatform;
        this.platformConfigService = platformConfigService;
    }

    public void copy(String source, String target) throws CopyException {
        StoreUtil inputStoreUtil = null;
        StoreUtil outputStoreUtil = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            inputStoreUtil = getInputStoreUtil();
            is = inputStoreUtil.downloadInputStream(source);

            outputStoreUtil = getOutputStoreUtil();
            outputStoreUtil.uploadInputStream(target, is);
        } catch (Exception e) {
            log.error("Stream Copy fail, ", e);
            throw new CopyException(e.getMessage());
        } finally {
            IoUtil.close(is);
            IoUtil.close(os);
            if (inputStoreUtil != null) {
                inputStoreUtil.destroy();
            }
            if (outputStoreUtil != null) {
                outputStoreUtil.destroy();
            }
        }

    }


    private StoreUtil getInputStoreUtil() {
        AbstractPlatformService platformStreamService = switchPlatform(this.inputPlatform);
        platformStreamService.setPlatformConfigService(platformConfigService);
        return platformStreamService.getStoreUtil(this.channel);
    }

    private StoreUtil getOutputStoreUtil() {
        AbstractPlatformService platformStreamService = switchPlatform(this.outputPlatform);
        platformStreamService.setPlatformConfigService(platformConfigService);
        return platformStreamService.getStoreUtil(this.channel);
    }

    private AbstractPlatformService switchPlatform(Integer platform) {
        AbstractPlatformService platformStreamService;
        switch (platform) {
            case 1:
                platformStreamService = new ObsStreamService();
                break;
            case 2:
                platformStreamService = new OssStreamService();
                break;
            case 3:
                platformStreamService = new FtpStreamService();
                break;
            case 4:
                platformStreamService = new MinioStreamService();
                break;
            default:
                throw new BusinessException(BusInessExceptionEnum.PLATFORM_NOT_SUPPORT);
        }
        return platformStreamService;
    }

}
