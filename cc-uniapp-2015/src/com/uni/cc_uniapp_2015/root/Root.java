package com.uni.cc_uniapp_2015.root;

import java.util.HashMap;

import com.uni.cc_uniapp_2015.crawler.CrawlValue;
import com.uni.cc_uniapp_2015.objects.Bib;

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
	
	/**
	 * Bibliothek Values
	 */
	public HashMap<String, Bib> bibsMap = new HashMap<String, Bib>();

	private Root()
	{
		//hopla
		String bibName = "Standort Holländischer Platz";
		String street = "Diagonale 10, 34127 Kassel";
		String tel = "+49 561 804-7711";
		String eMail = "info@bibliothek.uni-kassel.de";
		String fb = "Ingenieurwissenschaften, Technik\n Bereichsbibliothek 1\n\n Sprach- und Literaturwissenschaften\n"
				+ "Bereichsbibliothek 2\n\n Wirtschaft, Recht, Sport\n Bereichsbibliothek 3\n\n Geistes- und Gesellschaftswissenschaften\n"
				+ "Bereichsbibliothek 4/5\n";
		String services = "Bibliotheksgebäude:\n Mo bis Fr: 8 bis 23 Uhr\n Sa und So: 10 bis 21 Uhr\n\n Servicetheke:\n Mo bis Fr: 9 bis 18 Uhr\n Sa: 10 bis 13 Uhr\n\n"
				+ "Nutzerservice:\n Mo bis Fr: 9 bis 12 Uhr\n Di und Do: 13 bis 16 Uhr und nach Vereinbarung";

		bibsMap.put("1",new Bib(1, bibName, street, tel, eMail, fb, services));
		
		bibName = "Standort Brüder-Grimm-Platz";
		street = "Brüder-Grimm-Platz 4a, 34117 Kassel";
		tel = "+49 561 804-7337 (Information)\n +49 561 804-7318 (Ausleihe)";
		eMail = "bb6@bibliothek.uni-kassel.de";
		fb = "Landesbibliothek und\n Murhardsche Bibliothek der Stadt Kassel\n Bereichsbibliothek 6";
		services = "Brüder-Grimm-Platz:\n Mo bis Fr: 9 bis 18 Uhr\n Sa: 10 bis 13 Uhr\n";
		
		bibsMap.put("2", new Bib(2, bibName, street, tel, eMail, fb, services));
	}
	
	public Bib getBib(String bib) {
		return this.bibsMap.get(bib);
	}
	
	

}
