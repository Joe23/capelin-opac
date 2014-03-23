<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h1>Search Your Words</h1>

<form:form method="post" action="${url}searchAdvanced"
	commandName="search_form">
	<div class="capelin-advanced-form">
	<div class="row"><label>Document Type</label> 
		<form:select
			path="documentType">
			<form:option value="">All</form:option>
			<form:option value="article">Article</form:option>
			<form:option value="book">Book Review</form:option>
		</form:select>
	</div>

	<div class="row"><form:label path="title">Title:</form:label>
		<form:input path="title" size="30" /> <form:select
			path="titleOp">
			<form:option value="AND" />
			<form:option value="OR" />
			<form:option value="NOT" />
		</form:select>
	</div>

	<div class="row"><form:label path="subject">Subject:</form:label>
		<form:input path="subject" size="30" /> <form:select
			path="subjectOp">
			<form:option value="AND" />
			<form:option value="OR" />
			<form:option value="NOT" />
		</form:select>
	</div>

	<div class="row"><form:label path="author">Author:</form:label>
		<form:input path="author" size="30" /> <form:select
			path="authorOp">
			<form:option value="AND" />
			<form:option value="OR" />
			<form:option value="NOT" />
		</form:select>
	</div>

	<div class="row"><form:label path="journalTitle">Journal Title:</form:label>
		<form:input path="journalTitle" size="30" /> <form:select
			path="journalTitleOp">
			<form:option value="AND" />
			<form:option value="OR" />
			<form:option value="NOT" />
		</form:select>
	</div>
	
	<div class="row"><form:label path="all">All:</form:label>
		<form:input path="all" size="30" /> <form:select
			path="allOp">
			<form:option value="AND" />
			<form:option value="OR" />
			<form:option value="NOT" />
		</form:select>
	</div>	

	<div class="row"><label>From Year:</label> 
		<form:input	path="rangedMin" size="4" maxlength="4" />
	</div>
	
	<div class="row"><label>To Year:</label> 
		<form:input	path="rangedMax" size="4" maxlength="4" />
	</div>
	<div class="row">
		<form:label path="sortTerm">Sort By:</form:label>
		<form:select path="sortTerm">
		    <form:option value="year">Year</form:option>		    
			<form:option value="">Relevence</form:option>
			<form:option value="id">ID</form:option>
			<form:option value="documentType">Type</form:option>
		</form:select>
	</div>

	<div class="row">
	<table align="center">
		<tr>
			<td><input class="center" type="submit" value="Search" /></td>
		</tr>
	</table>
	</div>
	</div>
</form:form>

<c:import url="../jsp/sample/_searchResult.jsp" />