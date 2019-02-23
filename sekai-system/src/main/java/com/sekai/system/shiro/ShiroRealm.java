package com.sekai.system.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sekai.system.model.Permission;
import com.sekai.system.model.Role;
import com.sekai.system.model.User;
import com.sekai.system.service.PermissionService;
import com.sekai.system.service.RoleService;
import com.sekai.system.service.UserService;
import com.sekai.system.utils.LoginContext;

public class ShiroRealm extends AuthorizingRealm{
    //private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private LoginContext loginContext;
    /**
     * 用户授权认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer userId=loginContext.getUser().getUserId();
        String userName = principalCollection.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles = null;
        if(userName.equals("root")) {
        	roles = roleService.getAllRoles();
        }else {
        	roles = roleService.getRolesByUserName(userName);
        }

    	Set<String> set = new HashSet<String>();
    	for(Role role : roles) {
    		set.add(role.getRoleCode());
    	}
    	simpleAuthorizationInfo.setRoles(set);
    	
    	List<Permission> permissions = null;
    	if(userName.equals("root")) {
    		permissions = permissionService.getAllPermissions();
        }else {
        	permissions = permissionService.getPermissionsByUserId(userId);
        }
    	Set<String> set2 = new HashSet<String>();
    	for(Permission permission : permissions) {
    		set2.add(permission.getPermissionCode());
    	}
    	simpleAuthorizationInfo.setStringPermissions(set2);
        return simpleAuthorizationInfo;
    }
    /**
     * 用户登陆认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = authenticationToken.getPrincipal().toString();
        User user=null;
        try{
            user = userService.getUserByUserName(userName);
        }catch(Exception err){
            System.out.println(err.getMessage());
        }
        if (user!=null) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPwd(), getName());
            return authenticationInfo;
        }

        return null;
    }
	/**
	 * 清除缓存
	 */
    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
        //System.out.println("clearCache()");
    }
    /**
     * 注销登录
     */
    public void logOut() {
    	clearCache();
        SecurityUtils.getSubject().logout();
        //System.out.println("logOut()");
    }
}