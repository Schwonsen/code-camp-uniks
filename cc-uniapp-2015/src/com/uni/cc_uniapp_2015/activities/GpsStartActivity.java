package com.uni.cc_uniapp_2015.activities;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import java.util.Locale;

import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.gps.GPSFinder;
import com.uni.cc_uniapp_2015.helper.ImageHelper;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GpsStartActivity extends Activity
{

	// Adressen von UniKassel-Standorten
	final String ADRESS_HoPla = "Holländischer+Platz%2FUniversität";
	final String ADRESS_INGSchool = "Murhardstraße%2FUniversität";
	final String ADRESS_AVZ = "Heinrich-Plett-Straße+40";

	double latitude;
	double longitude;

	private ColorMatrix colorizerMatrix = new ColorMatrix();
	private BitmapDrawable mBitmapDrawable;
	ImageView mImageView;
	TextView mTextView;
	int mLeftDelta;
	int mTopDelta;
	private static final int ANIM_DURATION = 500;
	float mWidthScale;
	float mHeightScale;
	private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
	private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
	ColorDrawable mBackground;
	private RelativeLayout mBackgroundLayout;
	private LinearLayout mLayout;
	private int mOriginalOrientation;
	GPSFinder tracker;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps_start);

		// animation part
		mImageView = (ImageView) findViewById(R.id.mImageView);

		mTextView = (TextView) findViewById(R.id.mTextView);

		mBackgroundLayout = (RelativeLayout) findViewById(R.id.mybackground);
		mLayout = (LinearLayout) findViewById(R.id.mlayout);
		mBackground = new ColorDrawable(Color.parseColor("#ffffff"));
		mBackgroundLayout.setBackgroundDrawable(mBackground);
		Bundle bundle = getIntent().getExtras();
		Bitmap bitmap = ImageHelper.getBitmapFromFolder(this, "drawable",
				bundle.getString(LauncherActivity.PACKAGENAME + ".imagename"));
		mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
		Log.d("",
				"imageName from bundle: "
						+ bundle.getString(LauncherActivity.PACKAGENAME
								+ ".imagename"));
		// mImageView.setBackgroundDrawable(ImageHelper.getBitmapDrawableFromFolder(this,
		// "drawable",
		// bundle.getString(LauncherActivity.PACKAGENAME + ".imagename")));
		mImageView.setBackgroundDrawable(mBitmapDrawable);
		String description = bundle.getString(LauncherActivity.PACKAGENAME
				+ ".description");
		final int thumbnailTop = bundle.getInt(LauncherActivity.PACKAGENAME
				+ ".top");
		final int thumbnailLeft = bundle.getInt(LauncherActivity.PACKAGENAME
				+ ".left");
		final int thumbnailWidth = bundle.getInt(LauncherActivity.PACKAGENAME
				+ ".width");
		final int thumbnailHeight = bundle.getInt(LauncherActivity.PACKAGENAME
				+ ".height");
		mOriginalOrientation = bundle.getInt(LauncherActivity.PACKAGENAME
				+ ".orientation");

		if (savedInstanceState == null)
		{
			ViewTreeObserver observer = mImageView.getViewTreeObserver();
			observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
			{

				@Override
				public boolean onPreDraw()
				{
					mImageView.getViewTreeObserver().removeOnPreDrawListener(
							this);

					// Figure out where the thumbnail and full size versions
					// are, relative
					// to the screen and each other
					int[] screenLocation = new int[2];
					mImageView.getLocationOnScreen(screenLocation);
					mLeftDelta = thumbnailLeft - screenLocation[0];
					mTopDelta = thumbnailTop - screenLocation[1];

					// Scale factors to make the large version the same size as
					// the thumbnail
					mWidthScale = (float) thumbnailWidth
							/ mImageView.getWidth();
					mHeightScale = (float) thumbnailHeight
							/ mImageView.getHeight();

					runEnterAnimation();
					// AnimationHelper.runEnterAnimation(MensaStartActivity.this,
					// mImageView, ANIM_DURATION, 1, mWidthScale, mHeightScale,
					// mLeftDelta, mTopDelta, mBackgroundLayout);

					return true;
				}
			});
		}
		// end animation part

		// Beziehe Standort
		tracker = new GPSFinder(this);
		latitude = tracker.getLatitude();
		longitude = tracker.getLongitude();

		Animation animationFadeIn = AnimationUtils.loadAnimation(this,
				R.anim.fadein);
		mLayout.startAnimation(animationFadeIn);
		Handler handler2 = new Handler();
		handler2.postDelayed(new Runnable()
		{
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run()
			{
				mLayout.bringToFront();
			}
		}, 100);

	}

	public void onButtonClick(View view)
	{
		String uri = null;

		if (view.getId() == R.id.btn_toIngSchool)
		{

			// Wenn Position abrufbar -> Übergebe Position an GoogleMaps
			if (latitude != 0.0 | longitude != 0.0)
			{
				uri = String.format(Locale.ENGLISH,
						"http://maps.google.com/maps?saddr=%f,%f&daddr=%s",
						latitude, longitude, ADRESS_INGSchool);
				// Intent intent = new Intent(Intent.ACTION_VIEW,
				// Uri.parse(uri));
				// startActivity(intent);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}

			// Ist Position nicht abrufbar -> Setze in Maps nur ein Label und
			// lasse den Aktuellen Standort eingeben
			else
			{
				updateGpsData();
				Handler handler2 = new Handler();
				handler2.postDelayed(new Runnable()
				{
					@TargetApi(Build.VERSION_CODES.HONEYCOMB)
					@Override
					public void run()
					{
						if (latitude != 0.0 | longitude != 0.0)
						{
							String mUri = String
									.format(Locale.ENGLISH,
											"http://maps.google.com/maps?saddr=%f,%f&daddr=%s",
											latitude, longitude, ADRESS_HoPla);
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri
									.parse(mUri));
							startActivity(intent);
						}
						else
						{
							Toast.makeText(
									getApplicationContext(),
									"FEHLER: Standort muss erst ermittelt werden.(Stellen sie sicher ,dass Mobile Daten und GPS eingeschaltet sind!",
									Toast.LENGTH_SHORT).show();
						}
					}
				}, 1000);
			}

		}
		else if (view.getId() == R.id.btn_toHoPla)
		{

			// Toast.makeText(getApplicationContext(),
			// (" "+LATITUDE),Toast.LENGTH_LONG).show();
			if (latitude != 0.0 | longitude != 0.0)
			{
				uri = String.format(Locale.ENGLISH,
						"http://maps.google.com/maps?saddr=%f,%f&daddr=%s",
						latitude, longitude, ADRESS_HoPla);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
			else
			{
				updateGpsData();
				Handler handler2 = new Handler();
				handler2.postDelayed(new Runnable()
				{
					@TargetApi(Build.VERSION_CODES.HONEYCOMB)
					@Override
					public void run()
					{
						if (latitude != 0.0 | longitude != 0.0)
						{
							String mUri = String
									.format(Locale.ENGLISH,
											"http://maps.google.com/maps?saddr=%f,%f&daddr=%s",
											latitude, longitude, ADRESS_HoPla);
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri
									.parse(mUri));
							startActivity(intent);
						}
						else
						{
							Toast.makeText(
									getApplicationContext(),
									"FEHLER: Standort muss erst ermittelt werden.(Stellen sie sicher ,dass Mobile Daten und GPS eingeschaltet sind!",
									Toast.LENGTH_SHORT).show();
						}
					}
				}, 1000);
			}

		}
		else
		{

			// Toast.makeText(getApplicationContext(),
			// " "+LATITUDE,Toast.LENGTH_LONG).show();
			if (latitude != 0.0 | longitude != 0.0)
			{
				uri = String.format(Locale.ENGLISH,
						"http://maps.google.com/maps?saddr=%f,%f&daddr=%s",
						latitude, longitude, ADRESS_AVZ);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
			else
			{
				updateGpsData();
				Handler handler2 = new Handler();
				handler2.postDelayed(new Runnable()
				{
					@TargetApi(Build.VERSION_CODES.HONEYCOMB)
					@Override
					public void run()
					{
						if (latitude != 0.0 | longitude != 0.0)
						{
							String mUri = String
									.format(Locale.ENGLISH,
											"http://maps.google.com/maps?saddr=%f,%f&daddr=%s",
											latitude, longitude, ADRESS_HoPla);
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri
									.parse(mUri));
							startActivity(intent);
						}
						else
						{
							Toast.makeText(
									getApplicationContext(),
									"FEHLER: Standort muss erst ermittelt werden.(Stellen sie sicher ,dass Mobile Daten und GPS eingeschaltet sind!",
									Toast.LENGTH_SHORT).show();
						}
					}
				}, 1000);
			}
		}
		// Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		// startActivity(intent);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);

		Handler handler2 = new Handler();
		handler2.postDelayed(new Runnable()
		{
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run()
			{
				updateGpsData();
			}
		}, 500);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void runEnterAnimation()
	{
		final long duration = (long) (ANIM_DURATION * LauncherActivity.sAnimatorScale);

		// Set starting values for properties we're going to animate. These
		// values scale and position the full size version down to the thumbnail
		// size/location, from which we'll animate it back up
		mImageView.setPivotX(0);
		mImageView.setPivotY(0);
		mImageView.setScaleX(mWidthScale);
		mImageView.setScaleY(mHeightScale);
		mImageView.setTranslationX(mLeftDelta);
		mImageView.setTranslationY(mTopDelta);

		// We'll fade the text in later
		mTextView.setAlpha(0);

		// Animate scale and translation to go from thumbnail to full size
		mImageView.animate().setDuration(duration).scaleX(1).scaleY(1)
				.translationX(0).translationY(0).setInterpolator(sDecelerator)
				.withEndAction(new Runnable()
				{
					public void run()
					{
						// Animate the description in after the image animation
						// is done. Slide and fade the text in from underneath
						// the picture.
						mTextView.setTranslationY(-mTextView.getHeight());
						mTextView.animate().setDuration(duration / 2)
								.translationY(0).alpha(1)
								.setInterpolator(sDecelerator);
					}
				});

		// Fade in the black background
		ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0,
				255);
		bgAnim.setDuration(duration);
		bgAnim.start();

		// Animate a color filter to take the image from grayscale to full
		// color.
		// This happens in parallel with the image scaling and moving into
		// place.
		ObjectAnimator colorizer = ObjectAnimator.ofFloat(this, "saturation",
				0, 1);
		colorizer.setDuration(duration);
		colorizer.start();

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void runExitAnimation(final Runnable endAction)
	{
		final long duration = (long) (ANIM_DURATION * LauncherActivity.sAnimatorScale);

		// No need to set initial values for the reverse animation; the image is
		// at the
		// starting size/location that we want to start from. Just animate to
		// the
		// thumbnail size/location that we retrieved earlier

		// Caveat: configuration change invalidates thumbnail positions; just
		// animate
		// the scale around the center. Also, fade it out since it won't match
		// up with
		// whatever's actually in the center
		final boolean fadeOut;
		if (getResources().getConfiguration().orientation != mOriginalOrientation)
		{
			mImageView.setPivotX(mImageView.getWidth() / 2);
			mImageView.setPivotY(mImageView.getHeight() / 2);
			mLeftDelta = 0;
			mTopDelta = 0;
			fadeOut = true;
		}
		else
		{
			fadeOut = false;
		}

		// First, slide/fade text out of the way
		mTextView.animate().translationY(-mTextView.getHeight()).alpha(0)
				.setDuration(duration / 2).setInterpolator(sAccelerator)
				.withEndAction(new Runnable()
				{
					public void run()
					{
						// Animate image back to thumbnail size/location
						mImageView.animate().setDuration(duration)
								.scaleX(mWidthScale).scaleY(mHeightScale)
								.translationX(mLeftDelta)
								.translationY(mTopDelta)
								.withEndAction(endAction);
						if (fadeOut)
						{
							mImageView.animate().alpha(0);
						}
						// Fade out background
						ObjectAnimator bgAnim = ObjectAnimator.ofInt(
								mBackground, "alpha", 0);
						bgAnim.setDuration(duration);
						bgAnim.start();

						// Animate a color filter to take the image back to
						// grayscale,
						// in parallel with the image scaling and moving into
						// place.
						ObjectAnimator colorizer = ObjectAnimator.ofFloat(this,
								"saturation", 1, 0);
						colorizer.setDuration(duration);
						colorizer.start();
					}
				});

	}

	@Override
	public void onBackPressed()
	{
		mLayout.setVisibility(View.GONE);
		runExitAnimation(new Runnable()
		{
			public void run()
			{
				// *Now* go ahead and exit the activity
				finish();
			}
		});
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void finish()
	{
		super.finish();

		// override transitions to skip the standard window animations
		overridePendingTransition(0, 0);
	}

	public void setSaturation(float value)
	{
		colorizerMatrix.setSaturation(value);
		ColorMatrixColorFilter colorizerFilter = new ColorMatrixColorFilter(
				colorizerMatrix);
		mBitmapDrawable.setColorFilter(colorizerFilter);
	}

	public void updateGpsData()
	{
		tracker = new GPSFinder(this);
		latitude = tracker.getLatitude();
		longitude = tracker.getLongitude();
	}
}
