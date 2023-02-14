CREATE TABLE `customer` (
  `id` varchar(36) DEFAULT (UUID()) PRIMARY KEY,
  `first_name` varchar(128) DEFAULT NULL,
  `last_name` varchar(128) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL
)






