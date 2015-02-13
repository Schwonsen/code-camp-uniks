package com.uni.cc_uniapp_2015.activities;

import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.gps.Navigation;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BibStartActivity extends Activity
{
	LinearLayout bib_hopla_content;
	LinearLayout bib_bgp_content;
	LinearLayout bib_wa_content;
	LinearLayout bib_kunst_content;
	LinearLayout bib_oberzwehren_content;
	Navigation navigation;
	ImageView bibImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybibview);

		bibImageView = (ImageView) findViewById(R.id.bibImageView);
		bib_hopla_content = (LinearLayout) findViewById(R.id.bib_hopla_content);
		bib_bgp_content = (LinearLayout) findViewById(R.id.bib_bgp_content);
		bib_wa_content = (LinearLayout) findViewById(R.id.bib_wa_content);
		bib_kunst_content = (LinearLayout) findViewById(R.id.bib_kunst_content);
		bib_oberzwehren_content = (LinearLayout) findViewById(R.id.bib_oberzwehren_content);

		// hide until its title is clicked
		bib_hopla_content.setVisibility(View.GONE);
		bib_bgp_content.setVisibility(View.GONE);
		bib_kunst_content.setVisibility(View.GONE);
		bib_oberzwehren_content.setVisibility(View.GONE);
		bib_wa_content.setVisibility(View.GONE);
		navigation = new Navigation(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
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
			slideShow(bib_bgp_content);
		} 
		else if (v.getId() == R.id.bib_hopla){
			slideShow(bib_hopla_content);
		}
		else if (v.getId() == R.id.bib_kunst){
			slideShow(bib_kunst_content);
		}
		else if (v.getId() == R.id.bib_wa){
			slideShow(bib_wa_content);
		}
		else if (v.getId() == R.id.bib_oberzwehren){
			slideShow(bib_oberzwehren_content);
		}

	}
	private void slideShow(LinearLayout layout) {
		if(layout.isShown()){
			slide_up(this, layout);
			layout.setVisibility(View.GONE);
		}
		else{
			layout.setVisibility(View.VISIBLE);
			slide_down(this, layout);
		}
	}

	public void navigateTo(View view){
		if(view.getId() == R.id.navigateToHopla) {
			navigation.navigateTo("Diagonale+10,+34127+Kassel");
		} 
		else if(view.getId() == R.id.navigateToBgp) {
			navigation.navigateTo("Br�der-Grimm-Platz+4A,+34117+Kassel");
		}
		else if(view.getId() == R.id.navigateToWA) {
			navigation.navigateTo("Wilhelmsh�her+Allee+73,+34121+Kassel");
		}
		else if(view.getId() == R.id.navigateToKunst) {
			navigation.navigateTo("Menzelstra�e+13,+34121+Kassel");
		}
		else if(view.getId() == R.id.navigateToOberzwehren) {
			navigation.navigateTo("Heinrich-Plett-Stra�e+40,+34132+Kassel");
		}
	}
}