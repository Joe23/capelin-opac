package org.capelin.mvc.utils.sorter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import org.capelin.core.models.CapelinRecord;
import org.capelin.mvc.utils.WebViewUtils;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class RecordSorter {
	public void sort(List<CapelinRecord> list, String command) {
		if (null == command) {
			return;
		} else if (WebViewUtils.AUTHOR.equals(command)) {
			sort(list, author);
		} else if (WebViewUtils.TITLE.equals(command)) {
			sort(list, title);
		} else if (WebViewUtils.TYPE.equals(command)) {
			sort(list, type);
		} else if (WebViewUtils.YEAR.equals(command)) {
			sort(list, year);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sort(List<CapelinRecord> list, Comparator c) {
		Object[] a = list.toArray();
		Arrays.sort(a, c);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}
	}

	private static RecordSorter sorter;

	public static RecordSorter getInstance() {
		if (null == sorter) {
			sorter = new RecordSorter();
		}
		return sorter;
	}

	private CollatorAuthor author;
	private CollatorDocumentType type;
	private CollatorTitle title;
	private CollatorReverseYear year;

	private RecordSorter() {
		author = new CollatorAuthor();
		type = new CollatorDocumentType();
		title = new CollatorTitle();
		year = new CollatorReverseYear();

	}
}