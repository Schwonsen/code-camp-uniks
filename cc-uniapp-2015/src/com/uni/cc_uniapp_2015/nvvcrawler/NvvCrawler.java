package com.uni.cc_uniapp_2015.nvvcrawler;


import java.util.regex.Matcher;
import java.util.regex.Matcher;


import java.util.regex.Matcher;

import com.uni.cc_uniapp_2015.activities.NvvStartActivity;

public class NvvCrawler extends Crawler
{
	private static final String PRE_URL = "http://auskunft.nvv.de/nvv/bin/jp/stboard.exe/"
			+ "dn?L=vs_rmv.vs_nvv&showStBoard=yes&input=";

	// crawler URL for "Murhardstraße Uni....."
	private static final String ID_URL_MURHARD = "2200057&time=";

	// crawler URL for "HoPla"
	private static final String ID_URL_HOPLA = "2200014&time=";

	// crawler URL for "Korbach Uni"
	private static final String ID_URL_KOR_UNI = "2200229&time=";

	private static final String POST_URL = "&start=yes";

	public NvvCrawler(Crawls listener)
	{
		super(listener);
	}

	@Override
	protected CrawlValue backgoundCrawl(Object[] params, CrawlValue crawlValue)
	{
		try
		{
			int i = 0;
			String time = (String) params[0];
			String regex = "tr class=\"depboard(.*?)</tr";
			String checkedUrl = "";

			checkedUrl = checkUrl(checkedUrl);

			Matcher matcher = getUrlMatcher(PRE_URL + checkedUrl + time
					+ POST_URL, regex);

			while (matcher.find())
			{
				/**
				 * (.*?) means match smallest number of possible characters,
				 * while making the rest of expression match
				 */
				String timeRegEx = "time\">(.*?)</td>";
				Matcher matchedTime = getStringMatcher(matcher.group(0),
						timeRegEx);
				matchedTime.find();
				String timer = matchedTime.group(0);
				timer = timer.substring(6, timer.length() - 5);

				String tramRegEx = "src=(.*?)>(.*?)</a>";
				Matcher matchedTram = getStringMatcher(matcher.group(0),
						tramRegEx);
				matchedTram.find();
				String tram = removeTagsEntitys(matchedTram.group(0))
						.replaceAll("s(.*?)>\\s", "");

				String directRegEx = "<strong>(.*?)>(.*?)</a>";
				Matcher matchedDircetion = getStringMatcher(matcher.group(0),
						directRegEx);
				matchedDircetion.find();
				String direction = removeTagsEntitys(matchedDircetion.group(0));

				crawlValue.appendValue(Integer.toString(i), timer);
				crawlValue.appendValue(Integer.toString(i), tram);
				crawlValue.appendValue(Integer.toString(i), direction);

				i++;
			}

			return crawlValue;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		;
		return null;
	}

	public String checkUrl(String checkedUrl)
	{
		if (NvvStartActivity.hoplaCheck)
		{
			checkedUrl = ID_URL_HOPLA;
		}
		else if (NvvStartActivity.williCheck)
		{
			checkedUrl = ID_URL_MURHARD;
		}
		else if (NvvStartActivity.korbaCheck)
		{
			checkedUrl = ID_URL_KOR_UNI;
		}
		return checkedUrl;
	}

}