CREATE TABLE `comment` (
  `id` INT(11) NOT NULL primary key auto_increment COMMENT '评论D',
  `content` varchar(10240) NOT NULL COMMENT '帖子内容',
  `img_list` varchar(512) DEFAULT NULL COMMENT '帖子图片列表',
  `theme` varchar(128) DEFAULT NULL COMMENT '帖子主题',
  `user_id` INT(11) NOT NULL COMMENT '帖子发布人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '评论更新时间',
  KEY `index_user_id` (`user_id`),
  KEY `index_theme` (`theme`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;