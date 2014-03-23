<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Oops! Error Occur ...</h1>

<h2>${error_message}</h2>

<c:if test="${ ! empty error_exception}" >
	<p>Technical message: ${error_exception}</p>
</c:if>

<br>
<h3>Please <a href="javascript:history.go(-1)">Go Back</a> and try again!</h3>