package com.uni.cc_uniapp_2015.util;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

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

			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER,
					MIN_TIME_BW_UPDATES,
					MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

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

	/*public void stopUsingGPS(){
		if(locationManager != null){
			locationManager.removeUpdates(GPSFinder.this);
		}      
	}
	*/

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

	//public boolean canGetLocation() {
	//return this.canGetLocation;
	//}

	/*public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
	 */


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
