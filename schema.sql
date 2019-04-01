-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema login_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `login_db` ;

-- -----------------------------------------------------
-- Schema login_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `login_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci; ;
-- -----------------------------------------------------
-- Schema menu
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `menu` ;

-- -----------------------------------------------------
-- Schema menu
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `menu` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `login_db` ;

-- -----------------------------------------------------
-- Table `login_db`.`LoginData`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `login_db`.`LoginData` ;

CREATE TABLE IF NOT EXISTS `login_db`.`LoginData` (
  `idUser` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `color` VARCHAR(45) NULL DEFAULT NULL,
  `colorSecundario` VARCHAR(45) NULL DEFAULT NULL,
  `nombre` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

USE `menu` ;

-- -----------------------------------------------------
-- Table `menu`.`Allergens`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`Allergens` ;

CREATE TABLE IF NOT EXISTS `menu`.`Allergens` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `menu`.`dishes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`dishes` ;

CREATE TABLE IF NOT EXISTS `menu`.`dishes` (
  `idDishes` INT(11) NOT NULL AUTO_INCREMENT,
  `carboHydrates` DOUBLE NULL DEFAULT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  `name` VARCHAR(4000) NULL DEFAULT NULL,
  `energy` DOUBLE NULL DEFAULT NULL,
  `fat` DOUBLE NULL DEFAULT NULL,
  `saturedFat` DOUBLE NULL DEFAULT NULL,
  `sugars` DOUBLE NULL DEFAULT NULL,
  `salt` DOUBLE NULL DEFAULT NULL,
  `weight` DOUBLE NULL DEFAULT NULL,
  `image` VARCHAR(4000) NULL DEFAULT NULL,
  `description` VARCHAR(4000) NULL DEFAULT NULL,
  `proteins` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`idDishes`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `menu`.`DiAl`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`DiAl` ;

CREATE TABLE IF NOT EXISTS `menu`.`DiAl` (
  `iddiAl` INT(11) NOT NULL AUTO_INCREMENT,
  `Allergens_id` INT(11) NOT NULL,
  `dishes_idDishes` INT(11) NOT NULL,
  PRIMARY KEY (`iddiAl`),
  INDEX `fk_DiAl_Allergens_idx` (`Allergens_id` ASC),
  INDEX `fk_DiAl_dishes1_idx` (`dishes_idDishes` ASC),
  CONSTRAINT `fk_DiAl_Allergens`
    FOREIGN KEY (`Allergens_id`)
    REFERENCES `menu`.`allergens` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DiAl_dishes1`
    FOREIGN KEY (`dishes_idDishes`)
    REFERENCES `menu`.`dishes` (`idDishes`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 97
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `menu`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`categories` ;

CREATE TABLE IF NOT EXISTS `menu`.`categories` (
  `idcategories` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(4000) NULL DEFAULT NULL,
  `image` VARCHAR(4000) NULL DEFAULT NULL,
  `name` VARCHAR(4000) NOT NULL,
  PRIMARY KEY (`idcategories`))
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `menu`.`DiCa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`DiCa` ;

CREATE TABLE IF NOT EXISTS `menu`.`DiCa` (
  `iddiCa` INT(11) NOT NULL AUTO_INCREMENT,
  `dishes_idDishes` INT(11) NOT NULL,
  `categories_idcategories` INT(11) NOT NULL,
  PRIMARY KEY (`iddiCa`),
  INDEX `fk_DiCa_dishes1_idx` (`dishes_idDishes` ASC),
  INDEX `fk_DiCa_categories1_idx` (`categories_idcategories` ASC),
  CONSTRAINT `fk_DiCa_categories1`
    FOREIGN KEY (`categories_idcategories`)
    REFERENCES `menu`.`categories` (`idcategories`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DiCa_dishes1`
    FOREIGN KEY (`dishes_idDishes`)
    REFERENCES `menu`.`dishes` (`idDishes`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

