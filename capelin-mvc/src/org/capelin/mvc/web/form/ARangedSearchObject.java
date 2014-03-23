package org.capelin.mvc.web.form;

import org.capelin.mvc.utils.WebViewUtils;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 *
 * 
 * Form object that contains the advanced search information.
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @see BasicSearchObject
 */
public class ARangedSearchObject extends AdvancedSearchObject {
	protected String rangedTerm;
	protected String rangedMin;
	protected String rangedMax;

	public ARangedSearchObject() {
		super();
		rangedTerm = WebViewUtils.YEAR;		
	}
	
	public String getRangedTerm() {
		if(null == rangedMin && null == rangedMax) return null;		
		return rangedTerm;
	}	

	public String getRangedMin() {
		return rangedMin;
	}

	public String getRangedMax() {
		return rangedMax;
	}

	public void setRangedMin(String min) {
		if(WebViewUtils.isValidYear(min)){
			rangedMin = min; 
		}
	}

	public void setRangedMax(String max) {
		if(WebViewUtils.isValidYear(max)){
			rangedMax = max; 
		}
	}

	@Override
	public String getRangedMinForQuery() {
		if(!WebViewUtils.isValidYear(rangedMin)){
			return null;
		}
		return rangedMin;
	}

	@Override
	public String getRangedMaxForQuery() {
		if(!WebViewUtils.isValidYear(rangedMax)){
			return null; 
		}
		return rangedMax;
	}
	
	@Override
	public String getRangedTermForQuery(){
		if(null == rangedMin && null == rangedMax) return null;
		return rangedTerm;
	}	
	
}
