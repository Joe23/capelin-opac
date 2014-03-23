package org.capelin.mvc.web.form;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.capelin.mvc.utils.WebViewUtils;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 *
 * 
 * Form object that contains the basic search information.
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * 
 * @see AdvancedSearchObject
 *
 */
public class BasicSearchObject implements SearchFormObject{
	
	

	protected static String[] SEARCH_KEYS = null;
	
	protected Map<String, SearchFormUnit> map;
	protected String documentType;
	
	protected String term;
	protected String value;
	protected Query luceneQuery;
	protected Filter luceneFilter;
	protected Sort luceneSort;
	protected String sortTerm = WebViewUtils.YEAR;
	protected boolean matchAll = true;
	protected boolean keyword = false;

    public BasicSearchObject(){
            setUpKeys();
    }

    protected void setUpKeys(){
        if(SEARCH_KEYS == null){
                SEARCH_KEYS = new String [] {WebViewUtils.TITLE, WebViewUtils.SUBJECT, WebViewUtils.AUTHOR, WebViewUtils.JOURNALTITLE, WebViewUtils.YEAR, WebViewUtils.NOTES, WebViewUtils.ORIGINALNUMBER, WebViewUtils.ALL, WebViewUtils.OTHERS};
        }
    }
    
	/**
	 * Note: If you reset term or value. The map may not work properly.
	 */
	@Override
	public Map<String, SearchFormUnit> getMap() {
		if(map == null){
			map = new HashMap<String, SearchFormUnit>(1);			
		}
		map.put(term, new SearchFormUnit(value));
		return map;			
	}

	@Override
	public String getDocumentType() {
		return documentType;
	}

	@Override
	public void setDocumentType(String type) {
		documentType = type;		
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getResultView() {		
		return WebViewUtils.BASIC;
	}

	@Override
	public String getRangedTermForQuery() {
		return null;
	}

	@Override
	public String getRangedMinForQuery() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRangedMaxForQuery() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSearchKeys(){
		return SEARCH_KEYS;
	}
	
	@Override
	public Query getLuceneQuery() {
		return luceneQuery;
	}
	@Override
	public void setLuceneQuery(Query luceneQuery) {
		this.luceneQuery = luceneQuery;
	}
	
	@Override
	public boolean isMatchAll() {
		return matchAll;
	}

	public void setMatchAll(boolean matchAll) {
		this.matchAll = matchAll;
	}
	
	@Override
	public String getSortTerm() {
		return sortTerm;
	}

	public void setSortTerm(String sortTerm) {
		this.sortTerm = sortTerm;
	}
	
	@Override
	public Filter getLuceneFilter() {
		return luceneFilter;
	}
	
	@Override
	public Sort getLuceneSort() {
		return luceneSort;
	}
	
	@Override
	public void setLuceneFilter(Filter luceneFilter) {
		this.luceneFilter = luceneFilter;
	}

	@Override
	public void setLuceneSort(Sort luceneSort) {
		this.luceneSort = luceneSort;
	}

	public boolean isKeyword() {
		return keyword;
	}

	public void setKeyword(boolean keyword) {
		this.keyword = keyword;
	}
}
