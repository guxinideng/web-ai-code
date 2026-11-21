/*M!999999\- enable the sandbox mode */ 

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `drug_code` varchar(20) NOT NULL COMMENT '药品编码',
  `generic_name` varchar(100) NOT NULL COMMENT '通用名',
  `brand_name` varchar(100) DEFAULT NULL COMMENT '商品名',
  `category` varchar(30) NOT NULL COMMENT '药品类别',
  `dosage_form` enum('片剂','胶囊','颗粒','口服液','注射液','软膏','滴眼液','喷雾剂') NOT NULL COMMENT '剂型',
  `specification` varchar(50) NOT NULL COMMENT '规格',
  `manufacturer` varchar(100) NOT NULL COMMENT '生产厂家',
  `approval_no` varchar(30) DEFAULT NULL COMMENT '批准文号',
  `unit_price` decimal(8,2) NOT NULL COMMENT '单价(元)',
  `stock_qty` int(11) NOT NULL DEFAULT 0 COMMENT '库存数量',
  `is_otc` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1=非处方药 0=处方药',
  `need_cold_chain` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否需冷藏',
  `production_date` date NOT NULL COMMENT '生产日期',
  `expiry_date` date NOT NULL COMMENT '有效期至',
  `storage_cond` varchar(50) DEFAULT NULL COMMENT '储存条件',
  `indications` varchar(200) DEFAULT NULL COMMENT '适应症',
  `contraindications` varchar(200) DEFAULT NULL COMMENT '禁忌',
  `created_at` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_drug_code` (`drug_code`),
  KEY `idx_category` (`category`),
  KEY `idx_expiry` (`expiry_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='药品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `person_no` varchar(10) NOT NULL COMMENT '工号',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `surname` varchar(4) NOT NULL COMMENT '姓氏(方便按姓查找)',
  `gender` enum('男','女') NOT NULL COMMENT '性别',
  `birth_date` date NOT NULL COMMENT '出生日期',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号(虚构)',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱(虚构)',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `department` varchar(20) NOT NULL COMMENT '部门',
  `job_title` varchar(30) NOT NULL COMMENT '职位',
  `education` enum('高中','大专','本科','硕士','博士') NOT NULL COMMENT '学历',
  `marital_status` enum('未婚','已婚','离异') NOT NULL DEFAULT '未婚' COMMENT '婚姻状况',
  `hire_date` date NOT NULL COMMENT '入职日期',
  `salary` decimal(10,2) NOT NULL COMMENT '月薪(元)',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1=在职 0=已离职',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_person_no` (`person_no`),
  KEY `idx_surname` (`surname`),
  KEY `idx_name` (`name`),
  KEY `idx_department` (`department`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='人员信息表(虚构数据,学习用)';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID,唯一标识',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `gender` char(1) DEFAULT '男' COMMENT '性别',
  `qq_num` varchar(15) DEFAULT NULL COMMENT 'QQ号码',
  `wechat` varchar(20) DEFAULT NULL COMMENT '微信账号',
  `create_time` date DEFAULT NULL COMMENT '入职日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  CONSTRAINT `chk_age` CHECK (`age` > 0 and `age` <= 120)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_plus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

