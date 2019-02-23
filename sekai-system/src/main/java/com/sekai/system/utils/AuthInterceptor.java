package com.sekai.system.utils;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private LoginContext loginContext;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Object user = loginContext.getUser().getUserId();
    	if (user == null) {
    		if(request.getHeader("X-Requested-With") != null) {
	    		//ajax类型的登录提示
	            response.setCharacterEncoding("utf-8");
	            response.setContentType("text/json;charset=UTF-8");
	            
	            String json = new ObjectMapper().writeValueAsString(new JsonResult(0, "会话超时，请重新登录！"));
	            OutputStream out = response.getOutputStream();
	            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
	            //pw.print("{\"success\":0,\"data\":\"LoginFail\"}");
	            pw.print(json);
	            pw.flush();
	            pw.close();
    		}else {
	    		//页面跳转
	    		//System.out.println("用户登录超时!");
    			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	    		response.sendRedirect(basePath+"login.do");
    		}
    		return false;
    	}
        return true;   
     }
}
