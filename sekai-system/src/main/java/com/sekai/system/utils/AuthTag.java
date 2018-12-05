package com.sekai.system.utils;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import com.sekai.system.service.RolePermissionService;
//<%@ taglib uri="/sekai/tags" prefix="sk" %>
public class AuthTag extends RequestContextAwareTag{
	@Autowired
	private RolePermissionService rolePermissionService;
	private static final long serialVersionUID = -3142798273823463575L;
	private String module;
	private String code;
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@SuppressWarnings("static-access")
	@Override
	public int doStartTagInternal() throws JspException {
		
		rolePermissionService = this.getRequestContext().getWebApplicationContext().getBean(RolePermissionService.class);
		HttpSession session = pageContext.getSession();
		boolean hasPermission;
		hasPermission= rolePermissionService.checkLoginUserHasPermission(session, getCode());
		if(hasPermission) {
			return super.EVAL_BODY_INCLUDE;
		}
		return super.SKIP_BODY;
	}
	
}
