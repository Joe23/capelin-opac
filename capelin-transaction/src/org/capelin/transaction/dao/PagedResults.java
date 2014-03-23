package org.capelin.transaction.dao;

import java.util.List;
/**
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
@SuppressWarnings("rawtypes")
public class PagedResults {
	
	private int totalResultNumber;
	private int totalPageNumber;
	
	private List resultList;
	
	
	public PagedResults(int totalResultNumber, int totalPageNumber, List resultList) {
		super();
		this.totalResultNumber = totalResultNumber;
		this.totalPageNumber = totalPageNumber;
		this.resultList = resultList;
	}	

	public PagedResults() {
	}

	public int getTotalResultNumber() {
		return totalResultNumber;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public List getResultList() {
		return resultList;
	}
}
