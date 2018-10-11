CREATE TABLE `news` (
  `id` INT(11) NOT NULL primary key auto_increment COMMENT 'ID',
  `title` varchar(32) DEFAULT NULL COMMENT '标题',
  `content` varchar(10240) DEFAULT NULL COMMENT '内容',
  `thumbnail_url` varchar(128) DEFAULT NULL COMMENT '视频封面',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;