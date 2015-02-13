package com.uni.cc_uniapp_2015.nvvcrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CrawlValue
{
	private HashMap<String, ArrayList<String>> valueMap;

	public CrawlValue() {
		valueMap = new HashMap<String, ArrayList<String>>();
	}

	public ArrayList<String> getValue(String key)
	{
		return this.valueMap.get(key);
	}

	public Set<String> getKeys()
	{
		return valueMap.keySet();
	}

	public void appendValue(String key, String value)
	{
		ArrayList<String> arrayList = this.valueMap.get(key);
		if (arrayList == null)
			arrayList = new ArrayList<String>();

		arrayList.add(value);
		this.valueMap.put(key, arrayList);
	}
}
