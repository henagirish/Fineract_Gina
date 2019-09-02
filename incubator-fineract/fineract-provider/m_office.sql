CREATE TABLE `m_office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL,
  `hierarchy` varchar(100) DEFAULT NULL,
  `external_id` varchar(100) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `opening_date` date NOT NULL,
  `cin` varchar(20) DEFAULT NULL,
  `company_name` varchar(20) DEFAULT NULL,
  `company_status` varchar(20) DEFAULT NULL,
  `roc` varchar(20) DEFAULT NULL,
  `incorportated_date` date DEFAULT NULL,
  `funds` int(11) DEFAULT NULL,
  `registration_address` varchar(20) DEFAULT NULL,
  `registration_number` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_org` (`name`),
  UNIQUE KEY `externalid_org` (`external_id`),
  KEY `FK2291C477E2551DCC` (`parent_id`),
  CONSTRAINT `FK2291C477E2551DCC` FOREIGN KEY (`parent_id`) REFERENCES `m_office` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
