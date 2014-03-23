<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="capelin" uri="capelin-opac.org" %>

<h1>View Kept Folder</h1>

<c:if test="${! empty folder_list}">
<script src='<c:url value="/ext/lytebox/lytebox.js"/>'></script>
<link rel="stylesheet" href='<c:url value="/ext/lytebox/lytebox.css"/>' type="text/css" media="screen" />

<div class="row">
	<form method="get" action="#" class="right">
		<select id="sort_by" onchange="sortFolder('sort_by','${url}viewFolder')">
			<option >Sort By</option>
			<option value="documentType">Document Type</option>
			<option value="title">Title</option>		
			<option value="author">Author</option>
			<option value="year">Year</option>
		</select>	
	</form>
</div>

<form:form method="post" commandName="folder_form" action="${url}deleteFromFolder">
<table class="capelin-folder-view">
<tr>
	<th>&nbsp;</th>
	<th>Type</th>
	<th>Title</th>
	<th>Author</th>	
	<th>Subjects</th>
	<th>Year</th>
</tr>
<c:set var="newline" value="\n"/>

<c:forEach items="${folder_list}" var="data">
	<tr>
		<td>
		<form:checkbox path="ids" value="${data.id}" />
		</td>
		<td><b>${data.documentType}</b></td>
		<td>
			<a href="<c:url value="${url}viewDataPlain&id=${data.id}"/>" class="lytebox" data-lyte-options="group:kept">${data.title}</a>
		</td>
		<td>${capelin:scl(data.author)}</td>
		<td>${capelin:br(data.subject)}</td>
		<td>${capelin:br(data.year)}</td>
	</tr>	
	<tr><td colspan=6><hr/></td></tr>
</c:forEach>

</table>

<table align="center">
	<tr>
		<td>&nbsp;</td>
		<td><input type="submit" name="Remove from Folder" value="Remove from Folder"></td>
		<td><input type="button" value="Clear Folder" onclick="javascript:window.location='<c:url value="${url}clearFolder"/>';" /></td>
		 
		<td><input type="button" value="Print Folder" onclick="javascript:window.location='<c:url value="${url}printFolder"/>';" /></td>
		<td><input type="button" value="Mail Folder" onclick="javascript:window.location='<c:url value="${url}toMailFolder"/>';" /></td>
	</tr>
</table>
</form:form>
</c:if>
<table align="center">
	<tr>
		<td><a href="<c:url value="${url}searchPageTravel"/>">Back to Search Result</a></td>
	</tr>
</table>
