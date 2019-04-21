/*
Navicat MySQL Data Transfer

Source Server         : thensilence
Source Server Version : 50541
Source Host           : 127.0.0.1:3306
Source Database       : room

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2019-04-19 09:54:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `cart_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `house_id` bigint(11) unsigned NOT NULL,
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`cart_id`),
  UNIQUE KEY `unique_idx` (`cart_id`,`user_id`,`house_id`,`ctime`) USING BTREE,
  KEY `id_idx` (`cart_id`) USING BTREE,
  KEY `ca_uid_f` (`user_id`),
  KEY `ca_hid_f` (`house_id`),
  CONSTRAINT `ca_hid_f` FOREIGN KEY (`house_id`) REFERENCES `house` (`house_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ca_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL COMMENT '评论人id',
  `contract_id` bigint(11) unsigned NOT NULL,
  `user_grade` double(8,0) NOT NULL DEFAULT '5' COMMENT '被评论者分数，0-10',
  `house_grade` double(8,0) NOT NULL DEFAULT '5' COMMENT '房源及房东分数，0-10',
  `info` varchar(300) NOT NULL COMMENT '评论内容',
  `url` varchar(30) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `unique_idx` (`comment_id`,`user_id`,`contract_id`) USING BTREE,
  KEY `id_idx` (`comment_id`) USING BTREE,
  KEY `com_uid_f` (`user_id`),
  KEY `com_cid_f` (`contract_id`),
  CONSTRAINT `com_cid_f` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`contract_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `com_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `contract_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `house_id` bigint(11) unsigned NOT NULL,
  `user_id` bigint(11) unsigned NOT NULL COMMENT '租客账号',
  `stime` datetime NOT NULL COMMENT '租房开始时间',
  `etime` datetime NOT NULL COMMENT '租房结束时间',
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '合同创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '合同最后更新时间',
  `landlord_operation` tinyint(2) NOT NULL DEFAULT '0' COMMENT '房东操作状态,0：未处理，1：修改，2：同意，3：拒绝',
  `tenant_operation` tinyint(2) NOT NULL COMMENT '租客操作状态，0:未操作，1：修改，2：同意，3：拒绝',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:租房，1：退房',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '合同状态，0：申请中，1：创建中，2：签约中，3：付款中，4：签约完成，5：正常结束，6：毁约申请中，7：退款处理中，8:毁约结束',
  `file` varchar(100) DEFAULT NULL COMMENT '合同文件路径',
  `landlord_info` varchar(300) DEFAULT NULL COMMENT '房东附加信息',
  `tenant_info` varchar(300) DEFAULT NULL COMMENT '租客附加信息',
  PRIMARY KEY (`contract_id`),
  UNIQUE KEY `unique_idx` (`contract_id`,`house_id`,`user_id`) USING BTREE,
  KEY `id_idx` (`contract_id`) USING BTREE,
  KEY `c_uid_f` (`user_id`),
  KEY `c_hid_f` (`house_id`),
  CONSTRAINT `c_hid_f` FOREIGN KEY (`house_id`) REFERENCES `house` (`house_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `c_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contract
-- ----------------------------

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `feedback_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_id` bigint(11) unsigned NOT NULL COMMENT '创建者',
  `role_id` bigint(11) unsigned NOT NULL COMMENT '处理反馈信息用户的角色',
  `operate_id` bigint(11) unsigned DEFAULT NULL COMMENT '反馈信息处理者',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '反馈信息类型，0：咨询，1：建议，2：举报，3：投诉，4：报修',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '处理状态，0：未处理，1：处理中，2：处理完成',
  `info` text NOT NULL COMMENT '反馈内容',
  `url` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`),
  UNIQUE KEY `unique_idx` (`feedback_id`,`create_id`,`operate_id`) USING BTREE,
  KEY `id_idx` (`feedback_id`) USING BTREE,
  KEY `f_uid_f` (`create_id`),
  KEY `f_oid_f` (`operate_id`),
  KEY `f_rid_f` (`role_id`),
  CONSTRAINT `f_oid_f` FOREIGN KEY (`operate_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `f_rid_f` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `f_uid_f` FOREIGN KEY (`create_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house` (
  `house_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL COMMENT '房东账号',
  `name` varchar(20) NOT NULL COMMENT '房源名称',
  `order` int(4) NOT NULL DEFAULT '1' COMMENT '房源序号，默认1号房',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '出租方式，0:合租，1：整租',
  `kind` char(5) NOT NULL DEFAULT '00000' COMMENT '第一位表示房间数，第二位表示客厅数，第三位表示厕所数，第四位表示厨房数，第五位表示阳台数，00000表示0室0厅无厕所无厨房无阳台',
  `money` double(8,0) NOT NULL DEFAULT '0' COMMENT '租金',
  `cycle` int(4) NOT NULL DEFAULT '30' COMMENT '交费周期，天为单位，默认30天',
  `area` double(8,0) NOT NULL DEFAULT '0' COMMENT '房源面积',
  `floor` int(4) NOT NULL DEFAULT '1' COMMENT '房源楼层',
  `elevator` tinyint(2) NOT NULL DEFAULT '0' COMMENT '房源是否电梯房，0：电梯房，1:非电梯房',
  `orientation` varchar(10) DEFAULT NULL COMMENT '房源朝向',
  `address` varchar(100) DEFAULT NULL COMMENT '房源地址',
  `description` text COMMENT '房源描述',
  `keys` varchar(100) DEFAULT NULL COMMENT '搜索关键词，以英文分号间隔，最后一个不加分号',
  `title` varchar(100) DEFAULT NULL COMMENT '房源装修、设施等描述，以英文分号间隔，结尾不加分号',
  `url` varchar(200) DEFAULT NULL COMMENT '房源图片视频路径，多个以英文分号间隔，结尾不加分号',
  `grade` double(8,0) NOT NULL DEFAULT '5' COMMENT '房源分数，0-10',
  PRIMARY KEY (`house_id`),
  KEY `id_idx` (`house_id`) USING BTREE,
  KEY `h_uid_f` (`user_id`),
  CONSTRAINT `h_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of house
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `parent_id` bigint(11) unsigned DEFAULT NULL COMMENT '上级权限',
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '权限类型',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `url` varchar(100) DEFAULT NULL COMMENT '权限路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '权限图标',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '权限状态，0：有效，1：无效',
  `orders` bigint(20) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`permission_id`),
  KEY `id_idx` (`permission_id`) USING BTREE,
  KEY `pid_f` (`parent_id`),
  CONSTRAINT `pid_f` FOREIGN KEY (`parent_id`) REFERENCES `permission` (`permission_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', null, '用户信息管理', '1', '', null, null, '0', '1');
INSERT INTO `permission` VALUES ('2', null, '权限资源管理', '1', null, null, null, '0', '2');
INSERT INTO `permission` VALUES ('3', '1', '用户管理', '2', 'user:read', '/user/index', null, '0', '0');
INSERT INTO `permission` VALUES ('4', '1', '角色管理', '2', 'role:read', '/role/index', null, '0', '0');
INSERT INTO `permission` VALUES ('5', '3', '新增用户', '3', 'user:create', '/user/create', null, '0', '0');
INSERT INTO `permission` VALUES ('6', '3', '编辑用户', '3', 'user:update', '/user/update', null, '0', '0');
INSERT INTO `permission` VALUES ('7', '3', '删除用户', '3', 'user:delete', '/user/delete', null, '0', '0');
INSERT INTO `permission` VALUES ('8', '4', '新增角色', '3', 'role:create', '/role/create', null, '0', '0');
INSERT INTO `permission` VALUES ('9', '4', '编辑角色', '3', 'role:update', '/role/update', null, '0', '0');
INSERT INTO `permission` VALUES ('10', '4', '删除角色', '3', 'role:delete', '/role/delete', null, '0', '0');
INSERT INTO `permission` VALUES ('11', '2', '权限管理', '2', 'permission:read', '/permission/index', null, '0', '0');
INSERT INTO `permission` VALUES ('12', '11', '新增权限', '3', 'permission:create', '/permission/create', null, '0', '0');
INSERT INTO `permission` VALUES ('13', '11', '编辑权限', '3', 'permission:update', '/permission/update', null, '0', '0');
INSERT INTO `permission` VALUES ('14', '11', '删除权限', '3', 'permission:delete', '/permission/delete', null, '0', '0');
INSERT INTO `permission` VALUES ('15', null, '租房业务管理', '1', null, null, null, '0', '3');
INSERT INTO `permission` VALUES ('16', null, '反馈信息管理', '1', null, null, null, '0', '4');
INSERT INTO `permission` VALUES ('17', '1', '评价管理', '2', 'comment:read', '/comment/index', null, '0', null);
INSERT INTO `permission` VALUES ('18', '1', '钱包管理', '2', 'money:read', '/money/index', null, '0', null);
INSERT INTO `permission` VALUES ('19', '17', '新增评价', '3', 'comment:create', '/comment/create', null, '0', null);
INSERT INTO `permission` VALUES ('20', '17', '编辑评价', '3', 'comment:update', '/comment/update', null, '0', null);
INSERT INTO `permission` VALUES ('21', '17', '删除评价', '3', 'comment:delete', '/comment/delete', null, '0', null);
INSERT INTO `permission` VALUES ('22', '18', '新增钱包', '3', 'money:create', '/money/create', null, '0', null);
INSERT INTO `permission` VALUES ('23', '18', '编辑钱包', '3', 'money:update', '/money/update', null, '0', null);
INSERT INTO `permission` VALUES ('24', '18', '删除钱包', '3', 'money:delete', '/money/delete', null, '0', null);
INSERT INTO `permission` VALUES ('25', '15', '房源管理', '2', 'house:read', '/house/read', null, '0', null);
INSERT INTO `permission` VALUES ('26', '15', '合同管理', '2', 'contract:read', '/contract/index', null, '0', null);
INSERT INTO `permission` VALUES ('27', '1', '收藏管理', '2', 'cart:read', '/cart/index', null, '0', null);
INSERT INTO `permission` VALUES ('28', '16', '报修管理', '2', 'repair:read', '/repair/index', null, '0', null);
INSERT INTO `permission` VALUES ('29', '16', '举报管理', '2', 'report:read', '/report/index', null, '0', null);
INSERT INTO `permission` VALUES ('30', '16', '投诉管理', '2', 'complaint:read', '/complaint/index', null, '0', null);
INSERT INTO `permission` VALUES ('31', '16', '建议管理', '2', 'suggest:read', '/suggest/read', null, '0', null);
INSERT INTO `permission` VALUES ('32', '25', '新增房源', '3', 'house:create', '/house/create', null, '0', null);
INSERT INTO `permission` VALUES ('33', '25', '编辑房源', '3', 'house:update', '/house/update', null, '0', null);
INSERT INTO `permission` VALUES ('34', '25', '删除房源', '3', 'house:delete', '/house/delete', null, '0', null);
INSERT INTO `permission` VALUES ('35', '26', '新增合同', '3', 'contract:create', '/contract/create', null, '0', null);
INSERT INTO `permission` VALUES ('36', '26', '编辑合同', '3', 'contract:update', '/contract/update', null, '0', null);
INSERT INTO `permission` VALUES ('37', '26', '删除合同', '3', 'contract:delete', '/contract/delete', null, '0', null);
INSERT INTO `permission` VALUES ('38', '27', '新增收藏', '3', 'cart:create', '/cart/create', null, '0', null);
INSERT INTO `permission` VALUES ('39', '27', '修改收藏', '3', 'cart:update', '/cart/update', null, '0', null);
INSERT INTO `permission` VALUES ('40', '27', '删除收藏', '3', 'cart:delete', '/cart/delete', null, '0', null);
INSERT INTO `permission` VALUES ('41', '28', '新增报修', '3', 'repair:read', '/repair/read', null, '0', null);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `title` varchar(20) DEFAULT NULL COMMENT '角色标题',
  `description` text COMMENT '角色描述',
  `orders` bigint(20) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`role_id`),
  KEY `id_idx` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', '系统管理员', null, '0');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_permission_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(11) unsigned NOT NULL COMMENT '角色编号',
  `permission_id` bigint(11) unsigned NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`role_permission_id`),
  UNIQUE KEY `unique_idx` (`role_id`,`permission_id`) USING BTREE,
  KEY `id_idx` (`role_permission_id`) USING BTREE,
  KEY `rp_pid_f` (`permission_id`),
  CONSTRAINT `rp_pid_f` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rp_rid_f` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '3');
INSERT INTO `role_permission` VALUES ('2', '1', '4');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户帐号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '用户密码',
  `user_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户身份，0:"管理员"，1:"租房客"，2:"房东"',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态，0：有效，1：无效',
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `head` varchar(30) DEFAULT NULL COMMENT '头像路径',
  `money` double NOT NULL DEFAULT '0' COMMENT '用户钱包金额',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证账号',
  `photo` varchar(30) DEFAULT NULL COMMENT '身份证照片路径',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `sex` tinyint(2) NOT NULL DEFAULT '0' COMMENT '用户性别，0：女，1：男',
  `reputation` double NOT NULL DEFAULT '5' COMMENT '用户信誉值，0-10分',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_idx` (`username`) USING BTREE,
  KEY `id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '系统管理员', '123456', '0', '0', '0000-00-00 00:00:00', '2019-04-19 09:42:26', null, '0', null, null, null, null, '0', '5');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_role_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(11) unsigned NOT NULL COMMENT '用户编号',
  `role_id` bigint(11) unsigned NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `unique_idx` (`user_id`,`role_id`) USING BTREE,
  KEY `id_idx` (`user_role_id`) USING BTREE,
  KEY `rid_f` (`role_id`),
  CONSTRAINT `rid_f` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');