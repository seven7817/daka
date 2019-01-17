<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
%>
<title>Insert title here</title>
</head>
<body>
	<%
		String Email = (String) request.getAttribute("Email");
		String money = (String) request.getAttribute("money");
		String description = (String) request.getAttribute("description");
	%>
	执行成功！邮箱是：<%=Email%>，金额是：<%=money%>，描述是：<%=description%>
	<form action="recharge" method="post">
		<input type="hidden" name="Email" value=<%=Email%>> <input
			type="hidden" name="money" value=<%=money%>> <input
			type="hidden" name="description" value=<%=description%>> <input
			type="submit" value="点击进行充值">
	</form>
</body>
</html>