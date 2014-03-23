package org.capelin.core.utils;

/**
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html
 * 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 */
import java.util.List;

import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

public class RecordUtil {
	protected static RecordUtil util;

	protected RecordUtil() {
	}

	public static RecordUtil getInstance() {
		if (util == null) {
			util = new RecordUtil();
		}
		return util;
	}
	
	@SuppressWarnings("unchecked")
	public String getField(Record r, String dataField, String seperater, String subSeperater) {
		
		List<String> list = new UniqueList<String> ();
		List<String> subList;
		String tmp;
		StringBuffer subTmp;
		for (DataField df : (List<DataField>) r.getDataFields()) {
			if (dataField.equals(df.getTag())) {
				subList  = new UniqueList<String> ();
				for (Subfield sf : (List<Subfield>) df.getSubfields()) {
					tmp = sf.getData();
					if(tmp !=null){
						subList.add(tmp.trim());
					}
				}
				subTmp =  mergeList(subList, subSeperater);
				if(null != subTmp) list.add(subTmp.toString());
			}
		}
		StringBuffer sb = mergeList(list, seperater);
		return trim(sb);
	}
	
	/**
	 * Get Field from particular subfield of tag from Marc data.
	 * 
	 * @param r
	 * @param dataField
	 * @param subfield
	 * @return
	 */
	public String getField(Record r, String dataField, String subSeperater) {
		return getField(r, dataField, StaticStrings.DB_SPLIT_STRING, subSeperater);
	}

	/**
	 * Get Field from particular subfield of tag from Marc data.
	 * 
	 * @param r
	 * @param dataField
	 * @param subfield
	 * @return
	 */
	public String getField(Record r, String dataField) {
		return getField(r, dataField, StaticStrings.DB_SPLIT_STRING, StaticStrings.SPACE);
	}
	
	/**
	 * @param r
	 * @param dataFields
	 * @return
	 */
	public String getField(Record r, String[] dataFields) {
		return getField(r, dataFields, StaticStrings.SPACE);
	}
	
	public String getField(Record r, String[] dataFields, String subSeperater) {
		return getField(r, dataFields, StaticStrings.DB_SPLIT_STRING, subSeperater);
	}
	
	public String getField(Record r, String[] dataFields, String fieldSeperater, String subSeperater) {
		List<String> list = new UniqueList<String> ();
		String tmp;
		for (String field : dataFields) {
			tmp = getField(r, field, subSeperater);
			if(null != tmp){
				list.add(tmp);
			}				
		}		
		StringBuffer sb = mergeList(list, fieldSeperater);
		return trim(sb);
	}	
	
	
	public String append(Record r, String [] dataFields, String startWith){
		StringBuffer sb = new StringBuffer(startWith);
		String rest = getField(r,dataFields);
		if(rest !=null){
			sb.append(StaticStrings.DB_SPLIT_STRING).append(rest);			
		}
		return trim(sb);		
	}

	public String getOriginalNumber(Record r) {
		String id = r.getControlNumber();
		if(null == id) return null; 
		id = id.trim();
		if(id.length() <= 0) return null;
		return id;	
	}

	@SuppressWarnings("unchecked")
	public String getControlField(Record r, String dataField) {
		List<ControlField> l = (List<ControlField>) r.getControlFields();
		for (ControlField cf : l) {
			if (dataField.equals(cf.getTag())) {
				return cf.getData().trim();
			}
		}
		return null;
	}
	

	/**
	 * Get Field from particular subfield of tag from Marc data.
	 * @param r
	 * @param dataField
	 * @param subfield
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getField(Record r, String dataField, char subfield) {
		List<String> list = new UniqueList<String> ();
		String tmp;
		for (DataField df : (List<DataField>) r.getDataFields()) {
			if (dataField.equals(df.getTag())) {
				for (Subfield sf : (List<Subfield>) df.getSubfields()) {
					if (subfield == sf.getCode()) {
						tmp = sf.getData();
						if(null != tmp)
							list.add(tmp.trim());
					}

				}
			}
		}
		StringBuffer sb = mergeList(list, StaticStrings.DB_SPLIT_STRING);
		return trim(sb);
	}

	protected String trim(StringBuffer sb) {
		if (sb == null)
			return null;

		int start = 0, end = sb.length();
		while (start < end && trimList(sb.charAt(start))) {
			start++;
		}		
		while (start < end && trimList(sb.charAt(end -1))) {
			end--;
		}
		if (start >= end)
			return null;
		end = Math.min(end+1, sb.length());
		String s = sb.substring(start, end);
		if (StaticStrings.EMPLTY.endsWith(s))
			return null;
		return s;

	}
	
	protected boolean trimList(char c) {
		char[] trim = new char[] { ' ', '?', ':', ',', '\n' };
		for (char i : trim) {
			if (c == i)
				return true;
		}
		return false;
	}
	
	public StringBuffer mergeList(List<String> list, String fieldSeperater){
		StringBuffer sb = null;
		if(list.size() > 0){
			for (String field : list) {
				if (sb == null) {
					sb = new StringBuffer(field);
				} else {
					sb.append(fieldSeperater).append(field);
				}
			}
		}
		return sb;
	}
}
