/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50560
 Source Host           : localhost:3306
 Source Schema         : cdms

 Target Server Type    : MySQL
 Target Server Version : 50560
 File Encoding         : 65001

 Date: 28/06/2019 14:34:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `class_id` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tea_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `message` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`class_id`) USING BTREE,
  INDEX `tea_id`(`tea_id`) USING BTREE,
  CONSTRAINT `clazz_ibfk_1` FOREIGN KEY (`tea_id`) REFERENCES `teacher` (`tea_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES ('162013', '200001', '这是162013班的信息');
INSERT INTO `clazz` VALUES ('162014', '200002', '这是162014班的信息');

-- ----------------------------
-- Table structure for course_design
-- ----------------------------
DROP TABLE IF EXISTS `course_design`;
CREATE TABLE `course_design`  (
  `cd_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cd_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of course_design
-- ----------------------------
INSERT INTO `course_design` VALUES (1, '课设title1', '<p>课设content1<br/></p>');
INSERT INTO `course_design` VALUES (2, '课设title2', '<p>课设content2<br/></p>');
INSERT INTO `course_design` VALUES (3, '课设title3', '<p>课设content3<br/></p>');
INSERT INTO `course_design` VALUES (4, '课设title4', '<p>课设content4<br/></p>');

-- ----------------------------
-- Table structure for organize
-- ----------------------------
DROP TABLE IF EXISTS `organize`;
CREATE TABLE `organize`  (
  `org_id` int(11) NOT NULL AUTO_INCREMENT,
  `cd_id` int(11) NULL DEFAULT NULL,
  `stu_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `message` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`org_id`) USING BTREE,
  INDEX `cd_id`(`cd_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  CONSTRAINT `organize_ibfk_1` FOREIGN KEY (`cd_id`) REFERENCES `course_design` (`cd_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `organize_ibfk_2` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of organize
-- ----------------------------
INSERT INTO `organize` VALUES (1, 1, '16201323', '队伍1');

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `re_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `filename` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade` int(3) NULL DEFAULT NULL,
  PRIMARY KEY (`re_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES ('162013233', '主题一', '<p>内容一<br/></p>', NULL, 99);
INSERT INTO `report` VALUES ('162013234', '主题二', '<p>内容二<br/></p>', NULL, -1);
INSERT INTO `report` VALUES ('162013236', '6主题', '<p>23-6-内容-测试<br/></p>', NULL, -1);
INSERT INTO `report` VALUES ('162013239', '文档测试', '<p>文档上传测试是<br/></p>', '16201323-9.doc', -1);
INSERT INTO `report` VALUES ('162013244', '16201324主题4', '<p>16201324内容4<br/></p>', NULL, 89);
INSERT INTO `report` VALUES ('162013258', '25-8-主题-测试', '<p>25-8-内容-测试<br/></p>', NULL, -1);
INSERT INTO `report` VALUES ('162013259', 'hello', '<p>16201325-9报告<br/></p>', '16201325-9.doc', -1);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_id` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `contact` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stu_id`) USING BTREE,
  INDEX `class_id`(`class_id`) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `clazz` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('16201323', '吴国伟', '男', '162013', 22, '14796345488', '202cb962ac59075b964b07152d234b70');
INSERT INTO `student` VALUES ('16201324', '小强', '男', '162013', 12, '12312312312', '202cb962ac59075b964b07152d234b70');
INSERT INTO `student` VALUES ('16201325', '阿萨德25', '女', '162013', 21, '12312312312', '202cb962ac59075b964b07152d234b70');
INSERT INTO `student` VALUES ('16201401', '14啊1', '女', '162014', 12, '12312312312', '202cb962ac59075b964b07152d234b70');

-- ----------------------------
-- Table structure for student_organize
-- ----------------------------
DROP TABLE IF EXISTS `student_organize`;
CREATE TABLE `student_organize`  (
  `stu_org_id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `org_id` int(11) NULL DEFAULT NULL,
  `grade` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`stu_org_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  INDEX `org_id`(`org_id`) USING BTREE,
  CONSTRAINT `student_organize_ibfk_1` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `student_organize_ibfk_2` FOREIGN KEY (`org_id`) REFERENCES `organize` (`org_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student_organize
-- ----------------------------
INSERT INTO `student_organize` VALUES (1, '16201323', 1, -1);
INSERT INTO `student_organize` VALUES (2, '16201324', 1, 88);
INSERT INTO `student_organize` VALUES (3, '16201325', 1, -1);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tea_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `contact` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tea_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('200001', '教师1', '女', 34, '12312312312', '202cb962ac59075b964b07152d234b70');
INSERT INTO `teacher` VALUES ('200002', '教师2', '男', 34, '12312312312', '202cb962ac59075b964b07152d234b70');

-- ----------------------------
-- Table structure for weekly_report
-- ----------------------------
DROP TABLE IF EXISTS `weekly_report`;
CREATE TABLE `weekly_report`  (
  `we_re_id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `re_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`we_re_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  INDEX `re_id`(`re_id`) USING BTREE,
  CONSTRAINT `weekly_report_ibfk_1` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `weekly_report_ibfk_2` FOREIGN KEY (`re_id`) REFERENCES `report` (`re_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of weekly_report
-- ----------------------------
INSERT INTO `weekly_report` VALUES (3, '16201323', '162013233');
INSERT INTO `weekly_report` VALUES (4, '16201323', '162013234');
INSERT INTO `weekly_report` VALUES (5, '16201324', '162013244');
INSERT INTO `weekly_report` VALUES (6, '16201323', '162013236');
INSERT INTO `weekly_report` VALUES (7, '16201325', '162013258');
INSERT INTO `weekly_report` VALUES (13, '16201323', '162013239');
INSERT INTO `weekly_report` VALUES (15, '16201325', '162013259');

SET FOREIGN_KEY_CHECKS = 1;
