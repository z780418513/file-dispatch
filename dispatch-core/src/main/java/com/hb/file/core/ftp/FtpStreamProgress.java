package com.hb.file.core.ftp;

import cn.hutool.core.io.StreamProgress;

public class FtpStreamProgress implements StreamProgress {
    /**
     * 缓冲区
     */
    private final int bufferSize;

    /**
     * ftpProgressListener
     */
    private final FtpProgressListener ftpProgressListener;


    /**
     * 开始检查点
     */
    private long startCheckpoint;
    /**
     * 最后一次检查点
     */
    private long lastCheckpoint;
    /**
     * 新传输的字节
     */
    protected long newlyTransferredBytes;
    /**
     * 文件总大小
     */
    private long total;


    public FtpStreamProgress(int bufferSize, FtpProgressListener ftpProgressListener) {
        this.bufferSize = bufferSize;
        this.ftpProgressListener = ftpProgressListener;
    }

    @Override
    public void start() {
        startCheckpoint = System.currentTimeMillis();
    }

    @Override
    public void progress(long total, long progressSize) {
        this.progressChanged(total, progressSize);
    }

    @Override
    public void finish() {
        progressChanged(this.total, this.total);
    }

    private void progressChanged(long total, long progressSize) {
        if (this.total == 0L) {
            this.total = total;
        }

        newlyTransferredBytes += bufferSize;
        long now = System.currentTimeMillis();
        long swapIntervalTime = now - lastCheckpoint;
        // 间隔1秒计算一次
        if (swapIntervalTime > 1000) {
            FtpProgressStatus status = new FtpProgressStatus(this.newlyTransferredBytes, progressSize,
                    total, now - this.lastCheckpoint, now - this.startCheckpoint);
            long lastSecondBytes = (long) (newlyTransferredBytes / (swapIntervalTime / 1000.0));
            status.setInstantaneousSpeed(lastSecondBytes);
            ftpProgressListener.progressChanged(status);
            this.newlyTransferredBytes = 0;
            this.lastCheckpoint = now;
        }
    }

}

