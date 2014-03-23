<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date Feb 2012
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty psize}">
	<c:set var="psize" value="10" scope="page" />
	<c:choose>
		<c:when test="${page gt psize}">
			<c:set var="spage" value="${page-psize}" scope="page" />		
		</c:when>   
		<c:otherwise>
			<c:set var="spage" value="1" scope="page" />
		</c:otherwise>  
	</c:choose>
	
	<c:choose>
		<c:when test="${(total_page - page) gt psize}">
			<c:set var="epage" value="${page+psize}" scope="page" />		
		</c:when>   
		<c:otherwise>
			<c:set var="epage" value="${total_page}" scope="page" />
		</c:otherwise>  
	</c:choose>
</c:if>

<%-- Page Browser --%>
<div align="right" style="font-size:12px;">
Page: 
<c:if test="${spage gt 1}">
		<a href='<c:url value="${url}${param.pageUrl}=1"/>'>First</a> ...
</c:if>
	
<c:forEach var="pageNumber" begin="${spage}" end="${epage}">
	<c:choose>
		<c:when test="${pageNumber eq page}">
			<b>${pageNumber}</b>		
		</c:when>   
		<c:otherwise>
			<a href="<c:url value="${url}${param.pageUrl}=${pageNumber}"/>">${pageNumber}</a>
		</c:otherwise>  
	</c:choose>
</c:forEach>

<c:if test="${epage lt total_page}">
		... <a href='<c:url value="${url}${param.pageUrl}=${total_page}"/>'>Last</a>
</c:if>

(Total ${total}) | ${total_page} Pages
	<c:if test="${page gt 1}">
		<a href='<c:url value="${url}${param.pageUrl}=${page-1}"/>'>&lt; Pre</a>
	</c:if> 
	<c:if test="${page lt total_page}">
		<a href='<c:url value="${url}${param.pageUrl}=${page+1}"/>'>Next &gt;</a>
	</c:if> 
</div>
<%-- Page Browser --%>