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

 Date: 29/07/2021 14:18:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bar_post
-- ----------------------------
DROP TABLE IF EXISTS `bar_post`;
CREATE TABLE `bar_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `floor_id` bigint(20) NOT NULL COMMENT '楼层',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '楼层内容',
  `post_url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '帖子链接',
  `page_no` bigint(20) NOT NULL COMMENT '当前页数',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete` int(5) NOT NULL DEFAULT '0' COMMENT '0:否 1:删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq` (`post_url`,`floor_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1528443 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据外观帖';

SET FOREIGN_KEY_CHECKS = 1;
