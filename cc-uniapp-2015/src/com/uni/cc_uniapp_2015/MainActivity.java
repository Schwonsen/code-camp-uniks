package com.uni.cc_uniapp_2015;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;


public class MainActivity extends ActionBarActivity {
	
	final static String ZENTRAL_MENZA_URL = "http://www.studentenwerk-kassel.de/188.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button parseBtn = (Button) findViewById(R.id.button1);
        parseBtn.setOnClickListener(new OnClickListener() {
            
			@Override
			public void onClick(View v) {
			
				
				URLParser parserTask =  new URLParser();
				
				parserTask.execute(new String[]{"http://www.studentenwerk-kassel.de/188.html"});
				
				
			}
          });
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
}
