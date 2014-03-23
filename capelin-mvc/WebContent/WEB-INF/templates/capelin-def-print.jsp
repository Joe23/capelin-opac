<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<title><tiles:getAsString name="title" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/theme/print.css"/>" />
</head>

<body>

<div id="capelin-page">	
	<div id="capelin-header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="capelin-content">
			<tiles:insertAttribute name="content" />
	</div>
	 
	<div id="capelin-footer">
		<tiles:insertAttribute name="footer" />
	</div>		
</div>
</body>
</html>