package org.capelin.mvc.web.form;

import java.util.Map;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 *
 * Interface for form contains the search information.
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * 
 * @see BasicSearchObject AdvancedSearchObject ARangedSearchObject
 *
 */
public interface SearchFormObject {
	public Map<String, SearchFormUnit> getMap();

	public String getDocumentType();
	public String getResultView();
	public String[] getSearchKeys();
	public String getRangedTermForQuery();	
	public String getRangedMinForQuery();
	public String getRangedMaxForQuery();
	public String getSortTerm();
	public boolean isMatchAll();
	public boolean isKeyword();
	
	public Query getLuceneQuery();
	public void setLuceneQuery(Query query);
	public Filter getLuceneFilter();
	public Sort getLuceneSort();
	public void setLuceneFilter(Filter luceneFilter);
	public void setLuceneSort(Sort luceneSort);
	public void setDocumentType(String type);

	

}
