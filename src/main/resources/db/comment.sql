CREATE TABLE `comment` (
  `id` INT(11) NOT NULL primary key auto_increment COMMENT '评论D',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(512) NOT NULL COMMENT '评论内容',
  `reply_id` int(11) DEFAULT NULL COMMENT '回复ID',
  `reply_user_id` int(11) DEFAULT NULL COMMENT '回复ID',
  `reply_user_nickname` varchar(64) DEFAULT NULL COMMENT '回复人昵称',
  `group_type` int(11) NOT NULL COMMENT '类型1：视频 2：资讯，3：帖子',
  `group_id` VARCHAR (64) not NULL COMMENT '圈子ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '评论更新时间',
  KEY `index_user_id` (`user_id`),
  KEY `index_reply_user_id` (`reply_user_id`),
  KEY `index_group_type` (`group_type`),
  KEY `index_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;