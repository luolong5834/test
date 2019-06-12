/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.50_dev
Source Server Version : 50717
Source Host           : 192.168.1.50:3306
Source Database       : luolong_test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-05-30 15:15:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_workflow
-- ----------------------------
DROP TABLE IF EXISTS `account_workflow`;
CREATE TABLE `account_workflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_id` int(11) DEFAULT NULL,
  `out_id` int(11) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `youxin_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_workflow
-- ----------------------------
INSERT INTO `account_workflow` VALUES ('1', '1', '2', '2019-05-25 13:43:10', '1');
INSERT INTO `account_workflow` VALUES ('2', '2', '3', '2019-05-26 13:43:57', '1');
INSERT INTO `account_workflow` VALUES ('3', '3', '4', '2019-05-27 13:44:01', '1');
