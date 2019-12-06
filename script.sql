CREATE DATABASE IF NOT EXISTS `attendence_Managent_system`;

USE  `attendence_Managent_system`;


DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`(id INT AUTO_INCREMENT,
                     first_name VARCHAR(50) NOT NULL,
                     last_name VARCHAR(50) NOT NULL,
                     email VARCHAR(100) NOT NULL,
                     password VARCHAR(100) NOT NULL,
                     PRIMARY KEY(id)
);


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`(id INT AUTO_INCREMENT,
                     first_name VARCHAR(50) NOT NULL,
                     last_name VARCHAR(50) NOT NULL,
                     gender ENUM('male','female','other'),
                     email VARCHAR(100) NOT NULL,
                     password VARCHAR(100) NOT NULL,
                     image_url VARCHAR(500),
                     PRIMARY KEY(id)
);

DROP TABLE IF EXISTS `attendence`;
CREATE TABLE `attendence`(id INT PRIMARY KEY AUTO_INCREMENT,
                           attendence_date DATE,
                            user_id INT,
                          FOREIGN KEY(user_id) REFERENCES users(id)
                         );

