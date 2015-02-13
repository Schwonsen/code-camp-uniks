package com.uni.cc_uniapp_2015.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Schrom on 10.02.2015.
 */
public class OnlineHelper
{

	static public boolean isOnline(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
		{
			return true;
		}
		return false;
	}
}
