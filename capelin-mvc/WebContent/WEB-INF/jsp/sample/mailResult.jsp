<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
<c:when test="${mail_result}">
	<h1>Your Email has been sent</h1>
	<p> ${mail_email} will receive the following email</p>
	<pre>${mail_body}</pre>
</c:when>
<c:otherwise>
	<h1>${mail_body}</h1>
</c:otherwise>
</c:choose>



<table align="center">
	<tr>
		<td><input type="button" value="View Folder" onclick="javascript:window.location='<c:url value="${url}viewFolder"/>';" /></td>
		<td><input type="button" value="Back to Search Result" onclick="javascript:window.location='<c:url value="${url}searchBrowse"/>';" /></td>				
	</tr>
</table>
