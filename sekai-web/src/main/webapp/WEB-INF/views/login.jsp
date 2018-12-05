<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>管理系统</title> 
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="js/jquery.min.js"></script>
<link href="css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h1>登录Login<!--<sup>v1.0</sup>--></h1>
<div class="login" style="margin-top:50px;">
    <!--
    <div class="header">
        <div class="switch" id="switch">
            <a class="switch_btn_focus" id="switch_qlogin" href="javascript:void(0);" tabindex="7">登录</a>
        </div>
    </div>-->
  
    
    <div class="web_qr_login" id="web_qr_login" style="display: block; height: 235px;">    

            <!--登录-->
            <div class="web_login" id="web_login">
               
               
               <div class="login-box">
    
            
			<div class="login_form">
				<div id="return_msg" style="color:red;text-align:center;"></div>
				<form action="" name="login_form" accept-charset="utf-8" id="login_form"
                      method="post" action="login" target="_top">
                <div class="uinArea" id="uinArea">
                <label class="input-tips" for="u">帐号：</label>
                <div class="inputOuter" id="uArea">
                    
                    <input type="text" id="userName" name="userName" value="${user.userName}" class="inputstyle"/>
                </div>
                </div>
                <div class="pwdArea" id="pwdArea">
               <label class="input-tips" for="p">密码：</label> 
               <div class="inputOuter" id="pArea">
                    
                    <input type="password" id="pwd" name="pwd" value="${user.pwd}"  class="inputstyle"/>
                </div>
                </div>
               
                <div style="padding-left:50px;margin-top:20px;">
                	<input type="submit" value="登 录" style="width:150px;" class="button_blue"/></div>
                
              </form>
           </div>
           
            	</div>
               
            </div>
            <!--登录end-->
  </div>

</div>

<!--<div class="jianyi">推荐使用Chrome内核、火狐、ie8以上浏览器访问本系统</div>-->
<script type='text/javascript'>
	window.onload=function(){
		var msg = "${msg}";
		if(msg != ""){
			$("#return_msg").html(msg);
		}
		$("#userName").val("root");
		$("#pwd").val("123");
        /*
		document.getElementById('login_form').onsubmit=function (ev) {
		    return true;
        }*/
	};
	var user_agent=navigator.userAgent
    if(user_agent.indexOf("MSIE 6.0")>0 || user_agent.indexOf("MSIE 7.0")>0){  
      alert("浏览器版本太低！\n推荐使用Chrome内核、火狐、IE8以上浏览器访问本系统");   
    }

</script>

</body>
</html>