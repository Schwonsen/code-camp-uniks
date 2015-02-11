package com.uni.cc_uniapp_2015;

import java.util.Locale;

import com.uni.cc_uniapp_2015.util.GPSFinder;
import com.uni.cc_uniapp_2015.util.Navigation;

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
	
	Navigation navigation;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_uni_adresses);
		navigation = new Navigation(this);
	}
	public void onButtonClick(View view){
		String uri = null;
		
		if(view.getId() == R.id.btn_toIngSchool){
			this.navigation.navigateTo(ADRESS_INGSchool);	
		}
		else if(view.getId() == R.id.btn_toHoPla){
			
			this.navigation.navigateTo(ADRESS_HoPla);
		
		}
		else{
			
			this.navigation.navigateTo(ADRESS_AVZ);
		}
		//Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		//startActivity(intent);
	}

}
