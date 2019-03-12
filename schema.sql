-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema menu
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema menu
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `menu` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
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
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `menu`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`categories` ;

CREATE TABLE IF NOT EXISTS `menu`.`categories` (
  `idcategories` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `image` VARCHAR(45) NULL DEFAULT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcategories`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `menu`.`dishes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`dishes` ;

CREATE TABLE IF NOT EXISTS `menu`.`dishes` (
  `idDishes` INT(11) NOT NULL AUTO_INCREMENT,
  `carboHydrates` DOUBLE NULL DEFAULT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `energy` DOUBLE NULL DEFAULT NULL,
  `fat` DOUBLE NULL DEFAULT NULL,
  `saturatedFat` DOUBLE NULL DEFAULT NULL,
  `sugars` DOUBLE NULL DEFAULT NULL,
  `salt` DOUBLE NULL DEFAULT NULL,
  `weight` DOUBLE NULL DEFAULT NULL,
  `image` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idDishes`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `menu`.`DiAl`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`DiAl` ;

CREATE TABLE IF NOT EXISTS `menu`.`DiAl` (
  `iddiAl` INT(11) NOT NULL AUTO_INCREMENT,
  `Allergens_id` INT(11) NOT NULL,
  `dishes_idDishes` INT(11) NOT NULL,
  PRIMARY KEY (`iddiAl`),
  INDEX `fk_DiAl_Allergens_idx` (`Allergens_id` ASC) VISIBLE,
  INDEX `fk_DiAl_dishes1_idx` (`dishes_idDishes` ASC) VISIBLE,
  CONSTRAINT `fk_DiAl_Allergens`
    FOREIGN KEY (`Allergens_id`)
    REFERENCES `menu`.`Allergens` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DiAl_dishes1`
    FOREIGN KEY (`dishes_idDishes`)
    REFERENCES `menu`.`dishes` (`idDishes`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `menu`.`DiCa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu`.`DiCa` ;

CREATE TABLE IF NOT EXISTS `menu`.`DiCa` (
  `iddiCa` INT(11) NOT NULL AUTO_INCREMENT,
  `dishes_idDishes` INT(11) NOT NULL,
  `categories_idcategories` INT(11) NOT NULL,
  PRIMARY KEY (`iddiCa`),
  INDEX `fk_DiCa_dishes1_idx` (`dishes_idDishes` ASC) VISIBLE,
  INDEX `fk_DiCa_categories1_idx` (`categories_idcategories` ASC) VISIBLE,
  CONSTRAINT `fk_DiCa_dishes1`
    FOREIGN KEY (`dishes_idDishes`)
    REFERENCES `menu`.`dishes` (`idDishes`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DiCa_categories1`
    FOREIGN KEY (`categories_idcategories`)
    REFERENCES `menu`.`categories` (`idcategories`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
