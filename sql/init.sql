-- ===================== Create database =====================
-- Creates the 'acc_franchise' database if it does not already exist.
-- Uses utf8mb4 charset for full Unicode support and proper collation.
CREATE DATABASE IF NOT EXISTS acc_franchise 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- ===================== Create user =====================
-- Creates a new MySQL user 'acc_user' with password 'acc_password' if it does not already exist.
CREATE USER IF NOT EXISTS 'acc_user'@'%' IDENTIFIED BY 'acc_password';

-- ===================== Grant privileges =====================
-- Grants all privileges on the 'acc_franchise' database to the newly created user.
GRANT ALL PRIVILEGES ON acc_franchise.* TO 'acc_user'@'%';

-- ===================== Apply changes =====================
-- Flushes privileges to ensure that the new permissions take effect immediately.
FLUSH PRIVILEGES;
