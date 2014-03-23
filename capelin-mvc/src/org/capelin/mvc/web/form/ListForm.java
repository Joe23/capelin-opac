package org.capelin.mvc.web.form;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 *
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class ListForm {
	private String field;
	private String startWith;
	private String orderBy;
	public String getField() {
		return field;
	}
	public String getStartWith() {
		return startWith;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setField(String field) {
		this.field = field;
	}
	public void setStartWith(String startWith) {
		this.startWith = startWith;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
