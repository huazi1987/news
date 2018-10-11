CREATE TABLE `file` (
  `id` INT(11) NOT NULL primary key auto_increment COMMENT 'ID',
  `file_name` varchar(32) NOT NULL COMMENT '文件名',
  `path` varchar(128) NOT NULL COMMENT '文件路径',
  `size` int(11) NOT NULL DEFAULT 1 COMMENT '文件大小',
  `download_count` int(11) DEFAULT NULL COMMENT '下载次数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;