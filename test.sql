DROP DATABASE IF EXISTS `zxp`;
CREATE DATABASE `zxp`;
use `zxp`;


#用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) NOT NULL COMMENT '登录名',
  `password` varchar(50) NOT NULL COMMENT '登录密码',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `user_photo` varchar(50) DEFAULT NULL COMMENT '用户头像',
  `user_sex` tinyint(1) DEFAULT NULL COMMENT '用户性别，0：男，1：女',
  `school` varchar(20) NOT NULL COMMENT '学校',
  `address` varchar(30) NOT NULL COMMENT '地址',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态，0：正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


#订单表(拼单)
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user1_id` int(11) unsigned NOT NULL COMMENT '创建者用户ID',
  `order_status` tinyint(1) NOT NULL COMMENT '订单状态,0：正常,1：完成，2：过期，3：被撤销',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `description` varchar(500) NOT NULL COMMENT '描述信息',
  `address` varchar(20) NOT NULL COMMENT '地址',
  `picture` varchar(100) DEFAULT NULL COMMENT '图片',
  `user2_id` int(11) unsigned DEFAULT NULL COMMENT '拼单用户ID',
  PRIMARY KEY (`id`),
  index (`user1_id`),
  index(`user2_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

#留言表
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`(
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `uid` int(11) unsigned NOT NULL COMMENT '用户id',
    `order_id` int(11) unsigned NOT NULL COMMENT '评论的订单',
    `time` datetime NOT NULL COMMENT '评论时间',
    `content` varchar(200) NOT NULL COMMENT '评论内容',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


#回复表replyuser
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`(
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `uid` int(11) unsigned NOT NULL COMMENT '用户id',
    `comment_id` int(11) unsigned NOT NULL COMMENT '回复的评论',
    `reply_id` int(11) unsigned NOT NULL COMMENT '回复目标id',
    `time` datetime NOT NULL COMMENT '回复时间',
    `content` varchar(200) NOT NULL COMMENT '回复内容',
    PRIMARY KEY (`id`),
    index (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


SELECT  id,address,expire_time,create_time,user2_id,description,order_status,user1_id,picture  FROM order