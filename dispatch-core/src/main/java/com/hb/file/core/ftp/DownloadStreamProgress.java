package com.hb.file.core.ftp;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.NumberUtil;
import com.hb.file.core.enums.ProgressEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;


import java.util.List;
import java.util.Objects;


/**
 * 下载进度条类
 *
 * @author hanbaolaoba
 */
public class DownloadStreamProgress implements StreamProgress {

    private static final Logger logger = LoggerFactory.getLogger(DownloadStreamProgress.class);


    /**
     * 下载流水号
     */
    private final String streamingNo;

    /**
     * 到达指定下载进度打印进度
     */
    @Nullable
    private final List<ProgressEnum> progressEnumList;

    /**
     * 当前进度
     */
    private double currentProgress;

    /**
     * 下载开始时间
     */
    private Long startTime = 0L;

    public DownloadStreamProgress( String streamingNo, @Nullable List<ProgressEnum> progressEnumList) {
        this.streamingNo = streamingNo;
        this.progressEnumList = progressEnumList;
    }

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
        logger.debug("Start to Download, StreamingNo: {}",  streamingNo);

    }

    /**
     * 到达指定进度打印日志
     *
     * @param total        总大小，如果未知为 -1或者{@link Long#MAX_VALUE}
     * @param progressSize 已经进行的大小
     */
    @Override
    public void progress(long total, long progressSize) {
        if (progressEnumList == null || progressEnumList.isEmpty()) {
            return;
        }
        ProgressEnum progressEnum = progressEnumList.get(0);
        if (Objects.isNull(progressEnum)) {
            return;
        }
        double div = NumberUtil.div(progressSize, total);
        currentProgress = progressEnum.getProgress();
        if (div >= currentProgress) {
            logger.debug("Downloading, StreamingNo: {}, CurrentProgress: {}",  streamingNo, currentProgress);
            progressEnumList.remove(progressEnum);
        }


    }

    @Override
    public void finish() {
        logger.debug("Download Finish, StreamingNo: {}, Cost: {}",  streamingNo, System.currentTimeMillis() - startTime);
    }

    public double getCurrentProgress() {
        return currentProgress;
    }
}
