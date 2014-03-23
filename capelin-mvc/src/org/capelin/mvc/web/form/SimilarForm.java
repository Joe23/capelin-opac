package org.capelin.mvc.web.form;

import org.capelin.core.utils.StaticStrings;

public class SimilarForm {
	private String term;
	private String value;
	public String getTerm() {
		return term;
	}
	public String getValue() {
		return value;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getWiderValue(){
		String tmp = (new StringBuffer(value.trim()).append("~")).toString();
		return tmp.replaceAll(StaticStrings.SPACE, "~ ");		
	}
}
