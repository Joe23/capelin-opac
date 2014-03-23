<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="capelin" uri="capelin-opac.org" %>
		
<table class="capelin-data-view" align="center">
	<tr>
		<th>Capelin Id:</th>
		<td>${data.id}</td>
	</tr>
	<c:if test="${ ! empty data.author}" >
	<tr>
		<th>Author:</th>
		<td>
			<c:forEach var="item" items="${capelin:itemsToList(data.author)}">
				<c:if test="${! empty item}" >
				${item} <br />
				</c:if>
			</c:forEach>
		</td>
	</tr>
	</c:if>
		
	<tr>
		<th>${data.documentType}</th>
		<td>${data.title}</td>
	</tr>
	
	<c:if test="${ ! empty data.publicationInfo}" >
	<tr>
		<th>Publication Info</th>
		<td>${data.publicationInfo}</td>
	</tr>
	</c:if>

	<c:if test="${ ! empty data.journalTitle}" >
	<tr>
		<th>Journal Title:</th>
		<td>
			<c:forEach var="item" items="${capelin:itemsToList(data.journalTitle)}">
				<c:if test="${! empty item}" >
				${item}<br />
				</c:if>
			</c:forEach>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.journalAbbrev}" >
	<tr>
		<th>Journal Abbrev</th>
		<td>${data.journalAbbrev}</td>
	</tr>
	</c:if>

	
	<c:if test="${ ! empty data.isbn}" >
	<tr>
		<th>ISBN:</th>
		<td>${data.isbn}</td>
	</tr>
	</c:if>
		
	<c:if test="${ ! empty data.issn}" >
	<tr>
		<th>ISSN:</th>
		<td>${data.issn}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.edition}" >
	<tr>
		<th>Edition:</th>
		<td>${data.edition}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.authorAdd}" >
	<tr>
		<th>Added Author:</th>
		<td>${data.authorAdd}</td>
	</tr>
	</c:if>

	<c:if test="${ ! empty data.authorMain}" >
	<tr>
		<th>Main Author:</th>
		<td>${data.authorMain}</td>
	</tr>
	</c:if>

	<c:if test="${ ! empty data.language}" >
	<tr>
		<th>Language:</th>
		<td>${data.language}</td>
	</tr>
	</c:if>

	
	
	<c:if test="${ ! empty data.subject}" >
	<tr>
		<th>Subject:</th>
		<td>
			<c:forEach var="item" items="${capelin:itemsToList(data.subject)}">
				<c:if test="${! empty item}" >
					${item}<br />
				</c:if>				
			</c:forEach>
		</td>		
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.publisher}" >	
	<tr>
		<th>Publisher:</th>
		<td>${data.publisher}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.series}" >	
	<tr>
		<th>Series:</th>
		<td>${data.series}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.notes}" >	
	<tr>
		<th>General Notes:</th>
		<td>${data.notes}</td>
	</tr>
	</c:if>

	
	<c:if test="${ ! empty data.volume}" >	
	<tr>
		<th>Volume:</th>
		<td>${data.volume}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.number}" >	
	<tr>
		<th>Number:</th>
		<td>${data.number}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.publicationDate}" >	
	<tr>
		<th>Publication Date:</th>
		<td>${data.publicationDate}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.place}" >	
	<tr>
		<th>Publication Place:</th>
		<td>${data.place}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.titleOther}" >	
	<tr>
		<th>Other Title:</th>
		<td>${data.titleOther}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.titleMain}" >	
	<tr>
		<th>Main Title:</th>
		<td>${data.titleMain}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.url}" >	
	<tr>
		<th>Availability:</th>
		<td>${capelin:url(data.url)}</td>
	</tr>
	</c:if>
	
	
	<c:if test="${ ! empty data.originalNumber}" >	
	<tr>
		<th>Original Number:</th>
		<td>${data.originalNumber}</td>
	</tr>
	</c:if>
	
	<c:if test="${ ! empty data.year}" >	
	<tr>
		<th>Year:</th>
		<td>${data.year}</td>
	</tr>
	</c:if>	
</table>