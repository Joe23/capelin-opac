package org.capelin.mvc.utils;

import org.capelin.core.utils.StaticStrings;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * 
 * Utilities that help web parsing.
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class WebViewUtils {
	public static final String TILE_DEF = "Tiles_Definition_Name";
	public static final String MINUS = "-";
	public static final String PLUS = "+";
	public static final String ZERO = "0";
	

	public static final String U_QUOTE = "%27";
	public static final String U_DOUBLE_QUOTE = "%22";
	public static final String U_PLUS = "%2B";
	
	public static final String BASIC = "basic";
	public static final String ADVANCED = "advanced";
	public static final String TYPE = "documentType";
	
	public static final String ID = "id";
	public static final String AUTHOR = "author";
	public static final String TITLE = "title";
	public static final String YEAR = "year";	
	

	public static final String JOURNALTITLE = "journalTitle";
	public static final String NOTES = "notes";	
	public static final String ORIGINALNUMBER = "originalNumber";	
	public static final String SUBJECT = "subject";
	public static final String OTHERS = "others";
	public static final String ALL = "";
	public static final String STAR = "*";
	
	
	
	public static final String trim(String s) {
		String result = s;
		if (s != null) {
			result = s.trim();
			if (StaticStrings.EMPLTY.equals(result)) {
				return null;
			}
		}
		return result;
	}
	
	public static final boolean isValidYear(String year){
		if(STAR.equals(year)) return true;
		if(null == year || StaticStrings.EMPLTY.equals(year) || WebViewUtils.PLUS.equals(year)  || year.length() != 4) return false;
		for(int i=0;i<year.length();i++){
			if(!Character.isDigit(year.charAt(i))) return false;
		}
		return true;		
	}
	
	public static final boolean isValidSearchTerm(char c){
		int ascii = (int) c;
		// number
		if(ascii >= 48 && ascii <= 57) return true;
		
		// < = >
		//if(ascii >= 60 && ascii <= 63) return true;
		
		// upper case
		if(ascii >= 65 && ascii <= 90) return true;
		
		// lower case
		if(ascii >= 97 && ascii <=122) return true;
		
		
		// + -
		//return (ascii == 43 || ascii == 45);
		
		// ' - ,
		return ascii == 39 || ascii == 45 || ascii == 44;
	}
	
	public static boolean isValidSearchTerm(String term){
		if (term.length() <= 0) return false;
		boolean flag = true;
		for(int i=0;flag && i<term.length();i++){
			flag = isValidSearchTerm(term.charAt(i));
		}
		return flag;
	}
	
	public static String getAllRequredTermValue(String value, String requiredSymbol){
		if (null == value)
			return StaticStrings.EMPLTY;
		value = value.substring(1,value.length()-1);
		return getAllRequredTermValues(value, requiredSymbol);
	}
	
	public static String getAllRequredTermValues(String value, String requiredSymbol){
		value= value.replaceAll("(?i) and ", StaticStrings.SPACE);
		value= value.replaceAll("(?i) or ", StaticStrings.SPACE);
		value= value.replaceAll("(?i) not ", StaticStrings.SPACE);
		value= value.replaceAll("\\.", StaticStrings.EMPLTY);
		StringBuffer sb = new StringBuffer();
		
		value= value.replaceAll("\\[", StaticStrings.SPACE);
		value= value.replaceAll("\\]", StaticStrings.SPACE);
		value= value.replaceAll("\\(", StaticStrings.SPACE);
		value= value.replaceAll("\\)", StaticStrings.SPACE);
		value= value.replaceAll(" -", StaticStrings.SPACE);
		//value= value.replaceAll("- ", StaticStrings.SPACE);
		value= value.replaceAll("-", "\\-");
		
		
		/*
		for (int i = 0; i < value.length(); i++) {
			if (WebViewUtils.isValidSearchTerm(value.charAt(i))) {
				sb.append(value.charAt(i));
			} else {
				sb.append(StaticStrings.SPACE);
			}
		}
		String[] a = sb.toString().split(StaticStrings.SPACE);
		sb = new StringBuffer();
		*/
		String[] a = value.split(StaticStrings.SPACE);
		String test;
		for (int i = 0; i < a.length; i++) {
			test = a[i];
			
			if (WebViewUtils.isValidSearchTerm(test)){
				sb.append(requiredSymbol).append(test)
						.append(StaticStrings.SPACE);
			}else if(test.length() >=1){
				if(WebViewUtils.isValidSearchTerm(test.substring(0, test.length()-1))){
					sb.append(requiredSymbol).append(test.substring(0, test.length()-1))
						.append(StaticStrings.SPACE);
				}else if(WebViewUtils.isValidSearchTerm(test.substring(1, test.length()))){
					sb.append(requiredSymbol).append(test.substring(1, test.length())).append(StaticStrings.SPACE);
				}
			}
		}
		return sb.toString();
	}	
}