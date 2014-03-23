<%-- 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @project https://github.com/Joe23/capelin-opac
 * @date 2012-02
 * 
--%>
<h1>FAQ</h1>

<div class="capelin-block">
	<h1>Why it is called capelin-opac?</h1> 
	<p>
		Capelin is a kind of small fish that can be found in only a few places. See <a href="http://en.wikipedia.org/wiki/Capelin">Wikipedia</a><br>
		The founder of the project, Jing, was living in Newfoundland, which is one of the places that have that kind of fish.<br>
		On the other hand, a lot of open source projects choose animal names such as ant, squid, squirrel, tomcat and etc. He just picked up this unique one.<br>
		Meanwhile, this project rank the first in Google for the the term "capelin opac".  
	</p>
</div>

<div class="capelin-block">
	<h1>What licence it is?</h1> 
	<p>
		<a href="http://www.gnu.org/licenses/agpl.html">GNU Affero General Public License</a>. 
	</p>
</div>

<div class="capelin-block">
	<h1>How can I get help</h1> 
	<p>
		You could get documentations at <a target="_blank" href="https://github.com/Joe23/capelin-opac">main projet page.</a>
	</p>
</div>

<div class="capelin-block">
	<h1>How can I make advanced search query?</h1> 
	<p>
		The record search is based on <a target="_blank" href="http://lucene.apache.org">Apache Lucene</a>. You can get some detailed query example from  <a target="_blank" href="http://lucene.apache.org/java/3_0_0/queryparsersyntax.html">here</a>.
	</p>
</div>

<div class="capelin-block">
	<h1>Why no record is found?</h1> 
	<p>
		If you just deply the war file to the application, there would be no data in the database. You could either enter data by yourself, or import data followed the project wiki.
	</p>
</div>

<div class="capelin-block">
	<h1>Why the record/record is not persistent?</h1> 
	<p>
		If you used default setting, the data record and index are stored in memory. If you restart the application, all informaion will be gone. You need to change it to store in a database and a folder. For details see the wiki page from the <a href="https://github.com/Joe23/capelin-opac">main projet page.</a>
	</p>
</div>

<div class="capelin-block">
	<h1>What's the username and password for the sample catalog?</h1> 
	<p>
		username: capelin password: opac<br>
		This information is stored in a xml configuration file.	
	</p>
</div>