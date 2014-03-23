package org.capelin.mvc.utils;

import static org.apache.lucene.util.Version.LUCENE_24;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.capelin.core.utils.StaticStrings;
import org.capelin.core.utils.UniqueList;
import org.capelin.mvc.web.form.SearchFormObject;
import org.springframework.util.CollectionUtils;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html
 *  
 * MultiIndexLuceneBuilder is an extension of LuceneBuilder that can search multi-index.
 * 
 * 
 * Reserved Class, not fully implemented yet.
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class MultiIndexLuceneBuilder extends LuceneBuilder{
	protected Map<String,String[]> multiIndexMap;
	
	protected String [] allIndexs;

	public void setMultiIndexMap(Properties prop) {
		Map<String, String> springMap = new HashMap<String,String>();
		CollectionUtils.mergePropertiesIntoMap(prop, springMap);
		multiIndexMap = new HashMap<String,String[]>(springMap.size());
		for(String key : springMap.keySet()){
			multiIndexMap.put(key, springMap.get(key).trim().split(StaticStrings.COMMA));
		}
		setUpAllIndexes();
	}
	
	@Override
	protected QueryParser getQueryParser(SearchFormObject form, String key) {
		// check if search all
		if(StaticStrings.EMPLTY.equals(key)){
			return new MultiFieldQueryParser(Version.LUCENE_24, allIndexs, getAnalyzer(form.isKeyword()));
		}
		return new MultiFieldQueryParser(LUCENE_24, multiIndexMap.get(key), getAnalyzer(form.isKeyword()));
	}
	
	protected void setUpAllIndexes(){
		List<String> keys = new UniqueList<String>();
		for(String eachKey : multiIndexMap.keySet()){			
			Arrays.asList(multiIndexMap.get(eachKey));
			keys.addAll(Arrays.asList(multiIndexMap.get(eachKey)));
		}
		String [] tmp = new String[0];
		allIndexs = keys.toArray(tmp);
	}
	
	@Override
	protected QueryParser getQueryParser(String key) {
		return new MultiFieldQueryParser(LUCENE_24, multiIndexMap.get(key), getAnalyzer(true));
	}	
}
