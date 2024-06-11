drop table if exists theater_play;
CREATE TABLE IF NOT EXISTS `theater_play`
(
                                `premiere` date DEFAULT NULL,
                                `created_at` datetime(6) DEFAULT NULL,
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `updated_at` datetime(6) DEFAULT NULL,
                                `title` varchar(100) NOT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;
drop table if exists scheduled_play;
CREATE TABLE IF NOT EXISTS `scheduled_play`
(
                                  `date` date NOT NULL,
                                  `time` time(6) NOT NULL,
                                  `total_reserved_seats` int NOT NULL,
                                  `total_seats` int NOT NULL,
                                  `created_at` datetime(6) DEFAULT NULL,
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `theater_play_id` bigint NOT NULL,
                                  `updated_at` datetime(6) DEFAULT NULL,
                                  `address` varchar(100) NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKe9k3h1dyvc444fl2ib9k16jif` (`theater_play_id`),
                                  CONSTRAINT `FKe9k3h1dyvc444fl2ib9k16jif` FOREIGN KEY (`theater_play_id`) REFERENCES `theater_play` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;
drop table if exists reservation;
CREATE TABLE IF NOT EXISTS `reservation`
(
                               `reserved_seats` int NOT NULL,
                               `created_at` datetime(6) DEFAULT NULL,
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `scheduled_play_id` bigint NOT NULL,
                               `updated_at` datetime(6) DEFAULT NULL,
                               `email` varchar(50) NOT NULL,
                               `name` varchar(50) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK24ety9ug4w8m63i5qkhan6sqh` (`scheduled_play_id`),
                               CONSTRAINT `FK24ety9ug4w8m63i5qkhan6sqh` FOREIGN KEY (`scheduled_play_id`) REFERENCES `scheduled_play` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;
