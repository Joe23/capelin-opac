package org.capelin.mvc.web.form;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 *
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class SearchFormUnit {
	public static String AND = "AND";
	public static String OR = "OR";
	public static String NOT = "NOT";
	
	protected String value;
	protected String op = AND;
	
	public SearchFormUnit(){
	}
	
	public SearchFormUnit(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
}
