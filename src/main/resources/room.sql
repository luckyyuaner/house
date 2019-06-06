/*
Navicat MySQL Data Transfer

Source Server         : thensilence
Source Server Version : 50541
Source Host           : 127.0.0.1:3306
Source Database       : room

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2019-06-06 23:07:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `collect_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `house_id` bigint(11) unsigned NOT NULL,
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`collect_id`),
  UNIQUE KEY `unique_idx` (`user_id`,`house_id`) USING BTREE,
  KEY `id_idx` (`collect_id`) USING BTREE,
  KEY `co_uid_f` (`user_id`) USING BTREE,
  KEY `co_hid_f` (`house_id`) USING BTREE,
  CONSTRAINT `co_hid_f` FOREIGN KEY (`house_id`) REFERENCES `house` (`house_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `co_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES ('6', '12', '2', '2019-05-31 22:45:58');
INSERT INTO `collect` VALUES ('8', '12', '4', '2019-05-31 22:46:00');
INSERT INTO `collect` VALUES ('9', '12', '5', '2019-05-31 22:46:16');
INSERT INTO `collect` VALUES ('10', '12', '6', '2019-05-31 22:46:17');
INSERT INTO `collect` VALUES ('11', '12', '1', '2019-06-01 15:34:08');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL COMMENT '评论人id',
  `contract_id` bigint(11) unsigned NOT NULL,
  `user_grade` double(5,2) NOT NULL DEFAULT '5.00' COMMENT '租客分数，0-10',
  `house_grade` double(5,2) NOT NULL DEFAULT '5.00' COMMENT '房源及房东分数，0-10',
  `info` varchar(300) NOT NULL COMMENT '评论内容',
  `url` varchar(100) DEFAULT NULL COMMENT '路径',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `unique_idx` (`comment_id`,`user_id`,`contract_id`) USING BTREE,
  KEY `id_idx` (`comment_id`) USING BTREE,
  KEY `com_uid_f` (`user_id`),
  KEY `com_cid_f` (`contract_id`),
  CONSTRAINT `com_cid_f` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`contract_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `com_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '12', '3', '5.00', '5.00', '房子很好房子很好房子很好房子很好房子很好房子很好房子很好房子很好房子很好房子很好房子很好房子很好房子很好房子很好', '057d6b89-a170-404a-ad43-28766b940bf9.jpg', '2019-06-06 21:05:04');
INSERT INTO `comment` VALUES ('3', '12', '3', '5.00', '8.00', '可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀可以呀', '460c6d69-a9d2-47a1-87c2-5c8ea5379c88.jpg', '2019-06-06 21:33:44');

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
  `landlord_operation` tinyint(1) NOT NULL DEFAULT '0' COMMENT '房东操作状态,0：未处理，1：完成，2：同意，3：拒绝',
  `tenant_operation` tinyint(1) NOT NULL DEFAULT '0' COMMENT '租客操作状态，0:未操作，1：完成，2：同意，3：拒绝',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:租房，1：退房',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '合同状态，0：申请中，1：创建中，2：签约中，3：付款中，4：签约完成，5：正常结束，6：毁约申请中，7：退款处理中，8:毁约结束',
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contract
-- ----------------------------
INSERT INTO `contract` VALUES ('1', '2', '12', '2019-05-27 00:00:00', '2019-08-27 00:00:00', '2019-05-26 19:47:34', '2019-06-04 17:00:58', '2', '2', '0', '1', 'dab1d6fa-8197-4543-a0c3-080488f5f818.pdf', 'acfe3aa5-82dc-4b85-8899-a111139cd1b3.jpg,c1f9997f-6d40-4f97-a163-fb1afe0ea21e.jpg,6d6ee135-0986-45ee-af52-56cf8e774206.jpg', '身份证反面照.jpg,身份证正面照.jpg,手持身份证照.jpg');
INSERT INTO `contract` VALUES ('3', '1', '12', '2019-06-05 00:00:00', '2019-07-20 00:00:00', '2019-06-03 16:10:36', '2019-06-06 21:33:43', '0', '1', '0', '4', 'ef836965-dcd2-4793-b122-fd5d18c9b425.pdf', 'a45d7be7-ab1c-4853-9a38-1eefaacd32eb.png,61f4c4b1-c8fd-4b3c-a61c-2562d2819496.png,b673b10d-d5b9-4758-8efd-99ab8a0f0a83.png', 'c2fe670a-bd76-460f-acc1-016bb25674af.png,a3212bcf-cca9-4df1-b63c-bf9c3c1cee80.jpg');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `feedback_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_id` bigint(11) unsigned NOT NULL COMMENT '创建者',
  `role_id` bigint(11) unsigned NOT NULL COMMENT '处理反馈信息用户的角色',
  `operate_id` bigint(11) unsigned DEFAULT NULL COMMENT '反馈信息处理者',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '反馈信息类型，0：咨询，1：建议，2：举报，3：投诉，4：报修，5：公告',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '处理状态，0：未处理，1：处理中，2：处理完成',
  `info` text NOT NULL COMMENT '反馈内容',
  `url` varchar(100) DEFAULT NULL,
  `utime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
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
  `doorplate` varchar(5) NOT NULL COMMENT '门牌号',
  `name` varchar(40) NOT NULL COMMENT '房源名称',
  `orders` int(1) NOT NULL DEFAULT '1' COMMENT '房源序号，默认1号房',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '出租方式，0:合租，1：整租，2：豪宅',
  `kind` char(5) NOT NULL DEFAULT '00000' COMMENT '第一位表示房间数，第二位表示客厅数，第三位表示厕所数，第四位表示厨房数，第五位表示阳台数，00000表示0室0厅无厕所无厨房无阳台',
  `money` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '租金',
  `cycle` int(3) NOT NULL DEFAULT '30' COMMENT '交费周期，天为单位，默认30天',
  `area` double(6,2) NOT NULL DEFAULT '0.00' COMMENT '房源面积',
  `floor` varchar(5) NOT NULL DEFAULT '1/6' COMMENT '房源楼层',
  `elevator` tinyint(1) NOT NULL DEFAULT '0' COMMENT '房源是否电梯房，0：电梯房，1:非电梯房',
  `orientation` varchar(1) NOT NULL DEFAULT '南' COMMENT '房源朝向',
  `address` varchar(100) NOT NULL COMMENT '房源地址',
  `description` text COMMENT '房源描述',
  `words` varchar(20) NOT NULL COMMENT '搜索关键词，以英文分号间隔，最后一个不加分号',
  `urls` varchar(500) NOT NULL COMMENT '房源图片视频路径，多个以英文分号间隔，结尾不加分号',
  `grade` double(5,2) NOT NULL DEFAULT '5.00' COMMENT '房源分数，0-10',
  `longitude` double(10,6) NOT NULL COMMENT '经度',
  `latitude` double(10,6) NOT NULL COMMENT '纬度',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：未审核，1：审核失败，2：出租中，3：空闲',
  PRIMARY KEY (`house_id`),
  KEY `id_idx` (`house_id`) USING BTREE,
  KEY `h_uid_f` (`user_id`),
  CONSTRAINT `h_uid_f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of house
-- ----------------------------
INSERT INTO `house` VALUES ('1', '5', '2033', '完美生活小区新房', '1', '0', '11111', '0.00', '30', '43.00', '2/6', '0', '南', '湖南省长沙市滨湖西路36号', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈', '精装修,电梯房,首次出租', '38b1103e-5fc3-49c1-9845-0923bd947aed.jpg,78298baf-7893-4818-a519-90fa093800fe.jpg,59eeb1f2-0e2c-44da-9679-36370a6a72a4.jpg,8548e8ed-6e67-49e3-beb2-8285fb959a52.jpg,f8481a38-6114-48f9-b6c2-fae07a59ec41.jpg,d1506cbd-50f1-4808-bf69-d967f51f771e.jpg,3da0583a-4721-48b2-94ec-a80ba803de62.jpg', '6.00', '113.072149', '28.277161', '0');
INSERT INTO `house` VALUES ('2', '5', '4033', '霞凝小区3居室-01卧', '1', '0', '10000', '2000.00', '30', '9.00', '4/6', '0', '南', '湖南省长沙市开福区霞凝小区(青秀路西80米) ', '这是三居室中的主卧，朝南；房间宽敞明亮，粉灰风格，水晶粉与金属灰，适合爱拍照的你。清风徐来，心情舒畅，美好的生活从这里开始，我在自如等您哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈。', '精装修,电梯房,首次出租', 'cb89602f-2904-4f0f-9d73-208af9028705.jpg,5177d110-024c-43e5-837a-eb2a93baf4c0.jpg,62a30c83-a0ae-4445-9df3-cabaec1ac4c6.jpg,52cbbec1-42d4-4d71-b5eb-b4753c6cba38.jpg,486b6e8b-1516-4dcd-88bd-5df5cca8accd.jpg,316b38fd-7272-408d-ab4a-9838b3a92411.jpg,f748d45f-0db2-4410-8d4f-28377ae6b3cc.jpg,57e8c763-f782-4f0d-8eaa-1290d2a29aaf.jpg,8340d337-b7ce-4c69-a6d4-4ecb26a9a0fe.jpg', '5.00', '112.959810', '28.348695', '0');
INSERT INTO `house` VALUES ('4', '5', '2033', ' 板塘小区', '1', '0', '21111', '3990.00', '30', '44.00', '2/6', '0', '南', '长沙市开福区中青路1202号', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈', '精装修,电梯房,首次出租', '90b91f7a-3eaf-4ff6-8b17-16367c46b712.jpg,34da1f4c-7477-4ae4-8d44-dea1a3d47219.jpg', '5.00', '113.003634', '28.345989', '0');
INSERT INTO `house` VALUES ('5', '5', '4033', ' 幸福家园', '1', '0', '21111', '3425.00', '30', '55.00', '2/6', '0', '南', '长沙市长沙县开元东路1307号', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈', '精装修,电梯房,首次出租', '0de9645f-45c4-4136-89c1-d9b182f8c3a0.jpg,1c4ff698-438d-441a-ba0a-cdaaf77f532f.jpg', '5.00', '113.169851', '28.254541', '0');
INSERT INTO `house` VALUES ('6', '5', '2033', ' 银星小区', '2', '0', '11111', '2000.00', '30', '11.11', '2/6', '0', '南', '长沙市望城区银杉路', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈', '精装修,电梯房,首次出租', 'a2116cb3-b853-4723-82a4-3071fd47de6d.jpg,c29317fb-02c0-4996-9c37-915e6530322b.jpg', '5.00', '112.952565', '28.276938', '0');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `log_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `content` varchar(100) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(2) DEFAULT NULL COMMENT '操作类型，增删改查',
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
  `name` varchar(10) NOT NULL COMMENT '权限名称',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '权限类型',
  `permission_value` varchar(40) DEFAULT '' COMMENT '权限值',
  `url` varchar(40) DEFAULT NULL COMMENT '权限路径',
  `icon` varchar(100) DEFAULT NULL COMMENT '权限图标',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '权限状态，0：有效，1：无效',
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `unique_idx` (`name`) USING BTREE,
  KEY `id_idx` (`permission_id`) USING BTREE,
  KEY `pid_f` (`parent_id`),
  CONSTRAINT `pid_f` FOREIGN KEY (`parent_id`) REFERENCES `permission` (`permission_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', null, '用户信息管理', '1', '', null, null, '0');
INSERT INTO `permission` VALUES ('2', null, '房屋资源管理', '1', '', null, null, '0');
INSERT INTO `permission` VALUES ('3', '1', '用户管理', '2', 'user:read', '/user/listUser', 'icon4.png', '0');
INSERT INTO `permission` VALUES ('4', '1', '角色管理', '2', 'role:read', '/role/listRole', null, '0');
INSERT INTO `permission` VALUES ('5', '3', '新增用户', '3', 'user:create', '/user/addUser', null, '0');
INSERT INTO `permission` VALUES ('6', '3', '编辑用户', '3', 'user:update', '/user/updateUser', 'icon4.png', '0');
INSERT INTO `permission` VALUES ('7', '3', '删除用户', '3', 'user:delete', '/user/deleteUser', 'icon4.png', '0');
INSERT INTO `permission` VALUES ('8', '4', '新增角色', '3', 'role:create', '/role/showAdd', 'icon4.png', '0');
INSERT INTO `permission` VALUES ('9', '4', '编辑角色', '3', 'role:update', '/role/showUpdate', 'icon4.png', '0');
INSERT INTO `permission` VALUES ('10', '4', '删除角色', '3', 'role:delete', '/role/deleteRole', 'icon4.png', '0');
INSERT INTO `permission` VALUES ('11', '1', '权限管理', '2', 'permission:read', '/permission/listPermission', 'icon4.png', '0');
INSERT INTO `permission` VALUES ('12', '11', '新增权限', '3', 'permission:create', '/permission/showAdd', null, '0');
INSERT INTO `permission` VALUES ('13', '11', '编辑权限', '3', 'permission:update', '/permission/showUpdate', null, '0');
INSERT INTO `permission` VALUES ('14', '11', '删除权限', '3', 'permission:delete', '/permission/deletePermission', null, '0');
INSERT INTO `permission` VALUES ('15', null, '租房业务管理', '1', null, null, null, '0');
INSERT INTO `permission` VALUES ('16', null, '反馈信息管理', '1', null, null, null, '0');
INSERT INTO `permission` VALUES ('17', '1', '评价管理', '2', 'comment:read', '/comment/index', null, '0');
INSERT INTO `permission` VALUES ('18', '1', '钱包管理', '2', 'money:read', '/money/index', 'icon4.png', '0');
INSERT INTO `permission` VALUES ('19', '17', '新增评价', '3', 'comment:create', '/comment/create', null, '0');
INSERT INTO `permission` VALUES ('20', '17', '编辑评价', '3', 'comment:update', '/comment/update', null, '0');
INSERT INTO `permission` VALUES ('21', '17', '删除评价', '3', 'comment:delete', '/comment/delete', null, '0');
INSERT INTO `permission` VALUES ('22', '18', '新增钱包', '3', 'money:create', '/money/create', null, '0');
INSERT INTO `permission` VALUES ('23', '18', '编辑钱包', '3', 'money:update', '/money/update', null, '0');
INSERT INTO `permission` VALUES ('24', '18', '删除钱包', '3', 'money:delete', '/money/delete', null, '0');
INSERT INTO `permission` VALUES ('25', '2', '房源管理', '2', 'house:read', '/house/read', null, '0');
INSERT INTO `permission` VALUES ('26', '15', '合同管理', '2', 'contract:read', '/contract/index', null, '0');
INSERT INTO `permission` VALUES ('27', '1', '收藏管理', '2', 'cart:read', '/cart/index', null, '0');
INSERT INTO `permission` VALUES ('28', '16', '报修管理', '2', 'repair:read', '/repair/index', null, '0');
INSERT INTO `permission` VALUES ('29', '16', '举报管理', '2', 'report:read', '/report/index', null, '0');
INSERT INTO `permission` VALUES ('30', '16', '投诉管理', '2', 'complaint:read', '/complaint/index', null, '0');
INSERT INTO `permission` VALUES ('31', '16', '建议管理', '2', 'suggest:read', '/suggest/read', null, '0');
INSERT INTO `permission` VALUES ('32', '25', '新增房源', '3', 'house:create', '/house/create', null, '0');
INSERT INTO `permission` VALUES ('33', '25', '编辑房源', '3', 'house:update', '/manager/showUpdateHouse', null, '0');
INSERT INTO `permission` VALUES ('34', '25', '删除房源', '3', 'house:delete', '/house/delete', null, '0');
INSERT INTO `permission` VALUES ('35', '26', '新增合同', '3', 'contract:create', '/contract/create', null, '0');
INSERT INTO `permission` VALUES ('36', '26', '编辑合同', '3', 'contract:update', '/contract/update', null, '0');
INSERT INTO `permission` VALUES ('37', '26', '删除合同', '3', 'contract:delete', '/contract/delete', null, '0');
INSERT INTO `permission` VALUES ('38', '27', '新增收藏', '3', 'cart:create', '/cart/create', null, '0');
INSERT INTO `permission` VALUES ('39', '27', '修改收藏', '3', 'cart:update', '/cart/update', null, '0');
INSERT INTO `permission` VALUES ('40', '27', '删除收藏', '3', 'cart:delete', '/cart/delete', null, '0');
INSERT INTO `permission` VALUES ('41', '28', '新增报修', '3', 'repair:create', '/repair/create', null, '0');
INSERT INTO `permission` VALUES ('44', '28', '编辑报修', '3', 'repair:update', '/repair/update', null, '0');
INSERT INTO `permission` VALUES ('45', '28', '删除报修', '3', 'repair:delete', '/repair/delete', null, '0');
INSERT INTO `permission` VALUES ('46', '29', '新增举报', '3', 'report:create', '/report/create', null, '0');
INSERT INTO `permission` VALUES ('47', '29', '编辑举报', '3', 'report:update', '/report/update', null, '0');
INSERT INTO `permission` VALUES ('48', '29', '删除举报', '3', 'report:delete', '/report/delete', null, '0');
INSERT INTO `permission` VALUES ('49', '30', '新增投诉', '3', 'complaint:create', '/complaint/create', null, '0');
INSERT INTO `permission` VALUES ('50', '30', '编辑投诉', '3', 'complaint:update', '/complaint/update', null, '0');
INSERT INTO `permission` VALUES ('51', '30', '删除投诉', '3', 'complaint:delete', '/complaint/delete', null, '0');
INSERT INTO `permission` VALUES ('52', '31', '新增建议', '3', 'suggest:create', '/suggest/create', null, '0');
INSERT INTO `permission` VALUES ('53', '31', '编辑建议', '3', 'suggest:update', '/suggest/update', null, '0');
INSERT INTO `permission` VALUES ('54', '31', '删除建议', '3', 'suggest:delete', '/suggest/delete', null, '0');
INSERT INTO `permission` VALUES ('55', null, '系统管理', '1', null, null, null, '0');
INSERT INTO `permission` VALUES ('56', '55', '日志管理', '2', 'log:read', '/log/read', null, '0');
INSERT INTO `permission` VALUES ('57', '56', '新增日志', '3', 'log:create', '/log/create', null, '0');
INSERT INTO `permission` VALUES ('58', '56', '编辑日志', '3', 'log:update', '/log/update', null, '0');
INSERT INTO `permission` VALUES ('59', '56', '删除日志', '3', 'log:delete', '/log/delete', null, '0');
INSERT INTO `permission` VALUES ('60', '16', '公告管理', '2', 'notice:read', '/notice/read', null, '0');
INSERT INTO `permission` VALUES ('61', '60', '新增公告', '3', 'notice:create', '/notice/create', null, '0');
INSERT INTO `permission` VALUES ('62', '60', '编辑公告', '3', 'notice:update', '/notice/update', null, '0');
INSERT INTO `permission` VALUES ('63', '60', '删除公告', '3', 'notice:delete', '/notice/delete', null, '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name` varchar(10) NOT NULL COMMENT '角色名称',
  `description` text COMMENT '角色描述',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:有效，1：无效',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `unique_idx` (`name`) USING BTREE,
  KEY `id_idx` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', null, '0');
INSERT INTO `role` VALUES ('2', '业务管理员', null, '0');
INSERT INTO `role` VALUES ('3', '一般管理员', null, '0');
INSERT INTO `role` VALUES ('4', '租客', null, '0');
INSERT INTO `role` VALUES ('5', '房东', null, '0');

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
  `user_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户身份，0:"管理员"，1:"租房客"，2:"房东"',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态，0：有效，1：无效',
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `head` varchar(100) DEFAULT NULL COMMENT '头像路径',
  `money` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '用户钱包金额',
  `phone` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `photo` varchar(100) DEFAULT NULL COMMENT '身份证照片路径',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户性别，0：女，1：男',
  `reputation` double(5,2) NOT NULL DEFAULT '5.00' COMMENT '用户信誉值，0-10分',
  `email` varchar(320) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_idx` (`username`) USING BTREE,
  KEY `id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '系统管理员', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:25', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('2', '业务管理员', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '2019-04-19 10:25:28', '2019-06-06 21:05:41', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('3', '一般管理员', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:20', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('4', '租客', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:18', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('5', '房东', 'e10adc3949ba59abbe56e057f20f883e', '2', '0', '2019-04-19 10:25:28', '2019-06-06 21:05:34', null, '0.00', '15616460426', null, null, null, '1', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('8', '系统管理员1', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:14', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('9', '业务管理员1', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:11', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('10', '业务管理员2', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:09', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('11', '一般管理员1', 'e10adc3949ba59abbe56e057f20f883e', '0', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:07', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('12', '租客1', '96e79218965eb72c92a549dd5a330112', '1', '0', '2019-04-19 10:25:28', '2019-06-06 21:33:43', '0a948198-c703-48fc-aab5-8e6141d76511.jpg', '0.00', '15616460426', '', null, null, '0', '8.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('13', '房东1', 'e10adc3949ba59abbe56e057f20f883e', '2', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:02', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('14', '房东2', 'e10adc3949ba59abbe56e057f20f883e', '2', '0', '2019-04-19 10:25:28', '2019-05-30 20:28:00', null, '0.00', null, null, null, null, '0', '5.00', '2892661384@qq.com');
INSERT INTO `user` VALUES ('15', '测试', 'e10adc3949ba59abbe56e057f20f883e', '1', '0', '2019-05-30 20:25:32', '2019-06-06 21:05:37', null, '0.00', null, null, null, null, '0', '5.00', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '2', '2');
INSERT INTO `user_role` VALUES ('3', '3', '3');
INSERT INTO `user_role` VALUES ('4', '4', '4');
INSERT INTO `user_role` VALUES ('5', '5', '5');
INSERT INTO `user_role` VALUES ('6', '8', '1');
INSERT INTO `user_role` VALUES ('7', '9', '2');
INSERT INTO `user_role` VALUES ('8', '10', '2');
INSERT INTO `user_role` VALUES ('9', '11', '3');
INSERT INTO `user_role` VALUES ('10', '12', '4');
INSERT INTO `user_role` VALUES ('11', '13', '5');
INSERT INTO `user_role` VALUES ('12', '14', '5');
INSERT INTO `user_role` VALUES ('13', '15', '4');
