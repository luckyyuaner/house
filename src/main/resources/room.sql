/*
Navicat MySQL Data Transfer

Source Server         : thensilence
Source Server Version : 50541
Source Host           : 127.0.0.1:3306
Source Database       : room

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2019-04-07 18:50:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级权限',
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '权限类型',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `url` varchar(100) DEFAULT NULL COMMENT '权限路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '权限图标',
  `status` tinyint(4) DEFAULT NULL COMMENT '权限状态，0：有效，1：无效',
  `orders` bigint(20) NOT NULL COMMENT '排序',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '0', '用户角色管理', '1', '', null, null, '0', '1');
INSERT INTO `permission` VALUES ('2', '0', '权限资源管理', '1', null, null, null, '0', '2');
INSERT INTO `permission` VALUES ('3', '1', '用户管理', '2', 'user:read', '/user/index', null, '0', '3');
INSERT INTO `permission` VALUES ('4', '1', '角色管理', '2', 'role:read', '/role/index', null, '0', '4');
INSERT INTO `permission` VALUES ('5', '3', '新增用户', '3', 'user:create', '/user/create', null, '0', '5');
INSERT INTO `permission` VALUES ('6', '3', '编辑用户', '3', 'user:update', '/user/update', null, '0', '6');
INSERT INTO `permission` VALUES ('7', '3', '删除用户', '3', 'user:delete', '/user/delete', null, '0', '7');
INSERT INTO `permission` VALUES ('8', '4', '新增角色', '3', 'role:create', '/role/create', null, '0', '8');
INSERT INTO `permission` VALUES ('9', '4', '编辑角色', '3', 'role:update', '/role/update', null, '0', '9');
INSERT INTO `permission` VALUES ('10', '4', '删除角色', '3', 'role:delete', '/role/delete', null, '0', '10');
INSERT INTO `permission` VALUES ('11', '2', '权限管理', '2', 'permission:read', '/permission/index', null, '0', '11');
INSERT INTO `permission` VALUES ('12', '11', '新增权限', '3', 'permission:create', '/permission/create', null, '0', '12');
INSERT INTO `permission` VALUES ('13', '11', '编辑权限', '3', 'permission:update', '/permission/update', null, '0', '13');
INSERT INTO `permission` VALUES ('14', '11', '删除权限', '3', 'permission:delete', '/permission/delete', null, '0', '14');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `title` varchar(20) DEFAULT NULL COMMENT '角色标题',
  `description` text COMMENT '角色描述',
  `orders` bigint(20) NOT NULL COMMENT '排序',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', null, null, '0');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_permission_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `permission_id` int(11) NOT NULL COMMENT '权限编号',
  `type` tinyint(4) NOT NULL COMMENT '权限类型(-1:减权限,1:增权限)',
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '3', '1');
INSERT INTO `role_permission` VALUES ('2', '1', '5', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户帐号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '用户密码',
  `user_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户身份，0:"管理员"，1:"租房客"，2:"房东"',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态，0：有效，1：无效',
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', '0', '0', '2019-04-04 08:36:20', '2019-04-04 08:36:55');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_role_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
