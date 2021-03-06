create database  if not exists clouddisk;
use clouddisk;

CREATE TABLE `user` (
  `user_id` char(32) NOT NULL,
  `file_repository_id` char(32) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(20) NOT NULL,
  `register_time` datetime NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `file_respository_id_UNIQUE` (`file_repository_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `user_email_UNIQUE` (`email`)
) ENGINE=InnoDB ;

CREATE TABLE `my_file` (
  `file_id` char(32) NOT NULL,
  `file_repository_id` char(32) NOT NULL,
  `parent_folder_id` char(32) NOT NULL DEFAULT '0',
  `file_name` varchar(45) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `upload_time` datetime NOT NULL,
  `size` double NOT NULL,
  `type` varchar(100) NOT NULL,
  `state` char(1) NOT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB ;

CREATE TABLE `file_folder` (
  `file_folder_id` char(32) NOT NULL,
  `parent_folder_id` char(32) NOT NULL DEFAULT '0',
  `file_folder_name` varchar(45) NOT NULL DEFAULT '"新建文件夹"',
  `create_time` datetime NOT NULL,
  `file_repository_id` char(32) NOT NULL,
  `file_folder_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`file_folder_id`)
) ENGINE=InnoDB ;

CREATE TABLE `file_repository` (
  `file_repository_id` char(32) NOT NULL,
  `user_id` char(32) NOT NULL,
  `current_size` double DEFAULT '0',
  `max_size` double DEFAULT '15728640',
  PRIMARY KEY (`file_repository_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB ;

CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `admin_name_UNIQUE` (`admin_name`)
) ENGINE=InnoDB

