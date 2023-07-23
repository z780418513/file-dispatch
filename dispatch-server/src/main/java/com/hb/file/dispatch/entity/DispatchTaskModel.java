package com.hb.file.dispatch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dispatch_task")
public class DispatchTaskModel extends BaseModel {

    private String channel;
    /**
     * 输入渠道
     */
    private String inputPlatformId;
    /**
     * 输出渠道
     */
    private String outputPlatformId;

    // 任务流水号，唯一
    private String streamingNo;

    // 任务状态
    private Integer status;
    // 任务状态的描述信息，用于描述下载失败/上传失败等的具体原因
    private String message;
    // 源文件地址
    private String sourceUrl;
    /**
     * 源文件md5值
     */
    private String md5;
    // 上传后的文件位置
    private String uploadDir;

    // 强制注入
    private Boolean forcedInjection;

}
