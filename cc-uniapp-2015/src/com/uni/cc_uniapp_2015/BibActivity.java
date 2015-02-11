package com.uni.cc_uniapp_2015;

import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BibActivity extends ActionBarActivity
{
	LinearLayout bib_hopla_content;
	LinearLayout bib_bgp_content;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bib);
		bib_hopla_content = (LinearLayout) findViewById(R.id.bib_hopla_content);
		bib_bgp_content = (LinearLayout) findViewById(R.id.bib_bgp_content);

		// hide until its title is clicked
		bib_hopla_content.setVisibility(View.GONE);
		bib_bgp_content.setVisibility(View.GONE);

	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bib, menu);
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
	
	/**
	*
	* @param ctx
	* @param v
	*/
	public static void slide_down(Context ctx, View v){
		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
			if(a != null){
				a.reset();
				if(v != null){
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}
	
	public static void slide_up(Context ctx, View v){
		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
			if(a != null){
				a.reset();
				if(v != null){
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}
	
	/**
	* onClick handler
	*/
	public void toggle_contents(View v){
		if(v.getId() == R.id.bib_bgp) {
			if(bib_bgp_content.isShown()){
				slide_up(this, bib_bgp_content);
				bib_bgp_content.setVisibility(View.GONE);
			}
			else{
				bib_bgp_content.setVisibility(View.VISIBLE);
				slide_down(this, bib_bgp_content);
			}
		} else if (v.getId() == R.id.bib_hopla){
			if(bib_hopla_content.isShown()){
				slide_up(this, bib_hopla_content);
				bib_hopla_content.setVisibility(View.GONE);
			}
			else{
				bib_hopla_content.setVisibility(View.VISIBLE);
				slide_down(this, bib_hopla_content);
			}
		}
		
	}
}
