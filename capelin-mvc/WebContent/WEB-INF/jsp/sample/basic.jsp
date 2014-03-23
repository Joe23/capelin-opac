<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Search Your Words</h1>

<form:form method="post" action="${url}searchBasic" commandName="search_form">
	<div class="capelin-simple-form">
		&nbsp;&nbsp;&nbsp;
		<form:label path="matchAll">Match All:</form:label>		
		<form:checkbox path="matchAll" />
		
		<label>Type:</label>
		<form:select path="documentType">
			<form:option value="">All</form:option>
			<form:option value="article">Article</form:option>
			<form:option value="book">Book</form:option>
			<form:option value="chapter">Chapter</form:option>
			<form:option value="thesis">Thesis</form:option>
		</form:select>
	
		<form:label path="term">Keyword:</form:label>
		<form:select path="term">
		    <form:option value="">All</form:option>
		    <form:option value="author">Author</form:option>
			<form:option value="title">Title</form:option>
			<form:option value="subject">Subject</form:option>
			<form:option value="journalTitle">Journal</form:option>
		</form:select>		
		<form:input path="value" size="20" />
		
		<form:label path="sortTerm">Sort By:</form:label>
		<form:select path="sortTerm">
		    <form:option value="year">Year</form:option>		    
			<form:option value="">Relevence</form:option>
			<form:option value="id">ID</form:option>
			<form:option value="documentType">Type</form:option>
		</form:select>
		
		<input type="submit" value="Search" />
	</div>
</form:form>
<c:import url="../jsp/sample/_searchResult.jsp"/>