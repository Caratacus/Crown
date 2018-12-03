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

 Date: 12/03/2018 12:04:03 PM
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', '1', 'layui-icon-set', '1', '1', '2018-11-27 14:52:10', '2018-11-27 15:11:15', '0', null), ('23', '1', '用户管理', 'views/user/index.html', '2', 'layui-icon-username', '1', '1', '2018-11-27 15:10:32', '2018-11-28 13:04:46', '0', 'user'), ('24', '1', '角色管理', 'views/role/index.html', '2', 'layui-icon-face-surprised', '1', '1', '2018-11-27 15:16:59', '2018-11-28 13:04:54', '0', 'role'), ('25', '1', '菜单管理', 'views/menu/index.html', '2', 'layui-icon-template', '1', '1', '2018-11-27 15:17:59', '2018-11-28 13:05:03', '0', 'menu'), ('26', '1', '资源管理', 'views/resource/index.html', '2', 'layui-icon-read', '1', '1', '2018-11-27 15:18:31', '2018-11-28 13:05:28', '0', 'resource'), ('27', '26', '刷新资源', '', '3', 'layui-icon-refresh-3', '1', '1', '2018-11-27 15:19:15', '2018-11-27 15:19:15', '0', null), ('28', '25', '添加', '', '3', 'layui-icon-add-circle-fine', '1', '1', '2018-11-27 15:20:06', '2018-11-27 15:20:06', '0', null), ('29', '25', '修改', '', '3', 'layui-icon-senior', '1', '1', '2018-11-27 15:20:27', '2018-11-27 15:20:27', '0', null), ('30', '25', '删除', '', '3', 'layui-icon-close', '1', '1', '2018-11-27 15:21:14', '2018-11-27 15:21:14', '0', null), ('31', '24', '添加', '', '3', 'layui-icon-add-circle-fine', '1', '1', '2018-11-27 15:20:06', '2018-11-27 15:20:06', '0', null), ('32', '24', '修改', '', '3', 'layui-icon-senior', '1', '1', '2018-11-27 15:20:27', '2018-11-27 15:20:27', '0', null), ('33', '24', '删除', '', '3', 'layui-icon-close', '1', '1', '2018-11-27 15:21:14', '2018-11-27 15:21:14', '0', null), ('34', '23', '添加', '', '3', 'layui-icon-add-circle-fine', '1', '1', '2018-11-27 15:20:06', '2018-11-27 15:20:06', '0', null), ('35', '23', '修改', '', '3', 'layui-icon-senior', '1', '1', '2018-11-27 15:20:27', '2018-11-27 15:20:27', '0', null), ('36', '23', '重置密码', '', '3', 'layui-icon-fire', '1', '1', '2018-11-27 15:21:14', '2018-11-27 15:24:14', '0', null);
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
  `resource_name` varchar(32) NOT NULL COMMENT '资源名称',
  `mapping` varchar(64) NOT NULL COMMENT '路径映射',
  `method` varchar(6) NOT NULL COMMENT '请求方式',
  `verify` bit(1) DEFAULT NULL COMMENT '是否需要验证',
  `update_time` datetime DEFAULT NULL,
  `perm` varchar(68) NOT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';

-- ----------------------------
--  Records of `sys_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('15da52a5f4f4a6eac3b0d6f046f040c9', '删除菜单', '/menu/{id}', 'DELETE', b'1', '2018-12-03 11:59:00', 'DELETE:/menu/{id}'), ('2fd8f53f78a2ec49f551caa88f2d676f', '查询所有角色', '/role/roles', 'GET', b'0', '2018-12-03 11:59:00', 'GET:/role/roles'), ('306628750eb9d14b64193fdfc42a2d61', '重置用户密码', '/user/{id}/password/reset', 'PUT', b'0', '2018-12-03 11:59:00', 'PUT:/user/{id}/password/reset'), ('375edb54e85dc981c8df62c76f3782ed', '查询单个菜单', '/menu/{id}', 'GET', b'0', '2018-12-03 11:59:00', 'GET:/menu/{id}'), ('37687195b63b44e0d22436080de117bc', '修改菜单', '/menu/{id}', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/menu/{id}'), ('49764a50d0e0f74790ced2586b03bff8', '设置用户状态', '/user/{id}/status', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/user/{id}/status'), ('4f8b3e385f4983dcc91f95677fbc9eb4', '修改用户信息', '/user/perm/bottons', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/user/perm/bottons'), ('5446595d232bb7a83c02b2710c5b537c', '获取用户详情', '/user/details', 'GET', b'0', '2018-12-03 11:59:00', 'GET:/user/details'), ('54522abbe7d3a2a40e7edbc9339acc50', '修改用户', '/user/{id}', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/user/{id}'), ('5e5b895e3d4dcef7510ea77fa6b4fb32', '查询单个角色', '/role/{id}', 'GET', b'0', '2018-12-03 11:59:00', 'GET:/role/{id}'), ('6ab0f8a49671e489f11a1bef2fcaf102', '清除Token', '/account/token', 'DELETE', b'0', '2018-12-03 11:59:00', 'DELETE:/account/token'), ('6c3514540b1e2e1589a9c9566273c7c7', '查询单个用户', '/user/{id}', 'GET', b'0', '2018-12-03 11:59:00', 'GET:/user/{id}'), ('7eb82846df62890a8a7a2cebc1cd76f7', '修改用户信息', '/user/info', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/user/info'), ('842e33410b5a97b6c797e4782c97a90e', '获取Token', '/account/token', 'POST', b'0', '2018-12-03 11:59:00', 'POST:/account/token'), ('b0a0776c8585893fccfd028c67689a97', '修改用户信息', '/user/perm/menus', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/user/perm/menus'), ('b7b1c22076c0d28c8d0fe4c0b09b6a6e', '修改角色', '/role/{id}', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/role/{id}'), ('c2db9729dcd4a7d703e45411bb445dfd', '修改密码', '/account/password', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/account/password'), ('d83a249e0e0ed84806ecba2fd8c7148d', '查询父级菜单(下拉框)', '/menu/combos', 'GET', b'0', '2018-12-03 11:59:00', 'GET:/menu/combos'), ('d88bbe26ec9af41d418924c15bfb3a21', '刷新资源', '/resource/refresh', 'PUT', b'0', '2018-12-03 11:59:00', 'PUT:/resource/refresh'), ('ddf4e3fd39264da50eec54cfd02731f4', '删除角色', '/role/{id}', 'DELETE', b'1', '2018-12-03 11:59:00', 'DELETE:/role/{id}'), ('de8b6406105c3ea1c71de78e0870595e', '设置菜单状态', '/menu/{id}/status', 'PUT', b'1', '2018-12-03 11:59:00', 'PUT:/menu/{id}/status');
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'Crown', 'caratacus@qq.com', '13712345678', '0', '1', '2018-11-05 17:19:05', '2018-12-03 12:02:11', 'crown', '$apr1$crown$WQ2TEXVPUJ8l6N6gm0CGv.', '0:0:0:0:0:0:0:1'), ('18', 'crown1', '11@qq.com', '13718867899', '1', '1', '2018-11-19 18:56:19', '2018-11-26 10:37:33', 'crown1', '$apr1$crown1$NsepppGmlSjqtwPTlaLb1/', null);
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
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色关系表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('24', '18', '2'), ('49', '1', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
