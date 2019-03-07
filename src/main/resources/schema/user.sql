-- SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for employee
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) not null AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) default null,
  `create_date` datetime default current_timestamp,
  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;