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
	<link rel="stylesheet" type="text/css" href="<c:url value="/theme/style.css"/>" />
	<script src="<c:url value="/theme/scripts.js"/>"></script>
	<script type="text/javascript">var capelinThemeBase ='<c:url value="/theme/"/>';</script>
	<script src="<c:url value="/theme/color.js"/>"></script>
	<script src="<c:url value="/ext/jquery-1.7.1.min.js"/>"></script>
	<script src='<c:url value="/ext/ajax.js"/>'></script>
	<script src='<c:url value="/ajax/engine.js"/>'></script>
	<script src='<c:url value="/ajax/util.js"/>'></script>
	<script src='<c:url value="/ajax/interface/capelinAjax.js"/>'></script>
</head>
<body>

<div style="height: 10px;"></div>
<div id="capelin-page">	
	<div id="capelin-top">
		<tiles:insertAttribute name="top" />
	</div>
	<div id="capelin-header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="capelin-content">
		<div id="capelin-content-col1">
			<div id="capelin-content-menu">
				<tiles:insertAttribute name="menu" />
			</div>			
		</div>
		<div id="capelin-content-col2">
			<div id="capelin-content-main">
				<tiles:insertAttribute name="content" />
			</div>
		</div> 
	</div>
	 
	<div id="capelin-footer">
		<tiles:insertAttribute name="footer" />
	</div>		
</div>
</body>
</html>