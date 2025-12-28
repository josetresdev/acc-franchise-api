CREATE DATABASE IF NOT EXISTS acc_franchise CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'acc_user'@'%' IDENTIFIED BY 'acc_password';
GRANT ALL PRIVILEGES ON acc_franchise.* TO 'acc_user'@'%';
FLUSH PRIVILEGES;
