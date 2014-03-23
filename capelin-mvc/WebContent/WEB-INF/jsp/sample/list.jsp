<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>List Data</h1>
<form:form method="post" action="${url}listCatalog" commandName="list_form">
	<div class="capelin-simple-form">

	<form:label path="field">Field</form:label>:
	<form:select path="field">
		<form:option value="title">Title</form:option>
		<form:option value="subject">Subject</form:option>
		<form:option value="author">Author</form:option>
		<!-- 
		<form:option value="ISBN" />
		<form:option value="ISSN" />
		 -->
	</form:select>

	<form:label path="startWith">Start With</form:label>:
	<form:select path="startWith">
		<form:option value="A" />
		<form:option value="B" />
		<form:option value="C" />
		<form:option value="D" />
		<form:option value="E" />
		<form:option value="F" />
		<form:option value="G" />
		<form:option value="H" />
		<form:option value="I" />
		<form:option value="J" />
		<form:option value="K" />
		<form:option value="L" />
		<form:option value="M" />
		<form:option value="N" />
		<form:option value="O" />
		<form:option value="P" />
		<form:option value="Q" />
		<form:option value="R" />
		<form:option value="S" />
		<form:option value="T" />
		<form:option value="U" />
		<form:option value="V" />
		<form:option value="W" />
		<form:option value="X" />
		<form:option value="Y" />
		<form:option value="Z" />
		<form:option value="">All</form:option>
	</form:select>

	<form:label path="orderBy">Order By</form:label>:
	<form:select path="orderBy">
		<form:option value="1">Ascending</form:option>
		<form:option value="0">Decending</form:option>
	</form:select>

	<input type="submit" name="submit" value="List" />
</div>
</form:form>
<c:import url="../jsp/sample/_listResult.jsp" />