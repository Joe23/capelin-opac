<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Email Record</h1>

<form:form method="post" action="${url}mailFolder" commandName="mail_form" onsubmit="javascript:return validate('mail_form','email');">
	<div class="capelin-advanced-form">	
		<div class="row">
			<form:label path="email">Recipient Email:</form:label>
			<form:input path="email" size="30" />
		</div>
		
		<div class="row">
		<img src="Kaptcha.jpg">
		<form:label path="captcha">Captcha:</form:label>
		<form:input path="captcha" size="15" />
		</div>
		<div class="row">
			<table align="center">
				<tr><td><input class="center" type="submit" value="Send mail" /></td></tr>
			</table>
		</div>
	</div>
</form:form>
<br /><br /><br />
<div align="center">${error_message}</div>