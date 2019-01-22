/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : sass_conf

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 22/01/2019 14:16:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_synctargeturi
-- ----------------------------
DROP TABLE IF EXISTS `t_synctargeturi`;
CREATE TABLE `t_synctargeturi` (
  `SyncID` varchar(32) NOT NULL COMMENT 'ID',
  `SyncRule` tinyint(3) unsigned NOT NULL COMMENT '同步规则，1-数据库，2接口',
  `TargetUri` varchar(512) NOT NULL COMMENT '同步的访问地址，如数据库地址、接口地址',
  `SyncInstanceCode` varchar(32) NOT NULL COMMENT '同步实例的code',
  `SyncInstanceName` varchar(128) NOT NULL COMMENT '同步实例的名称',
  `MappingID` varchar(32) NOT NULL COMMENT '映射ID，多个实例可能对应一个映射ID',
  `MappingName` varchar(64) NOT NULL COMMENT '映射后的实例名称',
  `CreateUser` varchar(50) NOT NULL DEFAULT '' COMMENT '创建用户',
  `CreateDate` datetime NOT NULL COMMENT '创建时间',
  `UpdateUser` varchar(50) NOT NULL DEFAULT '' COMMENT '更新用户',
  `UpdateDate` datetime NOT NULL COMMENT '更新时间',
  `DataState` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '数据状态 1 有效 255删除',
  PRIMARY KEY (`SyncID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_synctargeturi
-- ----------------------------
BEGIN;
INSERT INTO `t_synctargeturi` VALUES ('2367173286530113333', 1, 'Server=127.0.0.1;Port=3306;User=root;Password=imkzp.com;DataBase=sassa;CharSet=utf8;Connection Timeout=30;Command Timeout=30;', 'saasa', '测试1', '1111111111', 'kzp', 'kkk', '2018-12-29 11:29:08', 'kkk', '2018-12-29 11:29:19', 1);
INSERT INTO `t_synctargeturi` VALUES ('2367173286530114444', 2, 'Server=127.0.0.1;Port=3306;User=root;Password=imkzp.com;DataBase=sassb;CharSet=utf8;Connection Timeout=30;Command Timeout=30;', 'saasb.saasb', '测试2', '222222', 'kzp', 'kkk', '2018-12-29 11:32:48', 'zzzz', '2018-12-29 11:32:53', 1);
COMMIT;

-- ----------------------------
-- Table structure for t_test
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(100) NOT NULL COMMENT '内容',
  PRIMARY KEY (`mid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_test
-- ----------------------------
BEGIN;
INSERT INTO `t_test` VALUES (3, '----default');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
