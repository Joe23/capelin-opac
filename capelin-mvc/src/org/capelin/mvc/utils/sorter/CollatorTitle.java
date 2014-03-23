package org.capelin.mvc.utils.sorter;

import org.capelin.core.models.CapelinRecord;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
class CollatorTitle extends Collator {
	@Override
	public int compare(Object o1, Object o2) {
		CapelinRecord c1 = (CapelinRecord) o1;
		CapelinRecord c2 = (CapelinRecord) o2;
		return stopWordCompare(c1.getTitle().toLowerCase(), c2.getTitle().toLowerCase());
	}	
}
