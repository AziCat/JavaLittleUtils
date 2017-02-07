<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
.mySelf{color: blue}
</style>
<script type="text/javascript" src="js/sockjs.min.js"></script>
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script>
//$(function(){
	var sock;
	var isLogin = false;
	function send(){
		var sendContent = $("#sendContent").val();
		if(!isLogin){
			alert("请先登录");
			return;
		}
		if(null!=sendContent&&""!=sendContent){
			sock.send(sendContent);
			$("#sendContent").val("");
		}
	}
	function login(){
		var userName = $("#userName").val();
		if(null==userName||""==userName){
			alert("请输入用户名！");
			return;
		}
		if(isLogin){
			alert("已在线，请不要重复登录！");
			return;
		}
		if('WebSocket' in window){
			sock = new WebSocket(encodeURI('ws://localhost:8080/websocket/ws?userName='+userName));
		}else{
			sock = new SockJS(encodeURI('http://localhost:8080/websocket/sockjs/ws?userName='+userName));
		}
		 sock.onopen = function() {
		     isLogin = true;
		     $("#status").html("在线");
		     $("#userName").attr("disabled",true);
		 };
		 sock.onmessage = function(e) {
		     var data = e.data;
		     //获取标志
		     var flag = data.substring(0,data.indexOf(":"));
		     //获取内容
		     var content = data.substring(data.indexOf(":")+1,data.length);
		     if("LOGIN"==flag){
		    	 alert(content);
		     }else if("MSG"==flag){
		    	 var user = content.substring(0,content.indexOf(":"));
		    	 var userName = $("#userName").val();
		    	 var liStr=null;
		    	 if(user==userName){
		    		 liStr = "<li class=\"mySelf\">"+content+"</li>";
		    	 }else{
		    		 liStr = "<li class=\"other\">"+content+"</li>";
		    	 }
		    	 $("#content").append(liStr);
		     }
		     
		 };
		 sock.onclose = function() {
			 isLogin=false;
		     $("#status").html("离线");
		     $("#userName").attr("disabled",false);
		 };
	}
	function logout(){
		if(isLogin){
			sock.close();
			isLogin=false;
		    $("#status").html("离线");
		    $("#userName").attr("disabled",false);
		}else{
			alert("目前没有在线！");
		}
	}
	function sendByEnter(e){
		if(e.keyCode==13){
			 send();
		}
	}
	 //sock.close();	
	//$("#loginBt").click(login());
//})
</script>
<title>聊天室</title>
</head>
<body>
<div>请输入用户名：<input type="text" id="userName"/><button id="loginBt" onclick="login()">登录</button>
<button onclick="logout()">登出</button>
</div>
<div>状态：<span id="status">离线</span><br>
</div>
<div style="background-color: #EEE;width: 50%;height: 500px;OVERFLOW-Y: auto; " >
<ul id="content">

</ul>
</div>
<textarea id="sendContent" rows="" cols="30" onkeydown="sendByEnter(event)"></textarea><button id="send" onclick="send()">send</button>
</body>
</html>