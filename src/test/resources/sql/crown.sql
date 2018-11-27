/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : crown

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 11/27/2018 15:40:24 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `path` varchar(64) DEFAULT NULL COMMENT '路径',
  `menu_type` smallint(2) NOT NULL COMMENT '类型:0:目录,1:菜单,2:按钮',
  `icon` varchar(32) DEFAULT NULL COMMENT '菜单图标',
  `create_uid` int(11) NOT NULL COMMENT '创建者ID',
  `update_uid` int(11) NOT NULL COMMENT '修改者ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` smallint(2) NOT NULL COMMENT '状态 0：禁用 1：正常',
  `router` varchar(64) DEFAULT NULL COMMENT '路由',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', '1', 'layui-icon-set', '1', '1', '2018-11-27 14:52:10', '2018-11-27 15:11:15', '0', null), ('23', '1', '用户管理', 'system/user.html', '2', 'layui-icon-username', '1', '1', '2018-11-27 15:10:32', '2018-11-27 15:36:26', '0', 'user'), ('24', '1', '角色管理', 'system/role.html', '2', 'layui-icon-face-surprised', '1', '1', '2018-11-27 15:16:59', '2018-11-27 15:36:31', '0', 'role'), ('25', '1', '菜单管理', 'system/menu.html', '2', 'layui-icon-template', '1', '1', '2018-11-27 15:17:59', '2018-11-27 15:36:37', '0', 'menu'), ('26', '1', '资源管理', 'system/resource.html', '2', 'layui-icon-read', '1', '1', '2018-11-27 15:18:31', '2018-11-27 15:36:45', '0', 'resource'), ('27', '26', '刷新资源', '', '3', 'layui-icon-refresh-3', '1', '1', '2018-11-27 15:19:15', '2018-11-27 15:19:15', '0', null), ('28', '25', '添加', '', '3', 'layui-icon-add-circle-fine', '1', '1', '2018-11-27 15:20:06', '2018-11-27 15:20:06', '0', null), ('29', '25', '修改', '', '3', 'layui-icon-senior', '1', '1', '2018-11-27 15:20:27', '2018-11-27 15:20:27', '0', null), ('30', '25', '删除', '', '3', 'layui-icon-close', '1', '1', '2018-11-27 15:21:14', '2018-11-27 15:21:14', '0', null), ('31', '24', '添加', '', '3', 'layui-icon-add-circle-fine', '1', '1', '2018-11-27 15:20:06', '2018-11-27 15:20:06', '0', null), ('32', '24', '修改', '', '3', 'layui-icon-senior', '1', '1', '2018-11-27 15:20:27', '2018-11-27 15:20:27', '0', null), ('33', '24', '删除', '', '3', 'layui-icon-close', '1', '1', '2018-11-27 15:21:14', '2018-11-27 15:21:14', '0', null), ('34', '23', '添加', '', '3', 'layui-icon-add-circle-fine', '1', '1', '2018-11-27 15:20:06', '2018-11-27 15:20:06', '0', null), ('35', '23', '修改', '', '3', 'layui-icon-senior', '1', '1', '2018-11-27 15:20:27', '2018-11-27 15:20:27', '0', null), ('36', '23', '重置密码', '', '3', 'layui-icon-fire', '1', '1', '2018-11-27 15:21:14', '2018-11-27 15:24:14', '0', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_menu_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_resource`;
CREATE TABLE `sys_menu_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  `resource_id` varchar(32) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单资源关系表';

-- ----------------------------
--  Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` varchar(32) NOT NULL,
  `resource_name` varchar(32) DEFAULT NULL COMMENT '资源名称',
  `mapping` varchar(64) DEFAULT NULL COMMENT '路径映射',
  `method` varchar(6) DEFAULT NULL COMMENT '请求方式',
  `verify` bit(1) DEFAULT NULL COMMENT '是否需要验证',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';

-- ----------------------------
--  Records of `sys_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('0d30e9ad366ce4b4ecef0856bf4e7d65', '重置用户密码', '/user/{id}/password/reset', 'PUT', b'1', '2018-11-16 13:01:34'), ('11048c2f08ca5b5c18c88c5d944f349b', '查询单个菜单', '/menu/{id}', 'GET', b'0', '2018-11-27 14:42:01'), ('162aa00c908133c6846aa5c1173ae0f9', '删除角色', '/role/{id}', 'DELETE', b'1', '2018-11-27 14:42:01'), ('1a9369cbe29619eee1ea191a92e48bfd', '修改角色', '/role/{id}', 'PUT', b'1', '2018-11-27 14:42:01'), ('2778ff24de52e7a97fae7847b093477c', '查询所有角色', '/role/roles', 'GET', b'1', '2018-11-20 16:02:44'), ('2856ae2858a7ad748c2a3ca507068800', '修改密码', '/account/password', 'PUT', b'1', '2018-11-16 13:01:34'), ('35cbac1c6d9e7a11faed85568093fb6b', '获取Token', '/account/token', 'POST', b'0', '2018-11-16 13:01:34'), ('46d3e1d55597a7943ec79f7502c7942a', '修改用户信息', '/user/info', 'PUT', b'1', '2018-11-16 13:01:34'), ('9291c7b9fed9d9c7f10c4f3ccca85251', '清理Token', '/account/token', 'DELETE', b'1', '2018-11-16 13:01:34'), ('93b1a54f4d726cabd366de8944b96f2c', '查询父级菜单(下拉框)', '/menu/combos', 'GET', b'0', '2018-11-27 14:42:01'), ('98b7256081238fe762275effafcb63eb', '设置菜单状态', '/menu/{id}/status', 'PUT', b'1', '2018-11-27 14:42:01'), ('9954cc4aba01dca400bc327845809335', '设置用户状态', '/user/{id}/status', 'PUT', b'1', '2018-11-16 13:01:34'), ('a057d1a98da67a47e0e05254d7c5ded8', '删除菜单', '/menu/{id}', 'DELETE', b'1', '2018-11-27 14:42:01'), ('b47576c58976231d3ede0fd9265320d0', '修改用户', '/user/{id}', 'PUT', b'1', '2018-11-16 13:01:34'), ('ba62aae44174af4514c965c7f3b64728', '修改菜单', '/menu/{id}', 'PUT', b'1', '2018-11-27 14:42:01'), ('c99d9db8780d813083bd4d030674e43a', '查询单个用户', '/user/{id}', 'GET', b'1', '2018-11-16 13:01:34'), ('d5a8d43937dbc7e0729cbe99a7ec3ec6', '获取用户详情', '/user/details', 'GET', b'1', '2018-11-16 13:01:34'), ('e523e00b15e63b23a7851e6f2847f6f5', '查询当个角色', '/role/{id}', 'GET', b'1', '2018-11-16 13:01:34'), ('e89b2d443f77ce7b14c45e2a8f837473', '刷新资源', '/resource/refresh', 'PUT', b'0', '2018-11-27 14:42:01');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `create_uid` int(11) NOT NULL COMMENT '创建者ID',
  `update_uid` int(11) NOT NULL COMMENT '修改者ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '1', '1', '2018-11-12 15:59:43', '2018-11-12 15:59:47', '超级管理员'), ('2', '普通管理员', '1', '1', '2018-11-12 16:00:17', '2018-11-12 16:00:19', '普通管理员');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关系表';

-- ----------------------------
--  Table structure for `sys_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `resource_id` varchar(32) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源关系表';

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `status` smallint(2) NOT NULL COMMENT '状态 0：禁用 1：正常',
  `create_uid` int(11) NOT NULL COMMENT '创建者ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `login_name` varchar(16) NOT NULL COMMENT '登陆名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `ip` varchar(32) DEFAULT NULL COMMENT 'IP地址',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'Crown', 'caratacus@qq.com', '13712345678', '0', '1', '2018-11-05 17:19:05', '2018-11-27 15:33:28', 'crown', '$apr1$crown$WQ2TEXVPUJ8l6N6gm0CGv.', '0:0:0:0:0:0:0:1'), ('18', 'crown1', '11@qq.com', '13718867899', '1', '1', '2018-11-19 18:56:19', '2018-11-26 10:37:33', 'crown1', '$apr1$crown1$NsepppGmlSjqtwPTlaLb1/', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色关系表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('24', '18', '2'), ('29', '1', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
