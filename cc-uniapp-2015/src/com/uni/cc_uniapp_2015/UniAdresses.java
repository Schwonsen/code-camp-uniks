package com.uni.cc_uniapp_2015;

import java.util.Locale;

import com.uni.cc_uniapp_2015.util.GPSFinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UniAdresses extends ActionBarActivity{
	
	//Adressen von UniKassel-Standorten
	final String ADRESS_HoPla = "Holländischer+Platz%2FUniversität";
	final String ADRESS_INGSchool = "Murhardstraße%2FUniversität";
	final String ADRESS_AVZ = "Heinrich-Plett-Straße+40";
	
	double LATITUDE;
	double LONGLATITUDE;
	

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_uni_adresses);
		
		//Beziehe Standort
		GPSFinder tracker = new GPSFinder(this);
		LATITUDE = tracker.getLatitude();
		LONGLATITUDE = tracker.getLongitude();
		
		


	}
	public void onButtonClick(View view){
		String uri = null;
		
		if(view.getId() == R.id.btn_toIngSchool){
			
			//Wenn Position abrufbar -> Übergebe Position an GoogleMaps
			if(LATITUDE != 0.0 | LONGLATITUDE != 0.0){
				uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%s", LATITUDE, LONGLATITUDE, ADRESS_INGSchool);
				//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				//startActivity(intent);
				Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
			
			//Ist Position nicht abrufbar -> Setze in Maps nur ein Label und lasse den Aktuellen Standort eingeben
			else{
				 //uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", ADRESS_HoPla);
				Toast.makeText(getApplicationContext(), "FEHLER: Schalten Sie Mobile Daten und GPS ein!",Toast.LENGTH_LONG).show();
				finish();
			}
			
		}
		else if(view.getId() == R.id.btn_toHoPla){
			
			//Toast.makeText(getApplicationContext(), (" "+LATITUDE),Toast.LENGTH_LONG).show();
			if(LATITUDE != 0.0 | LONGLATITUDE != 0.0){
				 uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%s", LATITUDE, LONGLATITUDE, ADRESS_HoPla);
				 Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
					startActivity(intent);
			}
			else{
				 //uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", ADRESS_HoPla);
				Toast.makeText(getApplicationContext(), "FEHLER: Schalten Sie Mobile Daten und GPS ein!",Toast.LENGTH_LONG).show();
				finish();
			}
			
		}
		else{
			
			//Toast.makeText(getApplicationContext(), " "+LATITUDE,Toast.LENGTH_LONG).show();
			if(LATITUDE != 0.0 | LONGLATITUDE != 0.0){
				uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%s", LATITUDE, LONGLATITUDE, ADRESS_AVZ);
				Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
			else{
				//uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", ADRESS_AVZ);
				Toast.makeText(getApplicationContext(), "FEHLER: Schalten Sie Mobile Daten und GPS ein!",Toast.LENGTH_LONG).show();
				finish();
			}
		}
		//Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		//startActivity(intent);
	}

}
