<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>
	<h1>物件管理システム</h1>
	<% String error = (String) request.getAttribute("error");
       if (error != null && !error.isEmpty()) { %>
	<div class="error-message">
		<%= error %>
	</div>
	<% } %>
	<form action="/house_list/Login" method="post">
		アカウント名<input type="text" id="name" name="name"><br> 
		パスワード<input type="text" id="password" name="password"><br> 
		<input type="submit" value="ログイン">
	</form>

</body>
</html>
