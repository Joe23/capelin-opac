<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="capelin" uri="capelin-opac.org" %>

<c:if test="${empty search_result && !empty message}">
<br/><br /><center><b>No Result Found.</b></center>
</c:if>

<c:if test="${! empty search_result}">
	<c:forEach items="${search_result}" var="data">	
		<div class="capelin-block">
		<a class="title" href="<c:url value="${url}searchByLink&term=${search_form.term}&value=${capelin:clink(data)}"/>">${data}</a> <br>
		</div>
	</c:forEach>
</c:if>