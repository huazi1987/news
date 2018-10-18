CREATE TABLE `verify_code` (
  `phone` varchar(12) NOT NULL COMMENT '手机号',
  `code` varchar(16) NOT NULL COMMENT '验证码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  KEY `index_phone_code` (`phone`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;