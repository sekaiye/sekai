if object_id('sk_system_role') is null
begin
  CREATE TABLE sys_role (
    role_id INT identity(1,1),
    role_code NVARCHAR(50) NOT NULL,
    role_name NVARCHAR(50) NOT NULL,
    PRIMARY KEY (role_id)
  );
  alter table sys_role ADD CONSTRAINT sys_role_uq_role_code UNIQUE (role_code)
end
GO


