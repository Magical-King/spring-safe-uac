/*
 Navicat Premium Data Transfer

 Source Server         : 10.10.180.28
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 10.10.180.28:3306
 Source Schema         : gz_security

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 30/11/2020 16:47:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gz_dept
-- ----------------------------
DROP TABLE IF EXISTS `gz_dept`;
CREATE TABLE `gz_dept` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dept_id` varchar(50) DEFAULT NULL,
  `dept_parent_id` varchar(50) DEFAULT NULL,
  `org_id` varchar(50) NOT NULL,
  `dept_name` varchar(50) DEFAULT NULL,
  `org_tel` varchar(50) DEFAULT NULL,
  `org_fax` varchar(50) DEFAULT NULL,
  `org_address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_ip_white
-- ----------------------------
DROP TABLE IF EXISTS `gz_ip_white`;
CREATE TABLE `gz_ip_white` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `ip_segment` varchar(255) NOT NULL,
  `ip_start` bigint(11) NOT NULL,
  `ip_end` bigint(11) NOT NULL,
  `create_by` varchar(50) NOT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_log
-- ----------------------------
DROP TABLE IF EXISTS `gz_log`;
CREATE TABLE `gz_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `event_category` varchar(50) NOT NULL,
  `event_level` int(2) NOT NULL DEFAULT '1',
  `event_type` varchar(50) NOT NULL,
  `message` text NOT NULL,
  `exception_code` int(10) DEFAULT NULL,
  `is_exception` tinyint(1) NOT NULL DEFAULT '0',
  `is_success` tinyint(1) NOT NULL DEFAULT '1',
  `request_uri` varchar(255) DEFAULT NULL,
  `originate` int(2) NOT NULL DEFAULT '1',
  `process_time` bigint(20) DEFAULT NULL,
  `server_addr` varchar(50) DEFAULT NULL,
  `remote_addr` varchar(50) DEFAULT NULL,
  `remote_host` varchar(50) DEFAULT NULL,
  `remote_port` int(10) DEFAULT NULL,
  `content_type` varchar(100) DEFAULT NULL,
  `method_name` varchar(50) DEFAULT NULL,
  `method_parameter` text,
  `request_parameter` text,
  `method_return` text,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2091 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_mail
-- ----------------------------
DROP TABLE IF EXISTS `gz_mail`;
CREATE TABLE `gz_mail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mail_sender` varchar(100) NOT NULL,
  `mail_recipient` varchar(100) NOT NULL,
  `mail_subject` varchar(100) NOT NULL,
  `mail_content_text` text NOT NULL,
  `mail_status` int(2) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `try_number` int(2) DEFAULT NULL,
  `success_date` timestamp NULL DEFAULT NULL,
  `memo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_organization
-- ----------------------------
DROP TABLE IF EXISTS `gz_organization`;
CREATE TABLE `gz_organization` (
  `org_id` varchar(50) NOT NULL,
  `org_parent_id` varchar(50) DEFAULT NULL,
  `org_code` varchar(50) DEFAULT NULL,
  `org_type` varchar(50) DEFAULT NULL,
  `org_name` varchar(50) NOT NULL,
  `org_tel` varchar(50) DEFAULT NULL,
  `org_fax` varchar(50) DEFAULT NULL,
  `org_postcode` varchar(18) DEFAULT NULL,
  `org_email` varchar(50) DEFAULT NULL,
  `org_address` varchar(100) DEFAULT NULL,
  `org_website` varchar(100) DEFAULT NULL,
  `org_description` varchar(1000) DEFAULT NULL,
  `org_is_invalid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_permission
-- ----------------------------
DROP TABLE IF EXISTS `gz_permission`;
CREATE TABLE `gz_permission` (
  `perm_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `perm_type` int(1) NOT NULL DEFAULT '2',
  `perm_parent_id` int(10) DEFAULT '0',
  `perm_parent_ids` varchar(100) DEFAULT NULL,
  `perm_name` varchar(50) DEFAULT NULL,
  `component` varchar(50) DEFAULT NULL,
  `iframe_url` varchar(1000) DEFAULT NULL,
  `perm_url_prefix` varchar(50) DEFAULT NULL,
  `perm_url` varchar(100) DEFAULT NULL,
  `perm_status` tinyint(1) NOT NULL DEFAULT '1',
  `perm_role_type` varchar(50) DEFAULT NULL,
  `perm_order` int(10) NOT NULL DEFAULT '0',
  `perm_css` varchar(50) DEFAULT NULL,
  `perm_ico` varchar(50) DEFAULT NULL,
  `perm_is_show` tinyint(1) NOT NULL DEFAULT '1',
  `perm_create_by` varchar(50) DEFAULT NULL,
  `perm_create_date` timestamp NULL DEFAULT NULL,
  `perm_update_by` varchar(50) DEFAULT NULL,
  `perm_update_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`perm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100002 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_review
-- ----------------------------
DROP TABLE IF EXISTS `gz_review`;
CREATE TABLE `gz_review` (
  `review_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `review_type_id` int(10) NOT NULL,
  `review_obj_id` varchar(50) NOT NULL,
  `review_auth_id` text NOT NULL,
  `review_summary` varchar(200) NOT NULL,
  `review_status` int(2) NOT NULL DEFAULT '0',
  `review_create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_review_group
-- ----------------------------
DROP TABLE IF EXISTS `gz_review_group`;
CREATE TABLE `gz_review_group` (
  `review_group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `review_group_name` varchar(50) NOT NULL,
  `review_group_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`review_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_review_type
-- ----------------------------
DROP TABLE IF EXISTS `gz_review_type`;
CREATE TABLE `gz_review_type` (
  `review_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `review_group_id` int(10) NOT NULL,
  `review_type_name` varchar(50) NOT NULL,
  `review_type_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`review_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_role
-- ----------------------------
DROP TABLE IF EXISTS `gz_role`;
CREATE TABLE `gz_role` (
  `role_id` varchar(50) NOT NULL,
  `role_org_id` varchar(50) NOT NULL,
  `role_type_id` int(10) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `role_status` int(2) NOT NULL DEFAULT '0',
  `role_create_by` varchar(50) DEFAULT NULL,
  `role_create_date` timestamp NULL DEFAULT NULL,
  `role_update_by` varchar(50) DEFAULT NULL,
  `role_update_date` timestamp NULL DEFAULT NULL,
  `role_remarks` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `gz_role_perm`;
CREATE TABLE `gz_role_perm` (
  `role_perm_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `perm_id` int(10) NOT NULL,
  `role_id` varchar(50) NOT NULL,
  PRIMARY KEY (`role_perm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1276 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_role_type
-- ----------------------------
DROP TABLE IF EXISTS `gz_role_type`;
CREATE TABLE `gz_role_type` (
  `role_id` varchar(50) DEFAULT NULL,
  `role_type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_type_name` varchar(50) NOT NULL,
  `role_type_leavel` int(10) NOT NULL,
  PRIMARY KEY (`role_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_security_config
-- ----------------------------
DROP TABLE IF EXISTS `gz_security_config`;
CREATE TABLE `gz_security_config` (
  `config_id` varchar(50) NOT NULL,
  `config_name` varchar(50) DEFAULT NULL,
  `config_key` tinyint(1) NOT NULL DEFAULT '0',
  `config_value` varchar(255) DEFAULT NULL,
  `is_sys` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `remake` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_user
-- ----------------------------
DROP TABLE IF EXISTS `gz_user`;
CREATE TABLE `gz_user` (
  `user_id` varchar(50) NOT NULL,
  `user_org_id` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `user_password` varchar(160) NOT NULL,
  `user_status` int(2) NOT NULL DEFAULT '0',
  `user_email` varchar(60) DEFAULT NULL,
  `user_mobile` varchar(60) DEFAULT NULL,
  `user_identity_number` varchar(18) DEFAULT NULL,
  `user_is_temporary` tinyint(1) NOT NULL DEFAULT '0',
  `user_end_date` date DEFAULT NULL,
  `user_last_login_ip` varchar(50) DEFAULT NULL,
  `user_last_login_date` timestamp NULL DEFAULT NULL,
  `user_last_pwd_change` timestamp NULL DEFAULT NULL,
  `user_last_send_email_date` timestamp NULL DEFAULT NULL,
  `user_login_failure_num` int(2) DEFAULT NULL,
  `user_first_failure_date` timestamp NULL DEFAULT NULL,
  `user_login_failure_date` timestamp NULL DEFAULT NULL,
  `user_lock_date` timestamp NULL DEFAULT NULL,
  `user_lock_cause` varchar(200) DEFAULT NULL,
  `user_pwd_security_level` int(2) NOT NULL DEFAULT '2',
  `user_create_by` varchar(50) NOT NULL,
  `user_create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_update_by` varchar(50) NOT NULL,
  `user_update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_salt` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_user_back
-- ----------------------------
DROP TABLE IF EXISTS `gz_user_back`;
CREATE TABLE `gz_user_back` (
  `user_id` varchar(50) NOT NULL,
  `org_id` varchar(50) DEFAULT NULL,
  `user_name` varchar(50) NOT NULL,
  `user_password` varchar(160) NOT NULL,
  `user_status` int(2) NOT NULL DEFAULT '0',
  `user_email` varchar(60) DEFAULT NULL,
  `user_mobile` varchar(60) DEFAULT NULL,
  `user_identity_number` varchar(18) DEFAULT NULL,
  `user_is_temporary` tinyint(1) NOT NULL DEFAULT '0',
  `user_end_date` date DEFAULT NULL,
  `user_last_login_ip` varchar(50) DEFAULT NULL,
  `user_last_login_date` timestamp NULL DEFAULT NULL,
  `user_last_pwd_change` timestamp NULL DEFAULT NULL,
  `user_login_failure_num` int(2) DEFAULT NULL,
  `user_login_failure_date` timestamp NULL DEFAULT NULL,
  `user_first_failure_date` timestamp NULL DEFAULT NULL,
  `user_lock_date` timestamp NULL DEFAULT NULL,
  `user_lock_cause` varchar(200) DEFAULT NULL,
  `user_pwd_security_level` int(2) NOT NULL DEFAULT '2',
  `user_create_by` varchar(50) NOT NULL,
  `user_create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_update_by` varchar(50) NOT NULL,
  `user_update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_salt` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_user_modified
-- ----------------------------
DROP TABLE IF EXISTS `gz_user_modified`;
CREATE TABLE `gz_user_modified` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `modified_type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_user_role
-- ----------------------------
DROP TABLE IF EXISTS `gz_user_role`;
CREATE TABLE `gz_user_role` (
  `user_role_id` varchar(100) NOT NULL,
  `role_id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_user_ukey
-- ----------------------------
DROP TABLE IF EXISTS `gz_user_ukey`;
CREATE TABLE `gz_user_ukey` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ukey_id` varchar(100) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `public_key` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for gz_version
-- ----------------------------
DROP TABLE IF EXISTS `gz_version`;
CREATE TABLE `gz_version` (
  `id` int(1) NOT NULL DEFAULT '1',
  `version` varchar(50) NOT NULL DEFAULT '0.0',
  `db` int(10) NOT NULL DEFAULT '0',
  `modify` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Triggers structure for table gz_user
-- ----------------------------
DROP TRIGGER IF EXISTS `gz_user_insert_trigger`;
delimiter ;;
CREATE TRIGGER `gz_user_insert_trigger` AFTER INSERT ON `gz_user` FOR EACH ROW BEGIN declare num int(4); set num = (select count(*) from gz_user_modified where user_id=new.user_id and modified_type = 'ADD'); IF num = 0 THEN INSERT INTO gz_user_modified(USER_ID, MODIFIED_TYPE) values (new.user_id,'ADD'); END IF; END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table gz_user
-- ----------------------------
DROP TRIGGER IF EXISTS `gz_user_update_trigger`;
delimiter ;;
CREATE TRIGGER `gz_user_update_trigger` AFTER UPDATE ON `gz_user` FOR EACH ROW BEGIN declare num int(4); set num = (select count(*) from gz_user_modified where user_id=new.user_id and modified_type = 'MODIFIED'); IF num = 0 AND (NEW.USER_PASSWORD<>OLD.USER_PASSWORD OR NEW.USER_ID<>OLD.USER_ID OR NEW.USER_EMAIL<>OLD.USER_EMAIL OR NEW.USER_MOBILE<>OLD.USER_MOBILE OR NEW.USER_IDENTITY_NUMBER<>OLD.USER_IDENTITY_NUMBER) THEN INSERT INTO gz_user_modified(USER_ID, MODIFIED_TYPE) values (new.user_id,'MODIFIED'); END IF; END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table gz_user
-- ----------------------------
DROP TRIGGER IF EXISTS `gz_user_delete_trigger`;
delimiter ;;
CREATE TRIGGER `gz_user_delete_trigger` AFTER DELETE ON `gz_user` FOR EACH ROW BEGIN declare num int(4); set num = (select count(*) from gz_user_modified where user_id=old.user_id and modified_type = 'DELETE'); IF num = 0 THEN INSERT INTO gz_user_modified(USER_ID, MODIFIED_TYPE) values (old.user_id,'DELETE'); END IF; END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
