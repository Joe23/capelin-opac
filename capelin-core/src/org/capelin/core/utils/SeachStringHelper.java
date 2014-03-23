package org.capelin.core.utils;
/**
 * 
 * Work with stop word and other functionalities
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class SeachStringHelper {
	private static String [] stopWords = {"a","an","and","or","not","the","that","which","le","la","les"};
	
	public static String stripStopWords(Object o){
		if(null == o) return null;
		String s = (String) o;
		for(String st: stopWords){
			s= s.replaceAll(new StringBuffer("(?i) ").append(st).toString(), StaticStrings.SPACE);
		}
		return s;
	}
}
