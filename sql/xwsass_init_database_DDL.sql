# Dump of table customer
# ------------------------------------------------------------

CREATE TABLE `customer`
(
  `id`                bigint(20)                     NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `tenant_id`         varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `member_user_id`    varchar(64)                    NOT NULL COMMENT '成员user_id',
  `user_id`           varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '企业微信外部成员UserID',
  `customer_name`     varchar(64)                    NOT NULL COMMENT '客户名称',
  `avatar`            varchar(512) CHARACTER SET utf8         DEFAULT NULL COMMENT '头像url',
  `customer_type`     int(2)                                  DEFAULT '0' COMMENT '外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户',
  `gender`            int(1)                                  DEFAULT '0' COMMENT '性别。0表示未定义，1表示男性，2表示女性',
  `union_id`          varchar(128) CHARACTER SET utf8         DEFAULT NULL COMMENT '部联系人在微信开放平台的唯一身份标识（微信unionid）',
  `customer_position` varchar(128) CHARACTER SET utf8         DEFAULT NULL COMMENT '职务信息',
  `corp_name`         varchar(256) CHARACTER SET utf8         DEFAULT NULL COMMENT '公司简称',
  `corp_full_name`    varchar(512) CHARACTER SET utf8         DEFAULT NULL COMMENT '主体名称',
  `external_profile`  longtext COMMENT '自定义展示信息',
  `follow_user`       longtext COMMENT '跟进成员信息',
  `status`            varchar(32) CHARACTER SET utf8 NOT NULL COMMENT 'INACTIVE – 非活跃；ACTIVE – 活跃；',
  `task_id`           varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '更新任务流水号',
  `extension`         varchar(4000) CHARACTER SET utf8        DEFAULT NULL COMMENT '扩展',
  `gmt_created`       timestamp                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`       varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '创建人',
  `gmt_updated`       timestamp                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`       varchar(64) CHARACTER SET utf8          DEFAULT NULL COMMENT '修改人',
  `deleted`           tinyint(4)                     NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通讯录数据信息,成员客户表';



# Dump of table customer_received
# ------------------------------------------------------------

CREATE TABLE `customer_received`
(
  `id`                bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `task_id`           varchar(64) NOT NULL COMMENT '同任务执行流水号',
  `tenant_id`         varchar(64) NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `member_user_id`    varchar(64) NOT NULL COMMENT '成员user_id',
  `user_id`           varchar(64) NOT NULL COMMENT '企业微信外部成员UserID',
  `customer_name`     varchar(64) NOT NULL COMMENT '客户名称',
  `avatar`            varchar(512)         DEFAULT NULL COMMENT '头像url',
  `customer_type`     int(2)               DEFAULT '0' COMMENT '外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户',
  `gender`            int(1)               DEFAULT '0' COMMENT '性别。0表示未定义，1表示男性，2表示女性',
  `union_id`          varchar(128)         DEFAULT NULL COMMENT '部联系人在微信开放平台的唯一身份标识（微信unionid）',
  `customer_position` varchar(128)         DEFAULT NULL COMMENT '职务信息',
  `corp_name`         varchar(256)         DEFAULT NULL COMMENT '公司简称',
  `corp_full_name`    varchar(512)         DEFAULT NULL COMMENT '主体名称',
  `external_profile`  longtext COMMENT '自定义展示信息',
  `follow_user`       longtext COMMENT '跟进成员信息',
  `extension`         varchar(4000)        DEFAULT NULL COMMENT '扩展',
  `gmt_created`       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`       varchar(64) NOT NULL COMMENT '创建人',
  `gmt_updated`       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`       varchar(64)          DEFAULT NULL COMMENT '修改人',
  `deleted`           tinyint(4)  NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_org_id_member_user_id_user_id_task_id` (`tenant_id`, `user_id`, `task_id`, `member_user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通讯录数据信息,成员客户临时表';



# Dump of table department
# ------------------------------------------------------------

CREATE TABLE `department`
(
  `id`               bigint(20)                      NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `tenant_id`        varchar(64) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `department_id`    bigint(20)                      NOT NULL COMMENT '企业微信部门id',
  `department_name`  varchar(256) CHARACTER SET utf8 NOT NULL COMMENT '部门名称',
  `english_name`     varchar(256) CHARACTER SET utf8          DEFAULT NULL COMMENT '部门英文名称',
  `parent_id`        bigint(20)                      NOT NULL COMMENT '企业微信上级部门id',
  `department_order` bigint(20)                      NOT NULL COMMENT '同级别次序',
  `status`           varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '状态',
  `task_id`          varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '更新任务流水号',
  `extension`        varchar(4000) CHARACTER SET utf8         DEFAULT NULL COMMENT '扩展',
  `gmt_created`      timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`      varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '创建人',
  `gmt_updated`      timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`      varchar(64) CHARACTER SET utf8           DEFAULT NULL COMMENT '修改人',
  `deleted`          tinyint(4)                      NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_org_id_department_id` (`tenant_id`, `department_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通讯录数据信息,部门表';



# Dump of table department_received
# ------------------------------------------------------------

CREATE TABLE `department_received`
(
  `id`               bigint(20)                      NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `task_id`          varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '同任务执行流水号',
  `tenant_id`        varchar(64) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `department_id`    bigint(20)                      NOT NULL COMMENT '企业微信部门id',
  `department_name`  varchar(256) CHARACTER SET utf8 NOT NULL COMMENT '部门名称',
  `english_name`     varchar(256) CHARACTER SET utf8          DEFAULT NULL COMMENT '部门英文名称',
  `parent_id`        bigint(20)                      NOT NULL COMMENT '企业微信上级部门id',
  `department_order` bigint(20)                      NOT NULL COMMENT '同级别次序',
  `extension`        varchar(4000) CHARACTER SET utf8         DEFAULT NULL COMMENT '扩展',
  `gmt_created`      timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`      varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '创建人',
  `gmt_updated`      timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`      varchar(64) CHARACTER SET utf8           DEFAULT NULL COMMENT '修改人',
  `deleted`          tinyint(4)                      NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_org_id_department_id_task_id` (`task_id`, `tenant_id`, `department_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通讯录数据信息,部门临时表';



# Dump of table group_chat
# ------------------------------------------------------------

CREATE TABLE `group_chat`
(
  `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `tenant_id`   varchar(64) NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `chat_name`   varchar(255)         DEFAULT NULL COMMENT '群聊名称',
  `chat_id`     varchar(64) NOT NULL COMMENT '群聊id',
  `owner`       varchar(64) NOT NULL COMMENT '群主',
  `create_time` varchar(64) NOT NULL COMMENT '创建时间',
  `notice`      text COMMENT '群公告',
  `member_list` text        NOT NULL COMMENT '群成员',
  `status`      int(1)               DEFAULT '0' COMMENT '客户群状态。0 - 正常1 - 跟进人离职2 - 离职继承中3 - 离职继承完成',
  `task_id`     varchar(64) NOT NULL COMMENT '更新任务流水号',
  `extension`   varchar(4000)        DEFAULT NULL COMMENT '扩展',
  `gmt_created` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator` varchar(64) NOT NULL COMMENT '创建人',
  `gmt_updated` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater` varchar(64)          DEFAULT NULL COMMENT '修改人',
  `deleted`     tinyint(4)  NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_chat_id` (`chat_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='群聊详情表';



# Dump of table member
# ------------------------------------------------------------

CREATE TABLE `member`
(
  `id`                 bigint(20)                      NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `tenant_id`          varchar(64) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `user_id`            varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '企业微信成员UserID',
  `member_name`        varchar(64)                     NOT NULL COMMENT '成员名称',
  `mobile`             varchar(32) CHARACTER SET utf8           DEFAULT NULL COMMENT '手机号码',
  `department_id_list` varchar(512) CHARACTER SET utf8 NOT NULL COMMENT '成员所属部门id列表,格式如：[1, 0]',
  `order_list`         varchar(512) CHARACTER SET utf8 NOT NULL COMMENT '部门内的排序列表,格式如：[1, 0] 与以上成员所属部门列表一一对应；',
  `member_position`    varchar(128) CHARACTER SET utf8          DEFAULT NULL COMMENT '职务信息',
  `gender`             int(1)                                   DEFAULT '0' COMMENT '性别。0表示未定义，1表示男性，2表示女性',
  `mail`               varchar(128) CHARACTER SET utf8          DEFAULT NULL COMMENT '邮箱',
  `is_leader_in_dept`  varchar(512) CHARACTER SET utf8          DEFAULT NULL COMMENT '格式如：[1, 0] 与以上成员所属部门列表一一对应；',
  `avatar`             varchar(512) CHARACTER SET utf8          DEFAULT NULL COMMENT '头像url',
  `thumb_avatar`       varchar(512) CHARACTER SET utf8          DEFAULT NULL COMMENT '头像缩略图url',
  `telephone`          varchar(32) CHARACTER SET utf8           DEFAULT NULL COMMENT '座机',
  `alias`              varchar(128) CHARACTER SET utf8          DEFAULT NULL COMMENT '别名',
  `status`             int(2)                          NOT NULL COMMENT '对应API接口status，激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。',
  `ext_attr`           mediumtext CHARACTER SET utf8 COMMENT '扩展属性',
  `qr_code`            varchar(512) CHARACTER SET utf8          DEFAULT NULL COMMENT '员工个人二维码',
  `external_profile`   mediumtext CHARACTER SET utf8 COMMENT '成员对外属性，字段详情见对外属性',
  `external_position`  varchar(128) CHARACTER SET utf8          DEFAULT NULL COMMENT '对外职务',
  `address`            varchar(512) CHARACTER SET utf8          DEFAULT NULL COMMENT '地址',
  `hide_mobile`        int(2)                                   DEFAULT NULL COMMENT '是否隐藏手机号',
  `english_name`       varchar(128) CHARACTER SET utf8          DEFAULT NULL COMMENT '英文名称',
  `open_userid`        varchar(128) CHARACTER SET utf8          DEFAULT NULL COMMENT '全局唯一。对于同一个服务商，不同应用获取到企业内同一个成员的open_userid是相同的',
  `main_department`    bigint(20)                      NOT NULL COMMENT '主部门',
  `member_status`      varchar(32) CHARACTER SET utf8  NOT NULL COMMENT 'INACTIVE – 非活跃；ACTIVE – 活跃；',
  `task_id`            varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '更新任务流水号',
  `extension`          varchar(4000) CHARACTER SET utf8         DEFAULT NULL COMMENT '扩展',
  `gmt_created`        timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`        varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '创建人',
  `gmt_updated`        timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`        varchar(64) CHARACTER SET utf8           DEFAULT NULL COMMENT '修改人',
  `deleted`            tinyint(4)                      NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_org_id_user_id` (`tenant_id`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通讯录数据信息,成员表';



# Dump of table member_received
# ------------------------------------------------------------

CREATE TABLE `member_received`
(
  `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `task_id`            varchar(64)  NOT NULL COMMENT '同任务执行流水号',
  `tenant_id`          varchar(64)  NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `user_id`            varchar(64)  NOT NULL COMMENT '企业微信成员UserID',
  `member_name`        varchar(64)  NOT NULL COMMENT '成员名称',
  `mobile`             varchar(32)           DEFAULT NULL COMMENT '手机号码',
  `department_id_list` varchar(512) NOT NULL COMMENT '成员所属部门id列表,格式如：[1, 0]',
  `order_list`         varchar(512) NOT NULL COMMENT '部门内的排序列表,格式如：[1, 0] 与以上成员所属部门列表一一对应；',
  `member_position`    varchar(128)          DEFAULT NULL COMMENT '职务信息',
  `gender`             int(1)                DEFAULT '0' COMMENT '性别。0表示未定义，1表示男性，2表示女性',
  `mail`               varchar(128)          DEFAULT NULL COMMENT '邮箱',
  `is_leader_in_dept`  varchar(512)          DEFAULT NULL COMMENT '格式如：[1, 0] 与以上成员所属部门列表一一对应；',
  `avatar`             varchar(512)          DEFAULT NULL COMMENT '头像url',
  `thumb_avatar`       varchar(512)          DEFAULT NULL COMMENT '头像缩略图url',
  `telephone`          varchar(32)           DEFAULT NULL COMMENT '座机',
  `alias`              varchar(128)          DEFAULT NULL COMMENT '别名',
  `status`             int(2)       NOT NULL COMMENT '对应API接口status，激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。',
  `ext_attr`           mediumtext COMMENT '扩展属性',
  `qr_code`            varchar(512)          DEFAULT NULL COMMENT '员工个人二维码',
  `external_profile`   mediumtext COMMENT '成员对外属性，字段详情见对外属性',
  `external_position`  varchar(128)          DEFAULT NULL COMMENT '对外职务',
  `address`            varchar(512)          DEFAULT NULL COMMENT '地址',
  `hide_mobile`        int(2)                DEFAULT NULL COMMENT '是否隐藏手机号',
  `english_name`       varchar(128)          DEFAULT NULL COMMENT '英文名称',
  `open_userid`        varchar(128)          DEFAULT NULL COMMENT '全局唯一。对于同一个服务商，不同应用获取到企业内同一个成员的open_userid是相同的',
  `main_department`    bigint(20)   NOT NULL COMMENT '主部门',
  `extension`          varchar(4000)         DEFAULT NULL COMMENT '扩展',
  `gmt_created`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`        varchar(64)  NOT NULL COMMENT '创建人',
  `gmt_updated`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`        varchar(64)           DEFAULT NULL COMMENT '修改人',
  `deleted`            tinyint(4)   NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_org_id_user_id_task_id` (`tenant_id`, `user_id`, `task_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通讯录数据信息,成员临时表';



# Dump of table msg_record
# ------------------------------------------------------------

CREATE TABLE `msg_record`
(
  `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `tenant_id`    varchar(64)  NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `seq_id`       bigint(20)   NOT NULL COMMENT '对应获取消息时，每条消息顺序号',
  `msg_id`       varchar(64)  NOT NULL COMMENT '消息的唯一标识，企业可以使用此字段进行消息去重',
  `action`       varchar(32)  NOT NULL COMMENT '消息动作，目前有send(发送消息)/recall(撤回消息)/switch(切换企业日志)三种类型。',
  `from_user_id` varchar(128) NOT NULL COMMENT '消息发送方id。',
  `to_user_id`   text COMMENT '消息接收方列表',
  `room_id`      varchar(128) NOT NULL DEFAULT '' COMMENT 'roomid，群聊消息的群id。如果是单聊则为空。',
  `msg_time`     varchar(128) NOT NULL COMMENT 'msgtime，消息发送时间戳，utc时间，ms单位。',
  `msg_type`     varchar(32)           DEFAULT NULL COMMENT 'msgtype， String类型参考企业微信会话API，有20多种类型',
  `content`      text COMMENT 'content，消息内容',
  `task_id`      varchar(64)  NOT NULL COMMENT '更新任务流水号',
  `extension`    varchar(4000)         DEFAULT NULL COMMENT '扩展',
  `gmt_created`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`  varchar(64)  NOT NULL COMMENT '创建人',
  `gmt_updated`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`  varchar(64)           DEFAULT NULL COMMENT '修改人',
  `deleted`      tinyint(4)   NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_msg_id` (`msg_id`),
  KEY `idx_from_user_id` (`from_user_id`) USING BTREE,
  KEY `idx_room_id` (`room_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='会话信息表';



# Dump of table oplog
# ------------------------------------------------------------

CREATE TABLE `oplog`
(
  `id`          bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id`   varchar(32)         DEFAULT '',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `ip`          varchar(64)         DEFAULT '' COMMENT '操作对应的IP地址',
  `operation`   varchar(1024)       DEFAULT '' COMMENT '操作对应的类型：CREATE/DELETE/QUERY/UPDATE',
  `account`     varchar(128)        DEFAULT '' COMMENT '操作账号',
  `method`      varchar(512)        DEFAULT '' COMMENT '操作对应的接口方法',
  `uri`         varchar(256)        DEFAULT '' COMMENT '操作路径',
  `http_method` varchar(256)        DEFAULT '' COMMENT 'http请求方法',
  `ua`          varchar(512)        DEFAULT '' COMMENT '使用的系统',
  `params`      text COMMENT '请求参数',
  `result`      text COMMENT '执行结果',
  `gmt_creator` varchar(64)         DEFAULT '' COMMENT '创建人',
  `gmt_created` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater` varchar(64)         DEFAULT '' COMMENT '更新人',
  `gmt_updated` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`   varchar(4000)       DEFAULT NULL COMMENT '扩展字段',
  `deleted`     tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除,1表示删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='操作日志记录';



# Dump of table organization
# ------------------------------------------------------------

CREATE TABLE `organization`
(
  `id`          bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '组织机构id',
  `code`        varchar(64)   NOT NULL COMMENT '组织机构编号',
  `tenant_id`   varchar(64)            DEFAULT NULL COMMENT '租户编码',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `parent_id`   bigint(20)             DEFAULT '0' COMMENT '父级组织机构id',
  `name`        varchar(128)  NOT NULL COMMENT '组织机构名字',
  `org_type`    varchar(16)   NOT NULL COMMENT '组织结构类型',
  `is_leaf`     tinyint(1)    NOT NULL DEFAULT '0' COMMENT '是否叶子组织机构',
  `state`       varchar(16)   NOT NULL COMMENT '状态',
  `remark`      varchar(256)  NOT NULL DEFAULT '' COMMENT '备注',
  `gmt_creator` varchar(64)   NOT NULL DEFAULT '' COMMENT '创建人',
  `gmt_created` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater` varchar(64)   NOT NULL DEFAULT '' COMMENT '更新人',
  `gmt_updated` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`   varchar(4000) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `deleted`     tinyint(1)    NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除；1表示删除	',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_tenant_id_code` (`tenant_id`, `code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='组织结构表';





# Dump of table qrtz_job_details
# ------------------------------------------------------------

CREATE TABLE `qrtz_job_details`
(
  `SCHED_NAME`        varchar(120) NOT NULL,
  `JOB_NAME`          varchar(200) NOT NULL,
  `JOB_GROUP`         varchar(200) NOT NULL,
  `DESCRIPTION`       varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME`    varchar(250) NOT NULL,
  `IS_DURABLE`        varchar(1)   NOT NULL,
  `IS_NONCONCURRENT`  varchar(1)   NOT NULL,
  `IS_UPDATE_DATA`    varchar(1)   NOT NULL,
  `REQUESTS_RECOVERY` varchar(1)   NOT NULL,
  `JOB_DATA`          blob,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`, `REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`, `JOB_GROUP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储每一个已配置的 Job 的详细信息';





# Dump of table qrtz_triggers
# ------------------------------------------------------------

CREATE TABLE `qrtz_triggers`
(
  `SCHED_NAME`     varchar(120) NOT NULL,
  `TRIGGER_NAME`   varchar(200) NOT NULL,
  `TRIGGER_GROUP`  varchar(200) NOT NULL,
  `JOB_NAME`       varchar(200) NOT NULL,
  `JOB_GROUP`      varchar(200) NOT NULL,
  `DESCRIPTION`    varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13)   DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13)   DEFAULT NULL,
  `PRIORITY`       int(11)      DEFAULT NULL,
  `TRIGGER_STATE`  varchar(16)  NOT NULL,
  `TRIGGER_TYPE`   varchar(8)   NOT NULL,
  `START_TIME`     bigint(13)   NOT NULL,
  `END_TIME`       bigint(13)   DEFAULT NULL,
  `CALENDAR_NAME`  varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR`  smallint(2)  DEFAULT NULL,
  `JOB_DATA`       blob,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`, `JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`, `CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`, `TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`, `TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`, `NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`,
                                       `TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储已配置的 Trigger 的信息';



# Dump of table qrtz_blob_triggers
# ------------------------------------------------------------

CREATE TABLE `qrtz_blob_triggers`
(
  `SCHED_NAME`    varchar(120) NOT NULL,
  `TRIGGER_NAME`  varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA`     blob,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，<span style="color:#800080;">JobStore</span> 并不知道如何存储实例的时候)';



# Dump of table qrtz_calendars
# ------------------------------------------------------------

CREATE TABLE `qrtz_calendars`
(
  `SCHED_NAME`    varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR`      blob         NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='以 Blob 类型存储 Quartz 的 Calendar 信息';



# Dump of table qrtz_cron_triggers
# ------------------------------------------------------------

CREATE TABLE `qrtz_cron_triggers`
(
  `SCHED_NAME`      varchar(120) NOT NULL,
  `TRIGGER_NAME`    varchar(200) NOT NULL,
  `TRIGGER_GROUP`   varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID`    varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储 Cron Trigger，包括 Cron 表达式和时区信息';



# Dump of table qrtz_fired_triggers
# ------------------------------------------------------------

CREATE TABLE `qrtz_fired_triggers`
(
  `SCHED_NAME`        varchar(120) NOT NULL,
  `ENTRY_ID`          varchar(95)  NOT NULL,
  `TRIGGER_NAME`      varchar(200) NOT NULL,
  `TRIGGER_GROUP`     varchar(200) NOT NULL,
  `INSTANCE_NAME`     varchar(200) NOT NULL,
  `FIRED_TIME`        bigint(13)   NOT NULL,
  `SCHED_TIME`        bigint(13)   NOT NULL,
  `PRIORITY`          int(11)      NOT NULL,
  `STATE`             varchar(16)  NOT NULL,
  `JOB_NAME`          varchar(200) DEFAULT NULL,
  `JOB_GROUP`         varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT`  varchar(1)   DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1)   DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`, `INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`, `JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息';




# Dump of table qrtz_locks
# ------------------------------------------------------------

CREATE TABLE `qrtz_locks`
(
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME`  varchar(40)  NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储程序的悲观锁的信息(假如使用了悲观锁)';



# Dump of table qrtz_paused_trigger_grps
# ------------------------------------------------------------

CREATE TABLE `qrtz_paused_trigger_grps`
(
  `SCHED_NAME`    varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储已暂停的 Trigger 组的信息';



# Dump of table qrtz_scheduler_state
# ------------------------------------------------------------

CREATE TABLE `qrtz_scheduler_state`
(
  `SCHED_NAME`        varchar(120) NOT NULL,
  `INSTANCE_NAME`     varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13)   NOT NULL,
  `CHECKIN_INTERVAL`  bigint(13)   NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储少量的有关 Scheduler 的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)';



# Dump of table qrtz_simple_triggers
# ------------------------------------------------------------

CREATE TABLE `qrtz_simple_triggers`
(
  `SCHED_NAME`      varchar(120) NOT NULL,
  `TRIGGER_NAME`    varchar(200) NOT NULL,
  `TRIGGER_GROUP`   varchar(200) NOT NULL,
  `REPEAT_COUNT`    bigint(7)    NOT NULL,
  `REPEAT_INTERVAL` bigint(12)   NOT NULL,
  `TIMES_TRIGGERED` bigint(10)   NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储简单的 Trigger，包括重复次数，间隔，以及已触的次数';



# Dump of table qrtz_simprop_triggers
# ------------------------------------------------------------

CREATE TABLE `qrtz_simprop_triggers`
(
  `SCHED_NAME`    varchar(120) NOT NULL,
  `TRIGGER_NAME`  varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1`    varchar(512)   DEFAULT NULL,
  `STR_PROP_2`    varchar(512)   DEFAULT NULL,
  `STR_PROP_3`    varchar(512)   DEFAULT NULL,
  `INT_PROP_1`    int(11)        DEFAULT NULL,
  `INT_PROP_2`    int(11)        DEFAULT NULL,
  `LONG_PROP_1`   bigint(20)     DEFAULT NULL,
  `LONG_PROP_2`   bigint(20)     DEFAULT NULL,
  `DEC_PROP_1`    decimal(13, 4) DEFAULT NULL,
  `DEC_PROP_2`    decimal(13, 4) DEFAULT NULL,
  `BOOL_PROP_1`   varchar(1)     DEFAULT NULL,
  `BOOL_PROP_2`   varchar(1)     DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;



# Dump of table resource
# ------------------------------------------------------------

CREATE TABLE `resource`
(
  `id`            bigint(20)                   NOT NULL AUTO_INCREMENT,
  `tenant_id`     varchar(32)                           DEFAULT NULL COMMENT '租户编码',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `code`          varchar(32)                           DEFAULT NULL COMMENT '资源编码',
  `parent_id`     bigint(20)                   NOT NULL COMMENT '父级资源id',
  `resource_type` varchar(16)                           DEFAULT NULL COMMENT 'menu:菜单,function:功能',
  `name`          varchar(128)                 NOT NULL COMMENT '资源名称',
  `authority`     varchar(128)                 NOT NULL DEFAULT '' COMMENT '对应的权限值,用于权限判断',
  `url`           varchar(128)                 NOT NULL DEFAULT '' COMMENT '资源路径',
  `root`          tinyint(1) unsigned zerofill NOT NULL COMMENT '是否根目录',
  `gmt_updated`   timestamp                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `gmt_creator`   varchar(64)                  NOT NULL DEFAULT '' COMMENT '创建人',
  `gmt_created`   timestamp                    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater`   varchar(64)                  NOT NULL DEFAULT '' COMMENT '更新人',
  `extension`     varchar(4000)                NOT NULL DEFAULT '' COMMENT '扩展字段',
  `deleted`       tinyint(1)                   NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除；1表示删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_code` (`code`),
  KEY `uk_url` (`url`),
  KEY `uk_auth` (`authority`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='资源';



# Dump of table role
# ------------------------------------------------------------

CREATE TABLE `role`
(
  `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
  `tenant_id`   varchar(32)           DEFAULT NULL COMMENT '租户编码',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `code`        varchar(32)           DEFAULT NULL COMMENT '角色编码',
  `name`        varchar(128) NOT NULL COMMENT '角色名称',
  `role_type`   varchar(64)  NOT NULL COMMENT '角色类型',
  `gmt_creator` varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人',
  `gmt_created` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater` varchar(64)  NOT NULL DEFAULT '' COMMENT '更新人',
  `gmt_updated` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`   varchar(4000)         DEFAULT NULL COMMENT '扩展字段',
  `deleted`     tinyint(1)   NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除,1表示删除',
  PRIMARY KEY (`id`),
  KEY `uq_code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色';



# Dump of table role_resource
# ------------------------------------------------------------

CREATE TABLE `role_resource`
(
  `id`            bigint(20)    NOT NULL AUTO_INCREMENT,
  `tenant_id`     varchar(32)            DEFAULT '' COMMENT '租户编码',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `role_code`     varchar(32)   NOT NULL COMMENT '角色编码',
  `role_name`     varchar(128)  NOT NULL COMMENT '角色名称',
  `resource_code` varchar(32)   NOT NULL COMMENT '资源编码',
  `resource_name` varchar(128)  NOT NULL COMMENT '资源名称',
  `gmt_creator`   varchar(64)   NOT NULL DEFAULT '' COMMENT '创建人',
  `gmt_created`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater`   varchar(64)   NOT NULL DEFAULT '' COMMENT '更新人',
  `gmt_updated`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`     varchar(4000) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `deleted`       tinyint(1)    NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色与资源';



# Dump of table tags
# ------------------------------------------------------------

CREATE TABLE `tags`
(
  `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
  `tenant_id`   varchar(32)  NOT NULL,
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `code`        varchar(32)           DEFAULT NULL COMMENT '标签编码',
  `name`        varchar(128) NOT NULL COMMENT '标签名称',
  `description` varchar(512)          DEFAULT '' COMMENT '标签描述',
  `tag_type`    varchar(64)  NOT NULL COMMENT '标签类型,COMMON:普通标签，OTHER:其他标签',
  `gmt_creator` varchar(64)           DEFAULT '' COMMENT '创建人',
  `gmt_created` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater` varchar(64)           DEFAULT '' COMMENT '更新人',
  `gmt_updated` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`   varchar(4000)         DEFAULT NULL COMMENT '扩展字段',
  `deleted`     tinyint(1)   NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除,1表示删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='标签';



# Dump of table tags_relations
# ------------------------------------------------------------

CREATE TABLE `tags_relations`
(
  `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
  `tenant_id`   varchar(32)           DEFAULT NULL COMMENT '对应的租户ID',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `tag_id`      varchar(32)           DEFAULT NULL COMMENT '标签ID',
  `key_id`      varchar(128) NOT NULL COMMENT '标签对应的关系key_id',
  `key_name`    varchar(512) NOT NULL DEFAULT '' COMMENT '标签对应的关系key_name，customer: 对应客户\\nmsg:对应消息\\nother:其他',
  `description` varchar(512)          DEFAULT NULL COMMENT '标签对应的关系key_name',
  `gmt_creator` varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人',
  `gmt_created` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater` varchar(64)  NOT NULL DEFAULT '' COMMENT '更新人',
  `gmt_updated` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`   varchar(4000)         DEFAULT NULL COMMENT '扩展字段',
  `deleted`     tinyint(1)   NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除,1表示删除',
  PRIMARY KEY (`id`),
  KEY `uq_code` (`tag_id`, `key_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='标签与其他关系对应的表';



# Dump of table tenant
# ------------------------------------------------------------

CREATE TABLE `tenant`
(
  `id`                      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `tenant_id`               varchar(64)  NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `tenant_name`             varchar(256) NOT NULL DEFAULT '' COMMENT '机构名称',
  `corp_id`                 varchar(256) NOT NULL COMMENT '企业微信用户corpid， 加密保存',
  `address_list_secret`     varchar(256)          DEFAULT NULL COMMENT '企业微信通讯录secret， 加密保存',
  `chat_record_secret`      varchar(256)          DEFAULT NULL,
  `customer_contact_secret` varchar(256)          DEFAULT NULL COMMENT '企业微信客户联系secret， 加密保存',
  `private_key`             varchar(4000)         DEFAULT NULL COMMENT '会话解密私钥， 加密保存',
  `extension`               varchar(4000)         DEFAULT NULL COMMENT '扩展',
  `gmt_created`             timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`             varchar(64)  NOT NULL COMMENT '创建人',
  `gmt_updated`             timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`             varchar(64)           DEFAULT NULL COMMENT '修改人',
  `deleted`                 tinyint(4)   NOT NULL DEFAULT '0' COMMENT '删除标记',
  `state`                   varchar(16)  NOT NULL COMMENT '状态',
  `remark`                  varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_org_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='机构基础信息配置表';



# Dump of table tenant_sync_config
# ------------------------------------------------------------

CREATE TABLE `tenant_sync_config`
(
  `id`              bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `tenant_id`       varchar(64) NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `task_type`       varchar(32) NOT NULL COMMENT '任务类型',
  `cron_expression` varchar(64) NOT NULL,
  `fetched_seq_no`  bigint(20)  NOT NULL DEFAULT '0' COMMENT '对应企业微信消息序号seq，每次获取时递增；初始为0，每次获取时从此顺序号+1开始；',
  `count_ceiling`   int(4)      NOT NULL DEFAULT '1000' COMMENT '会话每次提取上限',
  `time_interval`   int(8)      NOT NULL DEFAULT '3' COMMENT '会话每次提取间隔(秒)',
  `status`          int(1)      NOT NULL DEFAULT '0',
  `lock_flag`       int(1)      NOT NULL DEFAULT '0' COMMENT '任务锁，为0表示未上锁，为1表示上锁',
  `lock_time`       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上锁时间',
  `extension`       varchar(4000)        DEFAULT NULL COMMENT '扩展',
  `gmt_created`     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`     varchar(64) NOT NULL COMMENT '创建人',
  `gmt_updated`     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`     varchar(64)          DEFAULT NULL COMMENT '修改人',
  `deleted`         int(1)      NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_org_id_task_type` (`tenant_id`, `task_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='机构数据同步任务配置';



# Dump of table tenant_sync_excp
# ------------------------------------------------------------

CREATE TABLE `tenant_sync_excp`
(
  `id`                 bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `excp_id`            varchar(64) NOT NULL COMMENT '异常流水号',
  `task_id`            varchar(64) NOT NULL COMMENT '同任务执行流水号',
  `tenant_id`          varchar(64) NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `task_type`          varchar(32) NOT NULL COMMENT '任务类型 CONTACT_SYNC – 通讯录同步； MESSAGE_SYNC  - 会话同步；',
  `excp_step`          varchar(32) NOT NULL COMMENT 'RECEIVE_STEP – 获取数据步骤 IMPORT_STEP  - 导入步骤',
  `excp_msg_start_seq` bigint(20)           DEFAULT '0' COMMENT '消息异常开始顺序号',
  `excp_msg_end_seq`   bigint(20)           DEFAULT '0' COMMENT '消息异常结束顺序号',
  `excp_department_id` bigint(20)           DEFAULT '0' COMMENT '导入异常部门ID',
  `excp_code`          varchar(32)          DEFAULT NULL COMMENT 'PENDING – 待处理；PROCESSED –已处理；IGNORED – 已忽略；',
  `excp_desc`          varchar(512)         DEFAULT NULL COMMENT '错误详细原因',
  `extension`          varchar(4000)        DEFAULT NULL COMMENT '扩展',
  `gmt_created`        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`        varchar(64) NOT NULL COMMENT '创建人',
  `gmt_updated`        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`        varchar(64)          DEFAULT NULL COMMENT '修改人',
  `deleted`            tinyint(4)  NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_excp_id` (`excp_id`),
  KEY `idx_task_id` (`task_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='机构数据同步任务异常表';



# Dump of table tenant_sync_log
# ------------------------------------------------------------

CREATE TABLE `tenant_sync_log`
(
  `id`               bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `task_id`          varchar(64) NOT NULL COMMENT '自动生成的唯一任务流水号，32位流水号‘TK’+数字流水号；',
  `tenant_id`        varchar(64) NOT NULL DEFAULT '' COMMENT '为机构自动生成的唯一编码，32位流水号‘OG’+数字流水号',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `task_type`        varchar(32) NOT NULL COMMENT '任务类型 　CONTACT_SYNC – 通讯录同步；MESSAGE_SYNC  - 会话同步；',
  `task_date`        varchar(10) NOT NULL COMMENT '任务日期yyyyMMdd',
  `task_time`        varchar(32) NOT NULL COMMENT '任务运行时间yyyyMMdd hh:mm:ss',
  `message_count`    int(16)              DEFAULT '0' COMMENT '本次同步消息数量',
  `department_count` int(8)               DEFAULT '0' COMMENT '本次同步部门数量',
  `member_count`     int(8)               DEFAULT '0' COMMENT '本次同步成员数量',
  `customer_count`   int(8)               DEFAULT '0' COMMENT '本次同步客户数量',
  `task_status`      varchar(32)          DEFAULT '' COMMENT 'RECEIVING – 获取数据中；IMPORTING – 数据导入中；SUCCESS   – 成功同步；FAILURE   – 任务失败；',
  `error_code`       varchar(32)          DEFAULT NULL COMMENT 'RECEIVE_EXCEPTION 获取异常IMPORTING_EXCEPTION 导入异常',
  `error_desc`       varchar(512)         DEFAULT NULL COMMENT '错误详细原因',
  `extension`        varchar(4000)        DEFAULT NULL COMMENT '扩展',
  `gmt_created`      timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_creator`      varchar(64) NOT NULL COMMENT '创建人',
  `gmt_updated`      timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_updater`      varchar(64)          DEFAULT NULL COMMENT '修改人',
  `deleted`          tinyint(4)  NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_task_id` (`task_id`),
  KEY `idx_org_id_task_type_task_date` (`tenant_id`, `task_type`, `task_date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='机构数据同步任务流水表';



# Dump of table user
# ------------------------------------------------------------

CREATE TABLE `user`
(
  `id`          bigint(20)    NOT NULL AUTO_INCREMENT,
  `tenant_id`   varchar(64)            DEFAULT NULL COMMENT '租户编码',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `account`     varchar(64)   NOT NULL,
  `name`        varchar(64)   NOT NULL COMMENT '用户名字',
  `gender`      tinyint(2)    NOT NULL COMMENT '性别 0-男 1-女',
  `password`    varchar(128)  NOT NULL COMMENT '密码',
  `salt`        varchar(64)   NOT NULL COMMENT '盐值',
  `expire_date` datetime      NOT NULL COMMENT '密码过期时间',
  `status`      tinyint(4)    NOT NULL COMMENT '状态\n            0 可用，1 停用',
  `gmt_creator` varchar(64)   NOT NULL DEFAULT '' COMMENT '创建人',
  `gmt_created` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater` varchar(64)   NOT NULL DEFAULT '' COMMENT '更新人',
  `gmt_updated` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`   varchar(4000) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `deleted`     tinyint(4)    NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除；1表示删除',
  PRIMARY KEY (`id`),
  KEY `uq_account` (`account`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户';



# Dump of table user_org
# ------------------------------------------------------------

CREATE TABLE `user_org`
(
  `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
  `tenant_id`    varchar(32)  NOT NULL DEFAULT '' COMMENT '租户ID',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `org_code`     varchar(32)  NOT NULL DEFAULT '' COMMENT '组织code',
  `org_name`     varchar(128) NOT NULL DEFAULT '' COMMENT '角色名称',
  `user_account` varchar(16)  NOT NULL DEFAULT '' COMMENT '用户account',
  `user_name`    varchar(64)  NOT NULL DEFAULT '' COMMENT '用户名字',
  `gmt_creator`  varchar(64)           DEFAULT '' COMMENT '创建人',
  `gmt_created`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater`  varchar(64)           DEFAULT '' COMMENT '更新人',
  `gmt_updated`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`    varchar(4000)         DEFAULT '' COMMENT '扩展字段',
  `deleted`      tinyint(4)   NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除；1表示删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_org` (`tenant_id`, `org_code`, `user_account`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户与组织关系表';



# Dump of table user_role
# ------------------------------------------------------------

CREATE TABLE `user_role`
(
  `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
  `tenant_id`    varchar(32)           DEFAULT '' COMMENT '租户编码',
  `tenant` varchar(32) DEFAULT NULL COMMENT '租户编码',
  `role_code`    varchar(32)  NOT NULL COMMENT '角色id',
  `role_name`    varchar(128) NOT NULL COMMENT '角色名称',
  `user_account` varchar(16)  NOT NULL DEFAULT '' COMMENT '用户id',
  `user_name`    varchar(64)  NOT NULL COMMENT '用户名字',
  `gmt_creator`  varchar(64)           DEFAULT '' COMMENT '创建人',
  `gmt_created`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater`  varchar(64)           DEFAULT '' COMMENT '更新人',
  `gmt_updated`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension`    varchar(4000)         DEFAULT '' COMMENT '扩展字段',
  `deleted`      tinyint(4)   NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除；1表示删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户与角色';


