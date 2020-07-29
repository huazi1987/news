CREATE TABLE `friend` (
  `user_id` INT(11) NOT NULL COMMENT '被申请人id',
  `apply_user_id` INT(11) NOT NULL COMMENT '申请人id',
  `is_agree` SMALLINT (4) NOT NULL DEFAULT 0 COMMENT '是否已同意 0:待通过 1：通过 2：拒绝',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`apply_user_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;