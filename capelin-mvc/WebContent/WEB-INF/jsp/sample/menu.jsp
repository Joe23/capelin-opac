<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input type=hidden id="Capelin-Path" value="sample" />
<div id="caplin-content-menu-top"></div>
	<a href="<c:url value="sample.cat"/>" title="Basic Search">Basic Search</a>
	<a href="<c:url value="sample.cat?do=toAdvancedSearch"/>" title="Advanced Search">Advanced Search</a>
	<a href="<c:url value="sample.cat?do=toSimilar"/>" title="Similar">Similar</a>
	<a href="<c:url value="sample.cat?do=viewFolder"/>" title="View Folder">View Kept Folder</a>
	<c:if test="${empty sessionScope.sample}">
		<a href="<c:url value="sample.cat?do=toLogin"/>" title="Login">Login</a>
	</c:if>
	<c:if test="${not empty sessionScope.sample}">
		<a href="<c:url value="sample.cat?do=toNewData"/>" title="Add Record">Add Record</a>
		<a href="<c:url value="sample.cat?do=toList"/>" title="List Catalog">List Record</a>
		<a href="<c:url value="sample.cat?do=logout"/>" title="Logout">Logout [${sessionScope.sample}]</a>
	</c:if>
<div id="caplin-content-menu-button"></div>