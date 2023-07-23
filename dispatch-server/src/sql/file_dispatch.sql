-- 渠道配置表
CREATE TABLE `channel_config`
(
    `id`           bigint                                                 NOT NULL AUTO_INCREMENT,
    `channel`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '渠道',
    `channel_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '渠道名',
    `is_enable`    tinyint unsigned NOT NULL DEFAULT '0' COMMENT '启用状态 1= 启用 0=禁用',
    `create_time`  datetime                                               NOT NULL COMMENT '创建时间',
    `update_time`  datetime                                               NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='渠道配置表';

-- 平台配置表
CREATE TABLE `platform_config`
(
    `id`          bigint                                                NOT NULL AUTO_INCREMENT,
    `platform_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台id',
    `parameter`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '参数名',
    `value`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '参数值',
    `create_time` datetime                                              NOT NULL COMMENT '创建时间',
    `update_time` datetime                                              NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='平台配置表';


-- 平台任务表
CREATE TABLE `dispatch_task`
(
    `id`                 bigint                                                  NOT NULL AUTO_INCREMENT,
    `channel`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '渠道',
    `input_platform_id`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '输入平台id',
    `output_platform_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '输出平台id',
    `streaming_no`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '流水号',
    `status`             integer(2) unsigned NOT NULL COMMENT '任务状态 ',
    `message`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '任务描述',
    `source_url`         varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '源文件地址',
    `md5`                varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '文件md5',
    `upload_dir`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '上传后的文件位置',
    `forced_injection`   tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否强制注入 1= 是 0=否',

    `create_time`        datetime                                                NOT NULL COMMENT '创建时间',
    `update_time`        datetime                                                NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='平台任务表';


-- 任务优先级表
CREATE TABLE `task_priority`
(
    `id`           bigint                                                NOT NULL AUTO_INCREMENT,
    `channel`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '渠道',
    `priority`     integer(2) unsigned NOT NULL COMMENT '渠道任务优先级 ',
    `group`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务分组',
    `group_order`  integer(2) unsigned NOT NULL COMMENT '任务分组排序 ',
    `streaming_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '流水号',
    `status`       integer(2) unsigned NOT NULL COMMENT '任务状态 ',

    `create_time`  datetime                                              NOT NULL COMMENT '创建时间',
    `update_time`  datetime                                              NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='任务优先级表';

-- 渠道平台关联表
CREATE TABLE `channel_platform_rel`
(
    `id`          bigint                                                NOT NULL AUTO_INCREMENT,
    `channel`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '渠道',
    `platform`    tinyint(1) unsigned NOT NULL COMMENT '平台 ',
    `platform_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '平台id',

    `create_time` datetime                                              NOT NULL COMMENT '创建时间',
    `update_time` datetime                                              NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='渠道平台关联表';
