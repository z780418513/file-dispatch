package com.hb.file.dispatch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 每条处理成功的任务,都会有一条文件记录表
 *
 * @author hanbaolaoba
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("file_record_log")
public class FileRecordLogModel extends BaseModel {

    /**
     * md5值
     */
    private String md5;

    /**
     * 任务流水号，唯一
     */
    private String streamingNo;

    /**
     * 目标服务器文件存储地址
     */
    private String outputFileUrl;


}
