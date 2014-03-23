package org.capelin.core.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

public class TagBuilder {
	private static TagBuilder builder;

	public static TagBuilder getInstances() {
		if (builder == null) {
			builder = new TagBuilder();
		}
		return builder;
	}

	private Map<String, Map<Character, Integer>> tags = new HashMap<String, Map<Character, Integer>>();

	// control tags
	private Map<String, Integer> ctags = new HashMap<String, Integer>();

	private RecordUtil util = RecordUtil.getInstance();

	private Character defaultChar = new Character('a');

	@SuppressWarnings("unchecked")
	public void build(Record record) {
		String tId;
		Map<Character, Integer> field;
		char cId;
		Integer oldCount;
		String tmp;
		Integer i;
		for (ControlField cf : (List<ControlField>) record.getControlFields()) {
			tId = cf.getTag();
			if (tId == null)
				continue;
			i = ctags.get(tId);
			i = (null == i) ? 0 : i;
			i = Math.max(i, util.getControlField(record, tId).length());
			ctags.put(tId, i);
		}

		for (DataField df : (List<DataField>) record.getDataFields()) {
			tId = df.getTag();
			if (tId == null)
				continue;
			field = tags.get(tId);
			if (null == field) {
				field = new HashMap<Character, Integer>();
			}
			for (Subfield sf : (List<Subfield>) df.getSubfields()) {
				cId = sf.getCode();
				oldCount = field.get(cId);
				tmp = util.getField(record, tId, sf.getCode());
				if (tmp != null) {
					if (null == oldCount) {
						oldCount = new Integer(tmp.length());
					} else {
						oldCount = Math.max(oldCount, tmp.length());
					}

				}
				if (null != oldCount) {
					field.put(new Character(cId), oldCount);
				}
			}
			if (field != null) {
				tags.put(tId, field);
			}
		}
	}

	/**
	 * For csv record
	 * 
	 * @param record
	 */
	public void build(String[] record) {
		Integer n;
		String key;
		Map<Character, Integer> valueMap;
		for (int i = 0; i < record.length; i++) {
			key = Integer.toString(i);
			valueMap = (tags.get(key) == null) ? (new HashMap<Character, Integer>())
					: tags.get(key);
			n = valueMap.get(defaultChar);
			if(null == n){
				n = record[i].length();
			}else{
				n = Math.max(n, record[i].length());
			}
			valueMap.put(defaultChar, n);
			tags.put(key, valueMap);
		}
	}

	public void display(boolean flag) {
		String[] tkeys = new String[0], keys;
		// control fields.
		keys = ctags.keySet().toArray(tkeys);
		Arrays.sort(keys);

		for (String s : keys) {
			if (flag) {
				System.out.println(s + "(" + ctags.get(s) + ")");
			} else {
				System.out.println(s);
			}

		}
		if (flag) {
			System.out.println("---------------------");
		}
		// data fields.
		keys = tags.keySet().toArray(tkeys);
		Arrays.sort(keys);
		Map<Character, Integer> field;
		Character[] codes, t = new Character[0];
		int max, total;

		for (String s : keys) {
			total = 0;
			if (flag) {
				System.out.print(s + ":");
			} else {
				System.out.print(s);
			}

			field = tags.get(s);
			codes = field.keySet().toArray(t);
			Arrays.sort(codes);

			for (Character c : codes) {
				max = field.get(c);
				total += max;
				if (flag) {
					System.out.print("\t" + c + " (" + max + ")");
				} else {
					System.out.print("," + c);
				}

			}
			if (flag) {
				System.out.println("\t\tTotal (" + total + ")");
			} else {
				System.out.println();
			}
		}
	}
}
