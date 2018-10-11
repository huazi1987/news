CREATE TABLE `version` (
  `id` INT(11) NOT NULL primary key auto_increment COMMENT 'ID',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `os_type` varchar(32) DEFAULT NULL COMMENT '系统',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY `index_ostype` (`os_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;