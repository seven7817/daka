<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	执行成功！http://47.101.212.238:8080/daka/ 
	<% String Email = (String)request.getAttribute("Email"); %>
	<form action="recharge" method="post">
		<input type="hidden" name="Email" value= <%= Email %>> 
		<input type="submit" value="点击进行充值">
	</form>
</body>
</html>