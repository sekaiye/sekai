<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>首页</title>
	<link rel="stylesheet" href="ui/frame/css/frame.css">
	<link rel="stylesheet" type="text/css" href="ui/frame/skin/molv/skin.css" id="layout-skin"/>
    <link rel="stylesheet" href="js/bootstrap/css/bootstrap.min.css"/>
    <!--[if lt IE 9]>
	<script src="js/html5shiv.min.js"></script>
	<script src="js/respond.min.js"></script>
	<![endif]-->
  </head>
  
  <body>
	<div class="layout-admin">
		<header class="layout-header">
			<span class="header-logo">系统框架</span> 
			<a class="header-menu-btn" href="javascript:;"><i class="icon-font">&#xe600;</i></a>
			<ul class="header-bar">
				<li class="header-bar-role"><a href="javascript:;">${sessionScope.nickName}</a></li>
				<li class="header-bar-nav">
					<a href="javascript:;">${sessionScope.userName}<i class="icon-font" style="margin-left:5px;">&#xe60c;</i></a>
					isAdmin:${sessionScope.isAdmin}
					<ul class="header-dropdown-menu">
						<li><a href="javascript:openTab('修改密码','system/user/changeMyPwd.do');">修改密码</a></li>
						<li><a href="logout.do">退出</a></li>
					</ul>
				</li>
				<li class="header-bar-nav"> 
					<a href="javascript:;" title="换肤"><i class="icon-font">&#xe608;</i></a>
					<ul class="header-dropdown-menu right dropdown-skin">
						<li><a href="javascript:;" data-val="qingxin" title="清新">清新</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="molv" title="墨绿">墨绿</a></li>
						
					</ul>
				</li>
			</ul>
		</header>
		<aside class="layout-side">
			<ul class="side-menu">
			  
			</ul>
		</aside>
		
		<div class="layout-side-arrow"><div class="layout-side-arrow-icon"><i class="icon-font">&#xe60d;</i></div></div>
		
		<section class="layout-main">
			<div class="layout-main-tab">
				<button class="tab-btn btn-left"><i class="icon-font">&#xe60e;</i></button>
                <nav class="tab-nav">
                    <div class="tab-nav-content">
                        <a href="javascript:;" class="content-tab active" data-id="home.html">首页</a>
                    </div>
                </nav>
                <button class="tab-btn btn-right"><i class="icon-font">&#xe60f;</i></button>
			</div>
			<div class="layout-main-body">
				<iframe class="body-iframe" name="iframe0" width="100%" height="99%" src="system/frame/defaultPage" frameborder="0" data-id="home.html" seamless></iframe>
			</div>
		</section>
		<!--<div class="layout-footer">@2017 www.sekai.net</div>-->
	</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="ui/frame/js/frame.js"></script>
	<script type="text/javascript" src="js/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/bootbox.min.js"></script>
	<script type="text/javascript" src="js/layer/layer.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript">
	bootbox.setDefaults("locale","zh_CN");
	/*
	  初始化加载
	*/
	$(function(){
		/*获取皮肤*/
		//getSkinByCookie();

		/*菜单json*/
		/*
		var menu = [{"id":"1","name":"主菜单","parentId":"0","url":"","icon":"","order":"1","isHeader":"1","childMenus":[
						{"id":"3","name":"系统管理","parentId":"1","url":"","icon":"&#xe610;","order":"1","isHeader":"0","childMenus":[
							{"id":"31","name":"权限管理","parentId":"3","url":"","icon":"&#xe610;","order":"1","isHeader":"0","childMenus":[
								{"id":"311","name":"用户","parentId":"31","url":"system/user/list.do","icon":"&#xe602;","order":"1","isHeader":"0","childMenus":""},
								{"id":"312","name":"角色","parentId":"31","url":"system/role/list.do","icon":"","order":"1","isHeader":"0","childMenus":""},
								{"id":"313","name":"用户授权","parentId":"31","url":"system/user_role/list.do","icon":"","order":"1","isHeader":"0","childMenus":""}
							]},
							{"id":"4","name":"系统参数","parentId":"3","url":"system/config/config.do","icon":"","order":"1","isHeader":"0","childMenus":""}
						]},
						{"id":"6","name":"基础资料","parentId":"1","url":"","icon":"&#xe610;","order":"1","isHeader":"0","childMenus":[
							{"id":"7","name":"存货","parentId":"6","url":"base/Material/list.do","icon":"","order":"1","isHeader":"0","childMenus":""},
							{"id":"8","name":"未付款","parentId":"6","url":"home4.html","icon":"","order":"1","isHeader":"0","childMenus":""}
						]}
					]}
					];*/
		var menu = [{"id":"Main","name":"主菜单","parentId":"0","url":"","icon":"","order":"1","isHeader":"1","childMenus":[
			${systemMenu}	
		  ]}
		];
		initMenu(menu,$(".side-menu"));
		$(".side-menu > li").addClass("menu-item");
		
		/*获取菜单icon随机色*/
		//getMathColor();
	}); 
	</script>
	
  </body>
</html>