<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="capelin" uri="capelin-opac.org" %>

<c:if test="${empty search_result && total eq 0}">
<br/><br /><center><b>No Result Found.</b></center>
</c:if>

<c:if test="${! empty search_result}">

<c:if test="${empty page}">
	<c:set var="page" value="1" />
</c:if>


<%-- Page Browser --%>
<c:import url="../jsp/sample/_pageBrowse.jsp">
	<c:param name="pageUrl" value="searchPageTravel&page" />
</c:import>
<%-- Page Browser --%>




<div class="capelin_slide_down_l">	
	<c:forEach items="${search_result}" var="data" varStatus="status">
		<div class="capelin-block" id="d_${data[0]}">
		<b style="float:right">${data[6]}</b>
		${status.count + search_result_size * page - search_result_size}
		<input type="checkbox" value="${data[0]}" class="keptsCheckbox" <c:if test="${kept[status.count-1]}" >checked</c:if>	/>
		<c:if test="${ ! empty data[2]}" >
			<b>Author:</b> ${capelin:unique(data[2])} <br />
		</c:if>
		

		<b>${data[4]}:</b>	<a class="title" href="<c:url value="${url}viewDataWithTravel&id=${data[0]}"/>">${data[1]}</a> <br>
		<c:if test="${ ! empty data[2]}" >	
		<b>Subject:</b> ${capelin:scl(data[2])} <br />
		</c:if>
		
		<c:if test="${ ! empty data[5]}" >
			<b>Publication Info:</b> ${capelin:scl(data[5])} <br />
		</c:if>
		</div>
	</c:forEach>
</div>


<%-- Page Browser --%>
<c:import url="../jsp/sample/_pageBrowse.jsp">
	<c:param name="pageUrl" value="searchPageTravel&page" />
</c:import>
<%-- Page Browser --%>


</c:if>