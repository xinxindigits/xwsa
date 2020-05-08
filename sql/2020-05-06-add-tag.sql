CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) NOT NULL,
  `code` varchar(32) DEFAULT NULL COMMENT '标签编码',
  `name` varchar(128) NOT NULL COMMENT '标签名称',
  `description` varchar(512) DEFAULT '' COMMENT '标签描述',
  `tag_type` varchar(64) NOT NULL COMMENT '标签类型,COMMON:普通标签，OTHER:其他标签',
  `gmt_creator` varchar(64) DEFAULT '' COMMENT '创建人',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater` varchar(64) DEFAULT '' COMMENT '更新人',
  `gmt_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension` varchar(4000) DEFAULT NULL COMMENT '扩展字段',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除,1表示删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签';


CREATE TABLE `tags_relations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '对应的租户ID',
  `tag_id` varchar(32) DEFAULT NULL COMMENT '标签ID',
  `key_id` varchar(128) NOT NULL COMMENT '标签对应的关系key_id',
  `key_name` varchar(512) NOT NULL COMMENT '标签对应的关系key_name',
  `description` varchar(512) DEFAULT NULL COMMENT '标签对应的关系key_name',
  `gmt_creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_updater` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `gmt_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extension` varchar(4000) DEFAULT NULL COMMENT '扩展字段',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删除标志,0表示不删除,1表示删除',
  PRIMARY KEY (`id`),
  KEY `uq_code` (`tag_id`,`key_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签与其他关系对应的表';