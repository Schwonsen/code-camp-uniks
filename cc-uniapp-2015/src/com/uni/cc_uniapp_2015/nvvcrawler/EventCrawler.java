package com.uni.cc_uniapp_2015.nvvcrawler;

import java.util.regex.Matcher;

import android.app.Activity;

import android.os.Bundle;

public class EventCrawler extends Crawler {
	// Called when the activity is first created.
	
	public EventCrawler(Crawls listener) {
		super(listener);
	}
	
	@Override
	protected CrawlValue backgoundCrawl(Object[] params,
			CrawlValue crawValue) {
		int i = 0;
		
		//TODO
		
		Matcher urlMatcher = getUrlMatcher(
				"http://www.uni-kassel.de/uni/universitaet/pressekommunikation/veranstaltungen/listenansicht.html",
				"onmouseout(.*?)onmouseover");
		
		while (urlMatcher.find()) {
			Matcher event = getStringMatcher(urlMatcher.group(0),
					"title=\"(.*?)\"(.*?)\"(.*?)" +
					"width: 450px;\">(.*?), um (.*?) Uhr</div>");
			while (event.find()) {
				Matcher ort = getStringMatcher(event.group(0),"500px;\"><b>(.*?)</b></div>");
				ort.find();
				crawValue.appendValue("" + i, event.group(1));
				crawValue.appendValue("" + i, event.group(4));
				crawValue.appendValue("" + i, event.group(5));
				crawValue.appendValue("" + i, removeTagsEntitys(ort.group(0)).substring(9));
				i++;
			}
		}
		return crawValue;
	}
}