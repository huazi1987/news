CREATE TABLE `video` (
  `id` INT(11) NOT NULL primary key auto_increment COMMENT '视频ID',
  `title` varchar(32) DEFAULT NULL COMMENT '视频标题',
  `description` varchar(128) DEFAULT NULL COMMENT '视频描述',
  `height` int(11) DEFAULT NULL COMMENT '视频高度',
  `width` int(11) DEFAULT NULL COMMENT '视频宽度',
  `duration` int(11) DEFAULT NULL COMMENT '视频时长',
  `size` int(11) DEFAULT NULL COMMENT '视频大小',
  `play_count` int(11) DEFAULT NULL COMMENT '播放数',
  `thumbnail_url` varchar(128) DEFAULT NULL COMMENT '视频封面',
  `content_url` varchar(128) DEFAULT NULL COMMENT '视频地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '视频创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '视频更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;