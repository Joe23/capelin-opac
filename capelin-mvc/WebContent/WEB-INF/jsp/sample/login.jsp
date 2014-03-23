<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Admin Login</h1>
<form:form method="post" action="${url}login" commandName="loginForm">	
	<div class="capelin-simple-form">		
		<label>Username</label>
		<form:input path="username" size="15" />
	
		<label>Password</label>
		<form:password  path="password" size="15" />
		
		<label>Captcha</label>
		<form:input path="captcha" size="15" />
		
		<input type="submit" name="submit" value="Submit">
	</div>	
</form:form>
<div align="center"><img src="Kaptcha.jpg"></div>
<br /><br /><br />
<div align="center">${error_message}</div>