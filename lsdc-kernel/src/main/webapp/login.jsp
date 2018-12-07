<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>登录</title>
</head>
<body>

<form action="/lsdc/login/login" method="post">
    登录名：<input type="text" name="username"/><br/>
      密码：<input type="password" name="password"/>
    <input type="submit" value="登录"/>
</form>
</body>
