package com.sekai.system.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 */
public class ShiroUtil {


    /**
     *
     * <p>description: 获取ActiveUser并保存至session中一份</p>
     * @return
     * @date 2016年8月15日 下午3:37:23
     * @author MrDuan
     */
    /*
    public static ActiveUser getActiveUser(){
        //从shiro的session中取出activeUser
        Subject subject = SecurityUtils.getSubject();
        //取出身份信息
        Object activeUser = subject.getPrincipal();
        if(activeUser!=null){
            Session session = subject.getSession();
            ActiveUser user = (ActiveUser) session.getAttribute("user");
            if(user==null){
                session.setAttribute("user", activeUser);
            }
            return activeUser;
        }else{
            return null;
        }
    }
    */
    /**
     * 根据sessionid 获取用户信息
     * @param sessionID
     * @param request
     * @param response
     * @return
     */
    public String getActiveUser(String sessionID,HttpServletRequest request,HttpServletResponse response) throws Exception{
        boolean status = false;
        SessionKey key = new WebSessionKey(sessionID,request,response);
        Session se = SecurityUtils.getSecurityManager().getSession(key);
        Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        //org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to com.hncxhd.bywl.entity.manual.UserInfo
        SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
        //ActiveUser activeUser = (ActiveUser)coll.getPrimaryPrincipal();
        String activeUser = coll.getPrimaryPrincipal().toString();
        if(coll.getPrimaryPrincipal()!=null){
            String userName = coll.getPrimaryPrincipal().toString();
            return userName;
        }else{
            return null;
        }

    }


}