package org.capelin.mvc.utils;

import java.util.List;
import java.util.Map;

/**
 * HTMLTable Helper
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * 
 */
public class HtmlTableHelper {
	protected StringBuffer getTablePrefix(StringBuffer s) {
		String pre = "<table width='95%'>";
		if(null == s){
			s = new StringBuffer(pre);
		}else{
			s.append(pre);
		}
		
		return s;
	}

	protected StringBuffer addTablePostfix(StringBuffer sb) {
		return sb.append("</table>");
	}

	protected StringBuffer addRecrodsToTable(StringBuffer sb,
			Map<String, String> map) {
		for (String n : map.keySet()) {
			sb.append("<tr><th align='left' width='20%'>").append(n).append(": </th><td width='80%'>")
					.append(map.get(n)).append("</td></tr>");
		}
		return sb;
	}
	
	public StringBuffer recordListToTable(StringBuffer osb, List<Map<String,String>> list){
		StringBuffer sb = getTablePrefix(osb);
		for(Map<String,String> map : list){
			addRecrodsToTable(sb,map);
			sb.append("<tr><td colspan='2'><hr/></td></tr>");
		}
		return addTablePostfix(sb);
	}
}
