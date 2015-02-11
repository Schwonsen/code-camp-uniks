package com.uni.cc_uniapp_2015.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class GPSFinder extends Service implements LocationListener {

	private final Context mContext;

	Location location; 
	double latitude; 
	double longitude; 

	//Minimale Strecke, in der Änderungen erkannt werden = 10 meter
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; 

	//Updatezeit der Position = 1 sek
	private static final long MIN_TIME_BW_UPDATES = 100000;
	//1000 * 60 * 1; 

	//Definition eines Location Managers
	protected LocationManager locationManager;

	public GPSFinder(Context context) {
		this.mContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

			//Initialisiere Locationmanager
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER,
					MIN_TIME_BW_UPDATES,
					MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

			//Beziehe Informationen von GPS
			if (locationManager != null) {
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

				if (location != null) {
					latitude = location.getLatitude();
					longitude = location.getLongitude();
				}
			}

			if (location == null) {
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER,
						MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

				//Beziehe Informationen von Mobilen Daten
				if (locationManager != null) {
					location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	public double getLatitude(){
		if(location != null){
			latitude = location.getLatitude();
		}


		return latitude;
	}

	public double getLongitude(){
		if(location != null){
			longitude = location.getLongitude();
		}

		return longitude;
	}


	//UNGENUTZT!!
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
