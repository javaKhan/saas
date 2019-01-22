/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : sassa

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 22/01/2019 14:17:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for PRODUCT
-- ----------------------------
DROP TABLE IF EXISTS `PRODUCT`;
CREATE TABLE `PRODUCT` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of PRODUCT
-- ----------------------------
BEGIN;
INSERT INTO `PRODUCT` VALUES (1, 'Product 1');
INSERT INTO `PRODUCT` VALUES (2, 'Product 2');
INSERT INTO `PRODUCT` VALUES (3, 'Product 3');
INSERT INTO `PRODUCT` VALUES (4, 'Product 4');
INSERT INTO `PRODUCT` VALUES (5, 'Product 5');
COMMIT;

-- ----------------------------
-- Table structure for t_test
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(100) NOT NULL COMMENT '内容',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_test
-- ----------------------------
BEGIN;
INSERT INTO `t_test` VALUES (1, 'saas-aaaaa');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
