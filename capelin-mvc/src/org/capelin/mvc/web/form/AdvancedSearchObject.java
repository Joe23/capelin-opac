package org.capelin.mvc.web.form;

import java.util.HashMap;
import java.util.Map;

import org.capelin.mvc.utils.WebViewUtils;

/**
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * 
 * Form object that contains the advanced search information.
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @see BasicSearchObject
 */
public class AdvancedSearchObject extends BasicSearchObject {
	
	public AdvancedSearchObject() {
		super();
		setUpMap();
	}

	protected void setUpMap() {
		map = new HashMap<String, SearchFormUnit>(getSearchKeys().length);
		for (String key : getSearchKeys()) {
			map.put(key, new SearchFormUnit());
		}
	}

	public String getTitle() {
		return map.get(WebViewUtils.TITLE).getValue();
	}

	public String getSubject() {
		return map.get(WebViewUtils.SUBJECT).getValue();
	}

	public String getAuthor() {
		return map.get(WebViewUtils.AUTHOR).getValue();
	}

	public String getJournalTitle() {
		return map.get(WebViewUtils.JOURNALTITLE).getValue();
	}

	public String getNotes() {
		return map.get(WebViewUtils.NOTES).getValue();
	}
	
	public String getOriginalNumber() {
		return map.get(WebViewUtils.ORIGINALNUMBER).getValue();
	}
	
	public String getOthers() {
		return map.get(WebViewUtils.OTHERS).getValue();
	}
	
	public String getAll(){
		return map.get(WebViewUtils.ALL).getValue();
	}	
	
	public void setTitle(String value) {
		map.get(WebViewUtils.TITLE).setValue(value);
	}

	public void setSubject(String value) {
		map.get(WebViewUtils.SUBJECT).setValue(value);
	}

	public void setAuthor(String value) {
		map.get(WebViewUtils.AUTHOR).setValue(value);
	}

	public void setJournalTitle(String value) {
		map.get(WebViewUtils.JOURNALTITLE).setValue(value);
	}

	public void setNotes(String value) {
		map.get(WebViewUtils.NOTES).setValue(value);
	}
	
	public void setOthers(String value) {
		map.get(WebViewUtils.OTHERS).setValue(value);
	}
	
	public void setOriginalNumber(String value) {
		map.get(WebViewUtils.ORIGINALNUMBER).setValue(value);
	}


	public void setAll(String value){
		map.get(WebViewUtils.ALL).setValue(value);
	}
	
	public void setTitleOp(String op) {
		map.get(WebViewUtils.TITLE).setOp(op);
	}

	public void setSubjectOp(String op) {
		map.get(WebViewUtils.SUBJECT).setOp(op);
	}

	public void setAuthorOp(String op) {
		map.get(WebViewUtils.AUTHOR).setOp(op);
	}

	public void setJournalTitleOp(String op) {
		map.get(WebViewUtils.JOURNALTITLE).setOp(op);
	}
	
	public void setNotesOp(String op) {
		map.get(WebViewUtils.NOTES).setOp(op);
	}
	
	public void setOriginalNumberOp(String op) {
		map.get(WebViewUtils.ORIGINALNUMBER).setOp(op);
	}
	
	public void setAllOp(String op){
		map.get(WebViewUtils.ALL).setOp(op);
	}
	
	public void setOthersOp(String op) {
		map.get(WebViewUtils.OTHERS).setOp(op);
	}

	public String getTitleOp() {
		return map.get(WebViewUtils.TITLE).getOp();
	}

	public String getSubjectOp() {
		return map.get(WebViewUtils.SUBJECT).getOp();
	}

	public String getAuthorOp() {
		return map.get(WebViewUtils.AUTHOR).getOp();
	}

	public String getJournalTitleOp() {
		return map.get(WebViewUtils.JOURNALTITLE).getOp();
	}

	public String getNotesOp() {
		return map.get(WebViewUtils.NOTES).getOp();
	}
	
	public String getOriginalNumberOp() {
		return map.get(WebViewUtils.ORIGINALNUMBER).getOp();
	}	
	
	public String getOthersOp() {
		return map.get(WebViewUtils.OTHERS).getOp();
	}
	
	public String getAllOp(){
		return map.get(WebViewUtils.ALL).getOp();
	}


	public Map<String, SearchFormUnit> getMap() {
		return map;
	}

	@Override
	public String getResultView() {
		return WebViewUtils.ADVANCED;
	}
}
