/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云Rds
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : rm-bp1b934u5x6g031597o.mysql.rds.aliyuncs.com:3306
 Source Schema         : robot

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 29/07/2021 14:18:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for inform_relation
-- ----------------------------
DROP TABLE IF EXISTS `inform_relation`;
CREATE TABLE `inform_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `post_link_id` bigint(20) NOT NULL COMMENT '帖子链接ID',
  `group_id` bigint(20) NOT NULL COMMENT 'QQ群号',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete` int(5) NOT NULL DEFAULT '0' COMMENT '0:否 1:删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq` (`post_link_id`,`group_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='QQ群链接广播表';

SET FOREIGN_KEY_CHECKS = 1;
