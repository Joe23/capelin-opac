<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${! empty search_result}">

<c:if test="${empty page}">
	<c:set var="page" value="1" />
</c:if>


<%-- Page Browser --%>
<c:import url="../jsp/common/_pageBrowse.jsp">
	<c:param name="pageUrl" value="listCatalogByPage&page" />
</c:import>
<%-- Page Browser --%>

<div class="items">

<c:forEach items="${search_result}" var="data">	
	<div class="capelin-block">
	<b>${data.documentType}:</b>
	<a href="<c:url value="${url}viewData&id=${data.id}"/>">${data.title}</a> <br />
	<b>Subject:</b>	${data.subject} <br />
	<b>Author:</b> ${data.author}
	</div>
</c:forEach>
</div>


<%-- Page Browser --%>
<c:import url="../jsp/common/_pageBrowse.jsp">
	<c:param name="pageUrl" value="listCatalogByPage&page" />
</c:import>
<%-- Page Browser --%>


</c:if>