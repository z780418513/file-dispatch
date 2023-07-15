package com.hb.file.core.ftp;


public class FtpProgressStatus {
    private final long newlyTransferredBytes;
    private final long transferredBytes;
    private final long totalBytes;
    private final long intervalMilliseconds;
    private final long totalMilliseconds;
    private long instantaneousSpeed;

    public FtpProgressStatus(long newlyTransferredBytes, long transferredBytes, long totalBytes,
                             long intervalMilliseconds, long totalMilliseconds) {
        this.newlyTransferredBytes = newlyTransferredBytes;
        this.transferredBytes = transferredBytes;
        this.totalBytes = totalBytes;
        this.intervalMilliseconds = intervalMilliseconds;
        this.totalMilliseconds = totalMilliseconds;
    }

    public double getInstantaneousSpeed() {
        if (this.intervalMilliseconds <= 0) {
            return -1d;
        }
        return this.instantaneousSpeed;
    }


    public double getAverageSpeed() {
        if (this.totalMilliseconds <= 0) {
            return -1d;
        }
        return this.transferredBytes * 1000.0d / this.totalMilliseconds;
    }

    public int getTransferPercentage() {
        if (this.totalBytes < 0) {
            return -1;
        } else if (this.totalBytes == 0) {
            return 100;
        }
        return (int) (this.transferredBytes * 100 / this.totalBytes);
    }

    public long getNewlyTransferredBytes() {
        return this.newlyTransferredBytes;
    }

    public long getTransferredBytes() {
        return this.transferredBytes;
    }

    public long getTotalBytes() {
        return this.totalBytes;
    }


    public void setInstantaneousSpeed(long instantaneousSpeed) {
        this.instantaneousSpeed = instantaneousSpeed;
    }

}
