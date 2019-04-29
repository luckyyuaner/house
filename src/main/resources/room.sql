/*
Navicat MySQL Data Transfer

Source Server         : thensilence
Source Server Version : 50541
Source Host           : 127.0.0.1:3306
Source Database       : room

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2019-04-29 12:21:42
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
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '收藏时间',
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
  `url` varchar(100) DEFAULT NULL COMMENT '路径',
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
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '反馈信息类型，0：咨询，1：建议，2：举报，3：投诉，4：报修，5：公告',
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
  UNIQUE KEY `unique_idx` (`name`) USING BTREE,
  KEY `id_idx` (`house_id`) USING BTREE,
  KEY `h_uid_f` (`user_id`),
  CONSTRAINT `h_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of house
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `log_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `content` varchar(100) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(6) DEFAULT NULL COMMENT '操作类型，增删改查',
  `target` varchar(20) DEFAULT NULL COMMENT '操作对象',
  PRIMARY KEY (`log_id`),
  KEY `id_idx` (`log_id`) USING BTREE,
  KEY `l_uid_f` (`user_id`),
  CONSTRAINT `l_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
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
  UNIQUE KEY `unique_idx` (`name`) USING BTREE,
  KEY `id_idx` (`permission_id`) USING BTREE,
  KEY `pid_f` (`parent_id`),
  CONSTRAINT `pid_f` FOREIGN KEY (`parent_id`) REFERENCES `permission` (`permission_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', null, '用户信息管理', '1', '', null, null, '0', '1');
INSERT INTO `permission` VALUES ('2', null, '房屋资源管理', '1', null, null, null, '0', '2');
INSERT INTO `permission` VALUES ('3', '1', '用户管理', '2', 'user:read', '/user/listUser', null, '0', '0');
INSERT INTO `permission` VALUES ('4', '1', '角色管理', '2', 'role:read', '/role/listRole', null, '0', '0');
INSERT INTO `permission` VALUES ('5', '3', '新增用户', '3', 'user:create', '/user/addUser', null, '0', '0');
INSERT INTO `permission` VALUES ('6', '3', '编辑用户', '3', 'user:update', '/user/updateUser', null, '0', '0');
INSERT INTO `permission` VALUES ('7', '3', '删除用户', '3', 'user:delete', '/user/deleteUser', null, '0', '0');
INSERT INTO `permission` VALUES ('8', '4', '新增角色', '3', 'role:create', '/role/showAdd', null, '0', '0');
INSERT INTO `permission` VALUES ('9', '4', '编辑角色', '3', 'role:update', '/role/showUpdate', null, '0', '0');
INSERT INTO `permission` VALUES ('10', '4', '删除角色', '3', 'role:delete', '/role/deleteRole', null, '0', '0');
INSERT INTO `permission` VALUES ('11', '1', '权限管理', '2', 'permission:read', '/permission/listPermission', null, '0', '0');
INSERT INTO `permission` VALUES ('12', '11', '新增权限', '3', 'permission:create', '/permission/showAdd', null, '0', '0');
INSERT INTO `permission` VALUES ('13', '11', '编辑权限', '3', 'permission:update', '/permission/showUpdate', null, '0', '0');
INSERT INTO `permission` VALUES ('14', '11', '删除权限', '3', 'permission:delete', '/permission/deletePermission', null, '0', '0');
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
INSERT INTO `permission` VALUES ('25', '2', '房源管理', '2', 'house:read', '/house/read', null, '0', null);
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
INSERT INTO `permission` VALUES ('41', '28', '新增报修', '3', 'repair:create', '/repair/create', null, '0', null);
INSERT INTO `permission` VALUES ('44', '28', '编辑报修', '3', 'repair:update', '/repair/update', null, '0', null);
INSERT INTO `permission` VALUES ('45', '28', '删除报修', '3', 'repair:delete', '/repair/delete', null, '0', null);
INSERT INTO `permission` VALUES ('46', '29', '新增举报', '3', 'report:create', '/report/create', null, '0', null);
INSERT INTO `permission` VALUES ('47', '29', '编辑举报', '3', 'report:update', '/report/update', null, '0', null);
INSERT INTO `permission` VALUES ('48', '29', '删除举报', '3', 'report:delete', '/report/delete', null, '0', null);
INSERT INTO `permission` VALUES ('49', '30', '新增投诉', '3', 'complaint:create', '/complaint/create', null, '0', null);
INSERT INTO `permission` VALUES ('50', '30', '编辑投诉', '3', 'complaint:update', '/complaint/update', null, '0', null);
INSERT INTO `permission` VALUES ('51', '30', '删除投诉', '3', 'complaint:delete', '/complaint/delete', null, '0', null);
INSERT INTO `permission` VALUES ('52', '31', '新增建议', '3', 'suggest:create', '/suggest/create', null, '0', null);
INSERT INTO `permission` VALUES ('53', '31', '编辑建议', '3', 'suggest:update', '/suggest/update', null, '0', null);
INSERT INTO `permission` VALUES ('54', '31', '删除建议', '3', 'suggest:delete', '/suggest/delete', null, '0', null);
INSERT INTO `permission` VALUES ('55', null, '系统管理', '1', null, null, null, '0', null);
INSERT INTO `permission` VALUES ('56', '55', '日志管理', '2', 'log:read', '/log/read', null, '0', null);
INSERT INTO `permission` VALUES ('57', '56', '新增日志', '3', 'log:create', '/log/create', null, '0', null);
INSERT INTO `permission` VALUES ('58', '56', '编辑日志', '3', 'log:update', '/log/update', null, '0', null);
INSERT INTO `permission` VALUES ('59', '56', '删除日志', '3', 'log:delete', '/log/delete', null, '0', null);
INSERT INTO `permission` VALUES ('60', '16', '公告管理', '2', 'notice:read', '/notice/read', null, '0', null);
INSERT INTO `permission` VALUES ('61', '60', '新增公告', '3', 'notice:create', '/notice/create', null, '0', null);
INSERT INTO `permission` VALUES ('62', '60', '编辑公告', '3', 'notice:update', '/notice/update', null, '0', null);
INSERT INTO `permission` VALUES ('63', '60', '删除公告', '3', 'notice:delete', '/notice/delete', null, '0', null);

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
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:有效，1：无效',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `unique_idx` (`name`) USING BTREE,
  KEY `id_idx` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', '系统管理员', null, '0', '0');
INSERT INTO `role` VALUES ('2', '业务管理员', null, null, '0', '0');
INSERT INTO `role` VALUES ('3', '一般管理员', null, null, null, '0');
INSERT INTO `role` VALUES ('4', '租客', null, null, null, '0');
INSERT INTO `role` VALUES ('5', '房东', null, null, null, '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '3');
INSERT INTO `role_permission` VALUES ('2', '1', '4');
INSERT INTO `role_permission` VALUES ('7', '1', '5');
INSERT INTO `role_permission` VALUES ('8', '1', '6');
INSERT INTO `role_permission` VALUES ('9', '1', '7');
INSERT INTO `role_permission` VALUES ('10', '1', '8');
INSERT INTO `role_permission` VALUES ('11', '1', '9');
INSERT INTO `role_permission` VALUES ('12', '1', '10');
INSERT INTO `role_permission` VALUES ('3', '1', '11');
INSERT INTO `role_permission` VALUES ('4', '1', '12');
INSERT INTO `role_permission` VALUES ('5', '1', '13');
INSERT INTO `role_permission` VALUES ('6', '1', '14');
INSERT INTO `role_permission` VALUES ('19', '1', '56');
INSERT INTO `role_permission` VALUES ('20', '1', '57');
INSERT INTO `role_permission` VALUES ('21', '1', '58');
INSERT INTO `role_permission` VALUES ('22', '1', '59');
INSERT INTO `role_permission` VALUES ('31', '2', '17');
INSERT INTO `role_permission` VALUES ('32', '2', '19');
INSERT INTO `role_permission` VALUES ('33', '2', '20');
INSERT INTO `role_permission` VALUES ('34', '2', '21');
INSERT INTO `role_permission` VALUES ('23', '2', '25');
INSERT INTO `role_permission` VALUES ('27', '2', '26');
INSERT INTO `role_permission` VALUES ('24', '2', '32');
INSERT INTO `role_permission` VALUES ('25', '2', '33');
INSERT INTO `role_permission` VALUES ('26', '2', '34');
INSERT INTO `role_permission` VALUES ('28', '2', '35');
INSERT INTO `role_permission` VALUES ('29', '2', '36');
INSERT INTO `role_permission` VALUES ('30', '2', '37');
INSERT INTO `role_permission` VALUES ('35', '2', '60');
INSERT INTO `role_permission` VALUES ('36', '2', '61');
INSERT INTO `role_permission` VALUES ('37', '2', '62');
INSERT INTO `role_permission` VALUES ('38', '2', '63');
INSERT INTO `role_permission` VALUES ('39', '3', '28');
INSERT INTO `role_permission` VALUES ('40', '3', '29');
INSERT INTO `role_permission` VALUES ('41', '3', '30');
INSERT INTO `role_permission` VALUES ('42', '3', '31');
INSERT INTO `role_permission` VALUES ('43', '3', '41');
INSERT INTO `role_permission` VALUES ('46', '3', '44');
INSERT INTO `role_permission` VALUES ('47', '3', '45');
INSERT INTO `role_permission` VALUES ('48', '3', '46');
INSERT INTO `role_permission` VALUES ('49', '3', '47');
INSERT INTO `role_permission` VALUES ('50', '3', '48');
INSERT INTO `role_permission` VALUES ('51', '3', '49');
INSERT INTO `role_permission` VALUES ('52', '3', '50');
INSERT INTO `role_permission` VALUES ('53', '3', '51');
INSERT INTO `role_permission` VALUES ('54', '3', '52');
INSERT INTO `role_permission` VALUES ('55', '3', '53');
INSERT INTO `role_permission` VALUES ('56', '3', '54');
INSERT INTO `role_permission` VALUES ('57', '4', '3');
INSERT INTO `role_permission` VALUES ('58', '4', '5');
INSERT INTO `role_permission` VALUES ('59', '4', '6');
INSERT INTO `role_permission` VALUES ('60', '4', '7');
INSERT INTO `role_permission` VALUES ('70', '4', '17');
INSERT INTO `role_permission` VALUES ('74', '4', '18');
INSERT INTO `role_permission` VALUES ('71', '4', '19');
INSERT INTO `role_permission` VALUES ('72', '4', '20');
INSERT INTO `role_permission` VALUES ('73', '4', '21');
INSERT INTO `role_permission` VALUES ('75', '4', '22');
INSERT INTO `role_permission` VALUES ('76', '4', '23');
INSERT INTO `role_permission` VALUES ('77', '4', '24');
INSERT INTO `role_permission` VALUES ('61', '4', '25');
INSERT INTO `role_permission` VALUES ('66', '4', '26');
INSERT INTO `role_permission` VALUES ('62', '4', '27');
INSERT INTO `role_permission` VALUES ('67', '4', '35');
INSERT INTO `role_permission` VALUES ('68', '4', '36');
INSERT INTO `role_permission` VALUES ('69', '4', '37');
INSERT INTO `role_permission` VALUES ('63', '4', '38');
INSERT INTO `role_permission` VALUES ('64', '4', '39');
INSERT INTO `role_permission` VALUES ('65', '4', '40');
INSERT INTO `role_permission` VALUES ('78', '5', '3');
INSERT INTO `role_permission` VALUES ('79', '5', '5');
INSERT INTO `role_permission` VALUES ('80', '5', '6');
INSERT INTO `role_permission` VALUES ('81', '5', '7');
INSERT INTO `role_permission` VALUES ('86', '5', '17');
INSERT INTO `role_permission` VALUES ('94', '5', '18');
INSERT INTO `role_permission` VALUES ('87', '5', '19');
INSERT INTO `role_permission` VALUES ('88', '5', '20');
INSERT INTO `role_permission` VALUES ('89', '5', '21');
INSERT INTO `role_permission` VALUES ('95', '5', '22');
INSERT INTO `role_permission` VALUES ('96', '5', '23');
INSERT INTO `role_permission` VALUES ('97', '5', '24');
INSERT INTO `role_permission` VALUES ('82', '5', '25');
INSERT INTO `role_permission` VALUES ('90', '5', '26');
INSERT INTO `role_permission` VALUES ('83', '5', '32');
INSERT INTO `role_permission` VALUES ('84', '5', '33');
INSERT INTO `role_permission` VALUES ('85', '5', '34');
INSERT INTO `role_permission` VALUES ('91', '5', '35');
INSERT INTO `role_permission` VALUES ('92', '5', '36');
INSERT INTO `role_permission` VALUES ('93', '5', '37');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户帐号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '用户密码',
  `user_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户身份，0:"管理员"，1:"租房客"，2:"房东"',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态，0：有效，1：无效',
  `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '系统管理员', '123456', '0', '0', null, '2019-04-19 10:25:28', null, '0', null, null, null, null, '0', '5');
INSERT INTO `user` VALUES ('2', '业务管理员', '123456', '0', '0', null, '2019-04-29 11:08:40', null, '0', null, null, null, null, '0', '0');
INSERT INTO `user` VALUES ('3', '一般管理员', '123456', '0', '0', null, '2019-04-29 11:09:05', null, '0', null, null, null, null, '0', '5');
INSERT INTO `user` VALUES ('4', '租客', '123456', '1', '0', null, '2019-04-29 11:10:03', null, '0', null, null, null, null, '0', '5');
INSERT INTO `user` VALUES ('5', '房东', '123456', '2', '0', null, '2019-04-29 11:10:06', null, '0', null, null, null, null, '0', '5');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '2', '2');
INSERT INTO `user_role` VALUES ('3', '3', '3');
INSERT INTO `user_role` VALUES ('4', '4', '4');
INSERT INTO `user_role` VALUES ('5', '5', '5');
