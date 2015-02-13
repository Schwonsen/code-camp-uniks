package com.uni.cc_uniapp_2015.helper;

/**
 * Created by Schrom on 31.01.2015.
 */
public class StringHelper
{

	public static String getCleanedString(String string)
	{
		String cleanedString = "";
		try
		{
			cleanedString = string.toLowerCase().replaceAll("[^a-z1-9\\_]", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return cleanedString;
	}

}
