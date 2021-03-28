-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema askmedb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema askmedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `askmedb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `askmedb` ;

-- -----------------------------------------------------
-- Table `askmedb`.`admin_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`admin_user` (
  `admin_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`admin_id`, `user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`user` (
  `id` BIGINT NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `enabled` BIT(1) NOT NULL,
  `first_name` VARCHAR(255) NULL DEFAULT NULL,
  `last_name` VARCHAR(255) NULL DEFAULT NULL,
  `modify_date` DATETIME(6) NOT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `role` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`test` (
  `id` BIGINT NOT NULL,
  `create_date` DATETIME(6) NULL DEFAULT NULL,
  `modify_date` DATETIME(6) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `time` INT NOT NULL,
  `total_mark` DOUBLE NOT NULL,
  `creator_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKt1bg0we878igqj4xocxbxmltx` (`creator_id` ASC) VISIBLE,
  CONSTRAINT `FKt1bg0we878igqj4xocxbxmltx`
    FOREIGN KEY (`creator_id`)
    REFERENCES `askmedb`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`question` (
  `question_id` BIGINT NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `modify_date` DATETIME(6) NOT NULL,
  `points` DOUBLE NOT NULL,
  `text` VARCHAR(255) NULL DEFAULT NULL,
  `test_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  INDEX `FK8hejcpbbiq1qje11346akp3uj` (`test_id` ASC) VISIBLE,
  CONSTRAINT `FK8hejcpbbiq1qje11346akp3uj`
    FOREIGN KEY (`test_id`)
    REFERENCES `askmedb`.`test` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`answer` (
  `answer_id` BIGINT NOT NULL,
  `correct` BIT(1) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `modify_date` DATETIME(6) NOT NULL,
  `selected` TINYINT(1) NULL DEFAULT '0',
  `text` VARCHAR(255) NULL DEFAULT NULL,
  `question_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`answer_id`),
  INDEX `FK8frr4bcabmmeyyu60qt7iiblo` (`question_id` ASC) VISIBLE,
  CONSTRAINT `FK8frr4bcabmmeyyu60qt7iiblo`
    FOREIGN KEY (`question_id`)
    REFERENCES `askmedb`.`question` (`question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`answerseq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`answerseq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`confirmation_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`confirmation_token` (
  `token_id` BIGINT NOT NULL,
  `confirmation_token` VARCHAR(255) NULL DEFAULT NULL,
  `created_date` DATETIME(6) NULL DEFAULT NULL,
  `sent` BIT(1) NOT NULL,
  `used` BIT(1) NOT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`token_id`),
  INDEX `FKhjrtky9wbd6lbk7mu9tuddqgn` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKhjrtky9wbd6lbk7mu9tuddqgn`
    FOREIGN KEY (`user_id`)
    REFERENCES `askmedb`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`questionseq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`questionseq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`result` (
  `id` BIGINT NOT NULL,
  `create_date` DATETIME(6) NULL DEFAULT NULL,
  `examinee_id` BIGINT NULL DEFAULT NULL,
  `grade` DOUBLE NOT NULL,
  `passed` BIT(1) NOT NULL,
  `test_id` BIGINT NULL DEFAULT NULL,
  `test_name` VARCHAR(255) NULL DEFAULT NULL,
  `total_mark` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`resultseq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`resultseq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`test_access`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`test_access` (
  `test_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `access` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`test_id`, `user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`testseq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`testseq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`tokenseq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`tokenseq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`users_tests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`users_tests` (
  `user_tobe_tested_id` BIGINT NOT NULL,
  `test_id` BIGINT NOT NULL,
  INDEX `FKlepen1u8de0ne6eoh1mwxeh1i` (`test_id` ASC) VISIBLE,
  INDEX `FK4ecf0l9k4mro6xi8dbwku8m8x` (`user_tobe_tested_id` ASC) VISIBLE,
  CONSTRAINT `FK4ecf0l9k4mro6xi8dbwku8m8x`
    FOREIGN KEY (`user_tobe_tested_id`)
    REFERENCES `askmedb`.`user` (`id`),
  CONSTRAINT `FKlepen1u8de0ne6eoh1mwxeh1i`
    FOREIGN KEY (`test_id`)
    REFERENCES `askmedb`.`test` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `askmedb`.`userseq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `askmedb`.`userseq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


