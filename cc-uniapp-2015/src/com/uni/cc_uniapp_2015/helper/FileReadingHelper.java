package com.uni.cc_uniapp_2015.helper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReadingHelper
{

	public static String getStringFromFile(String filePath, String format,
			Context context)
	{

		try
		{
			filePath = filePath + "." + format;
			InputStream inputStream = context.getAssets().open(filePath);

			if (inputStream != null)
			{
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null)
				{
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				return stringBuilder.toString();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
