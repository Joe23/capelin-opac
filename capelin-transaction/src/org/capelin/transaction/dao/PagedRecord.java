package org.capelin.transaction.dao;

import java.util.List;

import org.capelin.core.models.CapelinRecord;
/**
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */

public class PagedRecord {	
	private CapelinRecord capelinRecord;
	private List<Integer> resultIds;
	private int position;
	public CapelinRecord getCapelinRecord() {
		return capelinRecord;
	}
	public List<Integer> getResultIds() {
		return resultIds;
	}
	public int getPosition() {
		return position;
	}
	public void setCapelinRecord(CapelinRecord capelinRecord) {
		this.capelinRecord = capelinRecord;
	}
	public void setResultIds(List<Integer> resultIds) {
		this.resultIds = resultIds;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public PagedRecord(CapelinRecord c, List<Integer> ids, int pos){
		capelinRecord = c;
		resultIds = ids;
		position = pos;
	}
}
