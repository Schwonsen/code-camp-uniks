package com.uni.cc_uniapp_2015.objects;

public class Bib
{
	private String bibName;
	private String street;
	private String tel;
	private String eMail;
	private String fb;
	private String services;
	private int key;

	public Bib(int key, String bibName, String street, String tel, String eMail, String fb, String services)
	{
		this.bibName = bibName;
		this.street = street;
		this.tel = tel;
		this.eMail = eMail;
		this.fb = fb;
		this.services = services;
		this.key = key;
	}

	public int getKey()
	{
		return key;
	}

	public void setKey(int key)
	{
		this.key = key;
	}

	public String getBibName()
	{
		return bibName;
	}

	public String getStreet()
	{
		return street;
	}

	public String getTel()
	{
		return tel;
	}

	public String geteMail()
	{
		return eMail;
	}

	public String getFb()
	{
		return fb;
	}

	public String getServices()
	{
		return services;
	}

	public void setBibName(String bibName)
	{
		this.bibName = bibName;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public void seteMail(String eMail)
	{
		this.eMail = eMail;
	}

	public void setFb(String fb)
	{
		this.fb = fb;
	}

	public void setServices(String services)
	{
		this.services = services;
	}
}
