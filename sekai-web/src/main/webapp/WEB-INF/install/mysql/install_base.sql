DROP PROCEDURE IF EXISTS p_install_base;

DELIMITER $$
CREATE PROCEDURE p_install_base()
BEGIN

CREATE TABLE IF NOT EXISTS base_material (
  mat_id INT(11) NOT NULL AUTO_INCREMENT,
  mat_code VARCHAR(20) NOT NULL,
  mat_name VARCHAR(50) NOT NULL,
  spec VARCHAR(100),
  unit VARCHAR(20),
  mat_class INT(4),
  forbid INT(1) DEFAULT 0,
  mat_define1 VARCHAR(50),
  mat_define2 VARCHAR(50),
  mat_define3 VARCHAR(50),
  mat_define4 VARCHAR(50),
  mat_define5 VARCHAR(50),
  mat_define6 VARCHAR(50),
  mat_define7 VARCHAR(50),
  mat_define8 VARCHAR(50),
  mat_define9 VARCHAR(50),
  mat_define10 VARCHAR(50),
  PRIMARY KEY (mat_id),
  UNIQUE KEY base_material_uq_mat_code (mat_code)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS base_warehouse (
  wh_id INT(11) NOT NULL AUTO_INCREMENT,
  wh_code VARCHAR(20) NOT NULL,
  wh_name VARCHAR(50) NOT NULL,
  is_ctrl_stock INT(1),
  forbid INT(1) DEFAULT 0,
  PRIMARY KEY (wh_id),
  UNIQUE KEY base_warehouse_uq_wh_code (wh_code)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS base_department (
  dept_id INT(11) NOT NULL AUTO_INCREMENT,
  dept_code VARCHAR(20) NOT NULL,
  dept_name VARCHAR(50) NOT NULL,
  parent_id INT(11),
  forbid INT(1) DEFAULT 0,
  PRIMARY KEY (dept_id),
  UNIQUE KEY base_department_uq_dept_code (dept_code)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*菜单*/
IF(SELECT COUNT(*) FROM sys_menu WHERE menu_code='material')=0
THEN
	INSERT INTO sys_menu(parent_code,sort,menu_code,menu_name,permission_code,url)
	VALUES('base',1,'material','物料','material_view','base/material/list'), 
		('base',2,'warehouse','仓库','warehouse_view','base/warehouse/list'),
		('base',3,'department','部门','department_view','base/department/list');
END IF;

/*权限*/
IF(SELECT COUNT(*) FROM sys_permission WHERE permission_code='material')=0
THEN
	
	INSERT INTO sys_permission(parent_code,permission_code,permission_name)
	VALUES ('base','material','物料');

	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('material','material_view','查看'), 
		('material','material_add','新增'), 
		('material','material_modify','修改'),
		('material','material_delete','删除'),
		('material','material_import','导入'), 
		('material','material_export','导出');
	
	INSERT INTO sys_permission(parent_code,permission_code,permission_name) 
	VALUES ('base','warehouse','仓库');

	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('warehouse','warehouse_view','查看'), 
		('warehouse','warehouse_add','新增'),
		('warehouse','warehouse_modify','修改'),
		('warehouse','warehouse_delete','删除'),
		('warehouse','warehouse_import','导入'),
		('warehouse','warehouse_export','导出');
		
	INSERT INTO sys_permission(parent_code,permission_code,permission_name)
	VALUES ('base','department','部门');

	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES 
		('department','department_view','查看'), 
		('department','department_add','新增'),
		('department','department_modify','修改'),
		('department','department_delete','删除'),
		('department','department_import','导入'),
		('department','department_export','导出');
END IF;

END$$
DELIMITER ;

#执行存储过程
CALL p_install_base;
