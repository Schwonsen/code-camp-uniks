package com.uni.cc_uniapp_2015.activities;

import android.app.Activity;
import android.os.Bundle;

import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.gps.Navigation;

import android.view.View;

public class GpsStartActivity extends Activity {

	//Adressen von UniKassel-Standorten
	final String ADRESS_HoPla = "Holländischer+Platz%2FUniversität";
	final String ADRESS_INGSchool = "Murhardstraße%2FUniversität";
	final String ADRESS_AVZ = "Heinrich-Plett-Straße+40";

	double LATITUDE;
	double LONGLATITUDE;

	Navigation navigation;

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_uni_adresses);
		navigation = new Navigation(this);
	}
	public void onButtonClick(View view){
		if(view.getId() == R.id.btn_toIngSchool){
			this.navigation.navigateTo(ADRESS_INGSchool);	
		}
		else if(view.getId() == R.id.btn_toHoPla){

			this.navigation.navigateTo(ADRESS_HoPla);
		}
		else{

			this.navigation.navigateTo(ADRESS_AVZ);
		}
	}
}
