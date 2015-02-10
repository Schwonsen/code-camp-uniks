package com.uni.cc_uniapp_2015;

import java.util.concurrent.ExecutionException;

import com.uni.cc_uniapp_2015.activitys.NvvActivity;

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

public class MainActivity extends ActionBarActivity implements
		View.OnClickListener
{
	
	public static boolean online;

	final static String ZENTRAL_MENZA_URL = "http://www.studentenwerk-kassel.de/188.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        online = isOnline();
        
        Button parseBtn = (Button) findViewById(R.id.button1);
        parseBtn.setOnClickListener(new OnClickListener() {
            
			@Override
			public void onClick(View v) {

				CanteenMenuParserTask parserTask =  new CanteenMenuParserTask();
				
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
        /*
        Intent intent = new Intent(this, NvvActivity.class);
		startActivity(intent);
		*/
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
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

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		
	}
}
