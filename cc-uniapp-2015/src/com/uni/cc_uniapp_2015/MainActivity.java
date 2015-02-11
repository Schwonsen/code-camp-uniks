package com.uni.cc_uniapp_2015;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.uni.cc_uniapp_2015.modell.Canteen;
import com.uni.cc_uniapp_2015.util.CanteenMenuParser;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


	public static boolean online;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button parseBtn = (Button) findViewById(R.id.btn_toIngSchool);
		 online = isOnline();

	 
	        parseBtn.setOnClickListener(new OnClickListener() {
	            
				@Override
				public void onClick(View v) {

					CanteenMenuParser parserTask =  new CanteenMenuParser();
					
				 	try {
						String test = parserTask.execute(new String[]{Canteen.K10_URL}).get();
						Log.d("TEST", test.length() + "");
		
						Log.d("TEST", test.substring(0, 2501));
						Log.d("TEST", test.substring(2500, test.length()));
		
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	          });
	        
	        //Intent intent = new Intent(this, NvvActivity.class);
			//startActivity(intent);
			
	    }

	       
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onButtonClick(View view){
		if(view.getId() == R.id.AdressButton){
			//Toast.makeText(getApplicationContext(), "HALLO WELT",Toast.LENGTH_LONG).show();
			startActivity(new Intent(this, UniAdresses.class));
		}
		else if(view.getId() == R.id.btn_toNVVPlan){
			Toast.makeText(getApplicationContext(), "HALLO WELT",Toast.LENGTH_LONG).show();
			startActivity(new Intent(this,NvvActivity.class));
		}
		
		else if(view.getId() == R.id.btn_share){
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			
			intent.putExtra(android.content.Intent.EXTRA_TEXT, "Ich nutze die cc-uniapp-2015!");
			startActivity(intent);
		}
		
	}
	public boolean isOnline()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
		{
			return true;
		}
		return false;
	}

}
