-- // First migration.
-- Migration SQL that makes the change goes here.

ALTER TABLE t_ebay_process_business ADD `SALE_GROUP_ID` int(20) DEFAULT NULL COMMENT '销售组ID';
