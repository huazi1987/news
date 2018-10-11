CREATE TABLE `user` (
  `id` int(11)  NOT NULL primary key auto_increment COMMENT 'ID',
  `login_name` varchar(64) NOT NULL COMMENT '登录名',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `phone` int(11) DEFAULT NULL COMMENT '电话',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  KEY `index_nickname` (`nickname`),
  UNIQUE KEY `index_loginname` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;