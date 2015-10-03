USE sigdb;

ALTER TABLE `sigdb`.`tb_user` 
CHANGE COLUMN `first_name` `first_name` VARCHAR(20) NOT NULL COMMENT '' AFTER `userid`,
CHANGE COLUMN `last_name` `last_name` VARCHAR(20) NOT NULL COMMENT '' AFTER `first_name`,
CHANGE COLUMN `password` `password` VARCHAR(8) NOT NULL COMMENT '' AFTER `last_name`,
CHANGE COLUMN `cash_bal` `cash_bal` DECIMAL(12,2) NULL COMMENT '' ,
CHANGE COLUMN `share_val` `share_val` DECIMAL(12,4) NULL COMMENT '' ,
CHANGE COLUMN `annual_ret` `annual_ret` DECIMAL(12,4) NULL COMMENT '' ,
CHANGE COLUMN `ytd_ret` `ytd_ret` DECIMAL(12,4) NULL COMMENT '' ,
CHANGE COLUMN `totalval_0101` `totalval_0101` DECIMAL(12,4) NULL COMMENT '' ,
CHANGE COLUMN `lastupdate` `lastupdate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '' ,
ADD COLUMN `initialBalance` DECIMAL(12,5) NULL COMMENT '' AFTER `lastupdate`,
ADD COLUMN `email` VARCHAR(100) NOT NULL COMMENT '' AFTER `initialBalance`;