DROP PROCEDURE IF EXISTS p_install_extend;

DELIMITER $$
CREATE PROCEDURE p_install_extend()
BEGIN

CREATE TABLE IF NOT EXISTS st_inout (
  id INT(11) NOT NULL AUTO_INCREMENT,
  bill_num VARCHAR(30) NOT NULL,
  bill_date DATE,
  wh_id INT(11),
  create_user_id INT(11),
  modified_user_id INT(11),
  create_date DATETIME,
  modified_date DATETIME,
  PRIMARY KEY (id),
  UNIQUE KEY st_inout_uk(bill_num)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS st_inouts (
  ids INT(11) NOT NULL AUTO_INCREMENT,
  id INT(11) NOT NULL,
  sort INT(4),
  mat_id INT(11),
  qty DECIMAL(11,6),
  price DECIMAL(11,6),
  taxprice DECIMAL(11,6),
  amount DECIMAL(11,6),
  taxamount DECIMAL(11,6),
  tax DECIMAL(11,6),
  remark VARCHAR(60),
  PRIMARY KEY (ids)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*菜单*/
IF(SELECT COUNT(*) FROM sys_menu WHERE menu_code='purchase_in')=0
THEN
	INSERT INTO sys_menu(parent_code,sort,menu_code,menu_name,permission_code,url)
	SELECT 'stock',1,'purchase_in','采购入库单','purchase_in_view','stock/inout/list?module=purchaseIn';
END IF;

/*权限*/
IF(SELECT COUNT(*) FROM sys_permission WHERE permission_code='purchase_in')=0
THEN
	INSERT INTO sys_permission(parent_code,permission_code,permission_name)
	VALUES('stock','purchase_in','采购入库单');

	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('purchase_in','purchase_in_view','查看'), 
		('purchase_in','purchase_in_add','新增'), 
		('purchase_in','purchase_in_modify','修改'), 
		('purchase_in','purchase_in_delete','删除'),
		('purchase_in','purchase_in_verify','审核'),
		('purchase_in','purchase_in_unverify','反审核'),
		('purchase_in','purchase_in_print','打印'), 
		('purchase_in','purchase_in_export','导出');
END IF;
END$$
DELIMITER ;

#执行存储过程
CALL p_install_extend;
