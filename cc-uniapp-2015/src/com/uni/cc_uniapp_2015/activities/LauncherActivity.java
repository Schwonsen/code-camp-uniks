package com.uni.cc_uniapp_2015.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.helper.ImageHelper;
import com.uni.cc_uniapp_2015.helper.StringHelper;

public class LauncherActivity extends ActionBarActivity implements
		SensorEventListener
{

	public static String PACKAGENAME = "app.campus.camp.code.campusapp.activities";
	List<ImageView> listOfImageViews;

	static float sAnimatorScale = 1;

	private SensorManager sensorManager;

	ImageView myBackgroundImageView;

	HorizontalScrollView myBackgroundImageScrollView;

	boolean scrollable = true;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);

		listOfImageViews = new ArrayList<ImageView>();
		ImageView mensaButton = (ImageView) findViewById(R.id.mensaButton);
		ImageView nvvButton = (ImageView) findViewById(R.id.nvvButton);
		ImageView gpsButton = (ImageView) findViewById(R.id.gpsButton);
		ImageView bibButton = (ImageView) findViewById(R.id.bibButton);
		mensaButton.setTag("MensaStartActivity");
		nvvButton.setTag("NvvStartActivity");
		gpsButton.setTag("GpsStartActivity");
		bibButton.setTag("BibStartActivity");
		listOfImageViews.add(mensaButton);
		listOfImageViews.add(nvvButton);
		listOfImageViews.add(gpsButton);
		listOfImageViews.add(bibButton);
		for (ImageView singleImageView : listOfImageViews)
		{
			String singleImageName = StringHelper
					.getCleanedString(singleImageView.getTag().toString());
			Log.d("tag", "singleImageName " + singleImageName);
			Drawable singleDrawable = ImageHelper
					.getGrayFilteredBitmapDrawableFromFolder(this, "drawable",
							singleImageName + "_round");
			singleImageView.setBackgroundDrawable(singleDrawable);
			singleImageView.setOnClickListener(imageClickListener);

			((LinearLayout) singleImageView.getParent())
					.setOnClickListener(new MyLovelyOnClickListener(
							singleImageView));
		}

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	public class MyLovelyOnClickListener implements View.OnClickListener
	{

		View myLovelyView;

		public MyLovelyOnClickListener(View view)
		{
			this.myLovelyView = view;
		}

		@Override
		public void onClick(View v)
		{
			myLovelyView.performClick();
		}

	};

	private View.OnClickListener imageClickListener = new View.OnClickListener()
	{

		@Override
		public void onClick(View view)
		{
			try
			{
				int[] screenLocation = new int[2];
				view.getLocationOnScreen(screenLocation);

				Drawable image = view.getBackground();
				int identifier = getResources().getIdentifier("pic1",
						"drawable", "android.demo");

				String singleImageName = StringHelper.getCleanedString(view
						.getTag().toString());
				String className = view.getTag().toString();
				Class mClass = Class.forName(PACKAGENAME + "." + className);

				Intent subActivity = new Intent(getApplicationContext(), mClass);
				int orientation = getResources().getConfiguration().orientation;
				// Log.d("a",subActivity + "");
				subActivity.putExtra(PACKAGENAME + ".orientation", orientation)
						.putExtra(PACKAGENAME + ".imagename", singleImageName)
						.putExtra(PACKAGENAME + ".left", screenLocation[0])
						.putExtra(PACKAGENAME + ".top", screenLocation[1])
						.putExtra(PACKAGENAME + ".width", view.getWidth())
						.putExtra(PACKAGENAME + ".height", view.getHeight())
						.putExtra(PACKAGENAME + ".description", "description");
				startActivity(subActivity);

				if (view.getId() == R.id.nvvButton)
				{
					NvvStartActivity.williCheck = true;
				}

				// Override transitions: we don't want the normal window
				// animation in addition
				// to our custom one
				overridePendingTransition(0, 0);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_launcher, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}
		else if (id == R.id.action_share)
		{
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");

			String downloadLink = "https://www.dropbox.com/s/os5o0iubsu6n02f/ic_cc_launcher.png?dl=0";

			intent.putExtra(android.content.Intent.EXTRA_TEXT,
					"Ich nutze die cc-uniapp-2015! Lade sie dir auch herunter! "
							+ downloadLink);
			startActivity(intent);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{

		// myBackgroundImageView.scrollBy(10, 123);
		// Toast.makeText(this,""+getResources().getConfiguration().orientation,Toast.LENGTH_SHORT).show();
	}

	private void getAccelerometer(SensorEvent event)
	{

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// register this class as a listener for the orientation and
		// accelerometer sensors
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause()
	{
		// unregister listener
		super.onPause();
		sensorManager.unregisterListener(this);
	}

}
