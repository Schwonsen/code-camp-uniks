package com.uni.cc_uniapp_2015.util;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class Navigation {

	String address;
	double LATITUDE;
	double LONGLATITUDE;
	Activity activity;
	GPSFinder tracker;

	public Navigation(Activity activity){
		this.address = address;
		this.activity = activity;
		//Beziehe Standort
		tracker = new GPSFinder(activity);
		this.LATITUDE = tracker.getLatitude();
		this.LONGLATITUDE = tracker.getLongitude();	
	}

	public void navigateTo(String address){
		String uri = null;
		
		
		//Wenn Position über GPS oder network abrufbar --> übergebe Koordinaten
		if(tracker.gpsIsOn | tracker.networkIsOn){
			uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%s", LATITUDE, LONGLATITUDE, address);
			//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			//startActivity(intent);
			Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			this.activity.startActivity(intent);
		}

		//Ist Position nicht abrufbar -> Setze in Maps nur ein Label und lasse den Aktuellen Standort eingeben
		else{
			//uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", ADRESS_HoPla);

			Toast.makeText(activity, "FEHLER: Schalten Sie Mobile Daten und GPS ein!",Toast.LENGTH_LONG).show();
			activity.finish();


		}
	}

}
