<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.GregorianCalendar"%>

<div id="capelin-footer-menu">
<!-- 
	<a href="<c:url value="/index.html"/>" title="Home">Home</a>|
	<a href="<c:url value="/info/about.html"/>" title="About Us">About Us</a>|
	<a href="<c:url value="/info/faq.html"/>" title="Contact Us">FAQ</a>	 
	<br />
 -->	
	<a href="https://github.com/Joe23/capelin-opac" target="_blank">Capelin OPAC &copy; 2010-<%= (new GregorianCalendar()).get(GregorianCalendar.YEAR) %></a>
</div>

<script type="text/javascript">
// <!--
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-20510042-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
// -->
</script>
