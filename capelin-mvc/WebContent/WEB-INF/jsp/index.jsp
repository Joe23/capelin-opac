<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2011
 * 
--%>
<h1>Welcome to Capelin-OPAC!</h1>
<p>Hopefully you will enjoy this application!</p>
<p>This is a framework-like solution for (Online Public Access Catalogs) OPACs, which contains a full back-end relational database, search index, and front-end search functionality. This project is mainly designed for the Queen Elizabeth II Library at Memorial University, but targets many other libraries in Newfoundland.</p>

<div class="capelin-block">
	<h1>Purpose</h1> 
	<p>
	Although there are many other similar projects around, this project is designed for bibliographic record management and searching. It is a stand-alone application that has a database back-end and has its own create/update/retrieve/delete functions. It also has a power search function that you can customize. 
	</p>
</div>
<div class="capelin-block">
	<h1>Version</h1> 
	<p>This is the version <%= org.capelin.mvc.Version.version %> that has been fully tested!</p>
</div>
<div class="capelin-block">
	<h1>Author</h1> 
	<p>Author of this application is Jing Xiao, who was the Senior Programmer of Memorial University when this was developed. See <a href="info/about.html">About Us</a></p>
</div>
<div class="capelin-block">
	<h1>Architecture</h1> 
	<p>
		Database: This sample shipped a lightweight java database, hsql. The default is to use memory for record and index.  See the online document to change it.<br/>
		Application: All applications are written in pure Java. <br/>
		Front-end: If you can see this page, the servlet container should be configured successfuly! :) <br/>
	</p>
</div>