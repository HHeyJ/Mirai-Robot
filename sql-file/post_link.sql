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

 Date: 29/07/2021 14:17:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for post_link
-- ----------------------------
DROP TABLE IF EXISTS `post_link`;
CREATE TABLE `post_link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `area` bigint(20) NOT NULL COMMENT '区服ID',
  `area_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '区服务名',
  `link_type` bigint(10) NOT NULL COMMENT '帖子链接类型',
  `link_url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '帖子链接地址',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete` int(5) NOT NULL DEFAULT '0' COMMENT '0:否 1:删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq` (`area`,`link_url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='帖子链接表';

SET FOREIGN_KEY_CHECKS = 1;
