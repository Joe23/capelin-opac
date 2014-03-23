package org.capelin.mvc.utils.sorter;

import java.util.Comparator;
import java.util.Locale;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @see CollatorAuthor CollatorDocumentType CollatorTitle
 *
 */
@SuppressWarnings("rawtypes")
abstract class Collator implements Comparator{	
	protected static java.text.Collator collator = java.text.Collator.getInstance(Locale.US);
	
	public int compare(Object o1, Object o2) {
		if(null != o1 && null != o2) return collator.compare(o1, o2);
		if(null == o1 && null == o2)return 0;
		if(o1 == null) return -Integer.MAX_VALUE;
		return Integer.MAX_VALUE;
	}
	
	protected int stopWordCompare(Object o1, Object o2){		
		if(null == o1 && null == o2)return 0;
		if(o1 == null) return -Integer.MAX_VALUE;
		if(o2 == null)return Integer.MAX_VALUE;
		String s1 = (String) o1;
		String s2 = (String) s1;
		return collator.compare(o1, o2);
	}
}
