package com.sekai.system.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sekai.system.model.User;
import com.sekai.system.service.MenuService;
import com.sekai.system.service.UserService;
import com.sekai.system.utils.EncryptUtils;
import com.sekai.system.utils.LoginContext;
import com.sekai.system.utils.ShiroRealm;
/*登录控制器*/
@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Resource
    private ShiroRealm shiroRealm;
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    @RequestMapping("/")
    public String welcome(){

        return "redirect:/login";
    }
    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpSession session, User user){
        if(request.getParameter("userName") == null){
            return "login";
        }

        String userName = request.getParameter("userName").trim();
        String pwd = request.getParameter("pwd").trim();
        if(userName.equals("")) {
            model.addAttribute("msg","用户名不允许为空!");
            return "login";
        }
    	/*
    	int count = userService.checkUserLogin(userName, password);
    	if(count == 0)
    	{
    		model.addAttribute("msg","用户名或密码不正确！");
    		return "login";
    	}*/

        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("userName",userName);
        String newPwd = new EncryptUtils().Encode(pwd);
        //System.out.println(userName+" "+pwd+" \n"+newPwd);
        UsernamePasswordToken usernamePasswordToken = new
                UsernamePasswordToken(userName,newPwd);
        String msg=null;
        try {
            subject.login(usernamePasswordToken);
            System.out.println(subject.getSession().getId());

            //subject.getSession().setAttribute("userId","");
        }catch(UnknownAccountException e){
            msg="用户"+userName+"不存在!";
        }catch(IncorrectCredentialsException e){
            msg="密码不正确!";
        }catch(LockedAccountException e){
            msg="用户已锁定，不能登陆!";
        }catch(AuthenticationException e){
            //其它情况
            msg="other:"+e.getMessage();
        }

        if(msg != null) {
            model.addAttribute("msg",msg);
            return "login";
        }
        user = userService.getUserByUserName(userName);
        if(user.getForbid() == 1) {
            model.addAttribute("msg","用户："+userName+"已被禁用！");
            return "login";
        }
        String mylog = "登录："+user.getUserName();
        logger.info(mylog);
        System.out.println(mylog);

        //生成会话状态
        new LoginContext().createContext(session, user);
        shiroRealm.clearCache();
        return "redirect:/frame";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("userId", null);
        session.setAttribute("userName", null);
        session.setAttribute("nickName", null);
        session.setAttribute("loginDate", null);
        session.setAttribute("isAdmin", null);
        shiroRealm.logOut();
        return "redirect:/login";
    }
    @RequestMapping("/frame")
    public String frame(Model model,HttpServletRequest request,HttpSession session){
        if(session.getAttribute("userName") == null){
            return "redirect:/login";
        }
        String systemMenu = menuService.getSystemMenu(session);
        model.addAttribute("systemMenu",systemMenu);
        return "/system/frame/index";
    }
    @RequestMapping("/system/frame/defaultPage")
    public String defaultPage(){

        return "/system/frame/default_page";
    }
    @RequestMapping("/scan")
    public String scan(){

        return "/scan";
    }
}