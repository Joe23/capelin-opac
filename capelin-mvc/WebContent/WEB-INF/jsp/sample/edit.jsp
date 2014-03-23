<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>${message} Record</h1>
 
<form:form method="post" action="${url}saveData" commandName="data">

	<form:hidden path="id" />
	<table class="capelin-data-view">
	<tr>
		<th><label>Document Type</label></th>
		<td><form:select path="documentType">
				<form:option value="Article" />
				<form:option value="Book" />
				<form:option value="Chapter" />
				<form:option value="Thesis" />
			</form:select>
		</td>
	</tr>	

	<tr>
		<th><form:label path="author">Author:</form:label></th>
		<td><form:textarea path="author" cols="60" rows="3" /></td>
	</tr>		
	<tr>
		<th><form:label path="title">Title:</form:label></th>
		<td><form:textarea path="title" cols="60" rows="3" /></td>
	</tr>
	
	<tr>
		<th><form:label path="publicationInfo">Publication Info:</form:label></th>
		<td><form:textarea path="publicationInfo" cols="60" rows="3" /></td>
	</tr>	
	<tr>
		<th><form:label path="journalTitle">Journal Title:</form:label></th>
		<td><form:textarea path="journalTitle" cols="60" rows="3" /></td>
	</tr>
	<tr>
		<th><form:label path="journalAbbrev">Journal Abbreviations:</form:label></th>
		<td><form:input path="journalAbbrev" size="60" maxlength="50"/></td>		
	</tr>		
	<tr>
		<th><form:label path="isbn">ISBN:</form:label></th>
		<td><form:input path="isbn" size="60" maxlength="80"/></td>
	</tr>
	<tr>
		<th><form:label path="issn">ISSN:</form:label></th>
		<td><form:input path="issn" size="60" maxlength="50"/></td>
	</tr>	
	<tr>
		<th><form:label path="edition">Edition:</form:label></th>
		<td><form:input path="edition" size="60" maxlength="8"/></td>
	</tr>

	<tr>
		<th><form:label path="authorAdd">Added Author:</form:label></th>
		<td><form:textarea path="authorAdd" cols="60" rows="3" /></td>
	</tr>
	<tr>
		<th><form:label path="authorMain">Main Author:</form:label></th>
		<td><form:input path="authorMain" size="60" maxlength="78"/></td>
	</tr>
			
	<tr>
		<th><form:label path="language">Language:</form:label></th>
		<td><form:input path="language" size="60" maxlength="8"/></td>	
	</tr>
	
	<tr>
		<th><form:label path="subject">Subject:</form:label></th>
		<td><form:textarea path="subject" cols="60" rows="3" /></td>
	</tr>
	
	<tr>
		<th><form:label path="publisher">Publisher:</form:label></th>
		<td><form:textarea path="publisher" cols="60" rows="3" /></td>
	</tr>	
	<tr>
		<th><form:label path="series">Series:</form:label></th>
		<td><form:textarea path="series" cols="60" rows="3" /></td>
	</tr>
	
	<tr>
		<th><form:label path="notes">General Notes:</form:label></th>
		<td><form:textarea path="notes" cols="60" rows="3" /></td>
	</tr>
	
	<tr>
		<th><form:label path="volume">Volume:</form:label></th>
		<td><form:input path="volume" size="60" maxlength="15"/></td>
	</tr>		
	
	<tr>
		<th><form:label path="number">Number:</form:label></th>
		<td><form:input path="number" size="60" maxlength="30"/></td>
	</tr>	
	
	<tr>
		<th><form:label path="publicationDate">Publication Date:</form:label></th>
		<td><form:input path="publicationDate" size="60" maxlength="20"/></td>
	</tr>
	
	<tr>
		<th><form:label path="place">Place:</form:label></th>
		<td><form:input path="place" size="60" maxlength="40"/></td>
	</tr>	
	
	<tr>
		<th><form:label path="titleOther">Other Title:</form:label></th>
		<td><form:textarea path="titleOther" cols="60" rows="3" /></td>
	</tr>	
	
	<tr>
		<th><form:label path="titleMain">Main Title:</form:label></th>
		<td><form:textarea path="titleMain" cols="60" rows="3" /></td>
	</tr>

	<tr>
		<th><form:label path="url">Availability:</form:label></th>
		<td><form:textarea path="url" cols="60" rows="3" /></td>
	</tr>
	<tr>
		<th><form:label path="originalNumber">Original Number:</form:label></th>
		<td><form:input path="originalNumber" size="60" maxlength="5"/></td>
	</tr>	
	<tr>
		<th><form:label path="year">Year:</form:label></th>
		<td><form:input path="year" size="60" maxlength="4"/></td>
	</tr>	
	<tr>
		<td colspan="2" align="center"><input type="submit" name="submit" value="Save"></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><a href="javascript:history.go(-1)">Back</a></td>
	</tr>
</table>
</form:form>


