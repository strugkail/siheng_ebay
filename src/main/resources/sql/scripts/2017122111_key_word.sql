/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.127
Source Server Version : 50636
Source Host           : 192.168.2.127:3306
Source Database       : siheng_core_test1031

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2017-12-01 13:22:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `key_word`
-- ----------------------------
DROP TABLE IF EXISTS `key_word`;
CREATE TABLE `key_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品Id',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键字',
  `keywordurl` varchar(255) DEFAULT NULL COMMENT '关键字路径',
  `site_id` bigint(20) DEFAULT NULL COMMENT '站点Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of key_word
-- ----------------------------
