package com.uni.cc_uniapp_2015.root;

import com.uni.cc_uniapp_2015.crawler.CrawlValue;

public class Root
{
	private static Root instance = null;
	
	public static Root getInsRoot()
	{
		if(instance == null)
		{
			instance = new Root();
		}
		return instance;
	}
	
	private CrawlValue nvvInfos;
	
	public CrawlValue getNvvInfos()
	{
		return nvvInfos;
	}
	
	public void setNvvInfos(CrawlValue nvvInfo)
	{
		this.nvvInfos = nvvInfo;
	}

}
