package org.capelin.core.utils;

import java.util.Collection;
import java.util.LinkedList;
/**
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html
 * 
 * A List that contains unique elements. 
 * 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * 
 */
public class UniqueList<E> extends LinkedList<E> {

	private static final long serialVersionUID = 876323262645176350L;

	public boolean add(E object) {
		if (this.contains(object))
			return false;
		return super.add(object);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean result = true;
		for (E e : collection) {
			if (!add(e)) {
				result = false;
			}
		}
		return result;
	}

	public UniqueList(Collection<? extends E> collection) {
		super();
		addAll(collection);
	}
	public UniqueList() {
		super();
	}	
}
