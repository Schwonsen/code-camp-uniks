package com.uni.cc_uniapp_2015.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageHelper
{

	public static SharedPreferences mSharedPrefs;
	public static SharedPreferences.Editor editor;

	private static void initStorageHelperIfNeeded(Context context)
	{
		if (mSharedPrefs == null)
		{
			mSharedPrefs = context.getSharedPreferences("storageHelper",
					Context.MODE_PRIVATE);
			editor = mSharedPrefs.edit();
		}
	}

	public static void removeData(Context context, String key)
	{
		initStorageHelperIfNeeded(context);
		editor.remove(key);
		editor.commit();
	}

	public static void storeData(Context context, String key, String data)
	{
		initStorageHelperIfNeeded(context);
		editor.putString(key, data);
		editor.commit();
	}

	public static String getData(Context context, String key)
	{
		initStorageHelperIfNeeded(context);
		return mSharedPrefs.getString(key, "");
	}
}
