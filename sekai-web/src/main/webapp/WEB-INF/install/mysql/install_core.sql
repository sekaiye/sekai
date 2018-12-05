DROP PROCEDURE IF EXISTS p_install_core;

DELIMITER $$
CREATE PROCEDURE p_install_core()
BEGIN

CREATE TABLE IF NOT EXISTS sys_role (
  role_id INT(11) NOT NULL AUTO_INCREMENT,
  role_code VARCHAR(50) NULL NULL,
  role_name VARCHAR(50) NULL NULL,
  PRIMARY KEY (role_id),
  UNIQUE KEY sys_role_uk(role_code)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS sys_user (
  user_id INT(11) NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(45) NOT NULL,
  nick_name VARCHAR(45) NOT NULL,
  pwd VARCHAR(128) NOT NULL,
  forbid INT(1) NOT NULL,
  email VARCHAR(45),
  cellphone VARCHAR(45),
  PRIMARY KEY (user_id),
  UNIQUE KEY sys_user_uk(user_name)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS sys_user_role (
  ur_id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  role_id INT(11) NOT NULL,
  PRIMARY KEY (ur_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS sys_permission (
  permission_id INT(11) NOT NULL AUTO_INCREMENT,
  permission_code VARCHAR(50) NOT NULL,
  permission_name VARCHAR(30) NOT NULL,
  parent_code VARCHAR(50),
  PRIMARY KEY (permission_id),
  UNIQUE KEY sys_permission_uk(permission_code)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS sys_menu (
  menu_id INT(4) NOT NULL AUTO_INCREMENT,
  menu_code VARCHAR(50) NOT NULL,
  menu_name VARCHAR(50) NOT NULL,
  sort INT(4) NOT NULL DEFAULT 0,
  parent_code VARCHAR(50) NOT NULL,
  permission_code VARCHAR(50),
  url VARCHAR(128),
  PRIMARY KEY (menu_id),
  UNIQUE KEY menu_code_uk(menu_code)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS sys_role_permission (
  rp_id INT(11) NOT NULL AUTO_INCREMENT,
  role_id INT(11) NOT NULL,
  permission_id INT(11) NOT NULL,
  PRIMARY KEY (rp_id),
  UNIQUE KEY sys_role_permission_uk (role_id,permission_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS sys_config (
  id INT(11) NOT NULL AUTO_INCREMENT,
  cf_code VARCHAR(50) NOT NULL,
  cf_name VARCHAR(50) NOT NULL,
  cf_value VARCHAR(90) NOT NULL,
  is_system INT(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY sys_config_uk(cf_code)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

IF(SELECT COUNT(*) FROM sys_user WHERE user_name='root')=0
THEN
	INSERT INTO sys_user(user_name,nick_name,forbid,pwd)
	SELECT 'root','系统管理员',0,'202cb962ac59075b964b07152d234b70';
	
	INSERT INTO sys_config (cf_code,cf_name,
	  cf_value,is_system)
	VALUES('listPageSize','列表每页显示行数','50',1),
	('fileUploadPath','文件上传路径','d:/upload',1);
END IF;
/*权限*/
IF(SELECT COUNT(*) FROM sys_permission WHERE permission_code='system')=0
THEN
	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('root','system','系统管理'),('root','base','基础信息'),('root','stock','库存管理');
	
	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('system','user','用户'),('system','role','角色'),
		('system','user_role','分配角色'),('system','config','系统参数');

	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('user','user_view','查看'),('user','user_add','新增'),
		('user','user_modify','修改'),('user','user_delete','删除'),
		('user','user_export','导出');

	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('role','role_view','查看'),('role','role_add','新增'),
		('role','role_modify','修改'),('role','role_delete','删除'),
		('role','role_export','导出');

	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('user_role','user_role_view','查看'),('user_role','user_role_add','新增'),
		('user_role','user_role_delete','删除');

	INSERT INTO sys_permission(parent_code,permission_code,permission_name) VALUES
		('config','config_view','查看参数'),('config','config_modify','修改参数');
	
END IF;

	/*生成系统菜单*/
IF(SELECT COUNT(*) FROM sys_menu WHERE menu_code='system')=0
THEN
	INSERT INTO sys_menu(parent_code,sort,menu_code,menu_name,permission_code,url) VALUES
		('root',10,'system','系统管理','system',NULL),
		('root',20,'base','基础信息','base',NULL),
		('root',30,'stock','库存管理','stock',NULL);

	INSERT INTO sys_menu(parent_code,sort,menu_code,menu_name,permission_code,url) VALUES
		('system',1,'user','用户','user_view','system/user/list'),
		('system',2,'role','角色','role_view','system/role/list'),
		('system',3,'user_role','分配角色','user_role_view','system/userRole/list'),
		('system',5,'config','系统参数','config_view','system/config/list');
END IF;

END$$
DELIMITER ;

#执行存储过程
CALL p_install_core;
