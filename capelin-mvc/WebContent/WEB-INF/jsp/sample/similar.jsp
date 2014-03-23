<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Find Similar Words</h1>

<form:form method="post" action="${url}searchSimilar" commandName="search_form">
	<div class="capelin-simple-form">
		<form:label path="term">Keyword:</form:label>
		<form:select path="term">
		    <form:option value="author">Author</form:option>
			<form:option value="title">Title</form:option>
			<form:option value="subject">Subject</form:option>
			<form:option value="journalTitle">Journal</form:option>
		</form:select>		
		<form:input path="value" size="20" />
		<input type="submit" value="Search" />
	</div>
</form:form>

<c:import url="../jsp/sample/_similarResult.jsp"/>