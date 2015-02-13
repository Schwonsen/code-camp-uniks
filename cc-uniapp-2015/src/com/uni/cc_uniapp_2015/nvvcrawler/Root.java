package com.uni.cc_uniapp_2015.nvvcrawler;

public class Root
{
	private static Root instance = null;

	public static Root getInsRoot()
	{
		if (instance == null)
		{
			instance = new Root();
		}
		return instance;
	}

	/**
	 * NVV Values
	 */
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
