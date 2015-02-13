package com.uni.cc_uniapp_2015.activities;

import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.gps.Navigation;
import com.uni.cc_uniapp_2015.helper.ImageHelper;

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
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class BibStartActivity extends Activity
{

	LinearLayout bib_hopla_content;
	LinearLayout bib_bgp_content;
	LinearLayout bib_wa_content;
	LinearLayout bib_kunst_content;
	LinearLayout bib_oberzwehren_content;
	Navigation navigation;

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
	private ScrollView mLayout;
	private int mOriginalOrientation;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bib_start);

		// animation part
		mImageView = (ImageView) findViewById(R.id.mImageView);

		mTextView = (TextView) findViewById(R.id.mTextView);

		mBackgroundLayout = (RelativeLayout) findViewById(R.id.mybackground);
		mLayout = (ScrollView) findViewById(R.id.mlayout);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_bib_start, menu);
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
	public static void slide_down(Context ctx, View v)
	{
		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
		if (a != null)
		{
			a.reset();
			if (v != null)
			{
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}

	public static void slide_up(Context ctx, View v)
	{
		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
		if (a != null)
		{
			a.reset();
			if (v != null)
			{
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}

	/**
	 * onClick handler
	 */
	public void toggle_contents(View v)
	{
		if (v.getId() == R.id.bib_bgp)
		{
			slideShow(bib_bgp_content);
		}
		else if (v.getId() == R.id.bib_hopla)
		{
			slideShow(bib_hopla_content);
		}
		else if (v.getId() == R.id.bib_kunst)
		{
			slideShow(bib_kunst_content);
		}
		else if (v.getId() == R.id.bib_wa)
		{
			slideShow(bib_wa_content);
		}
		else if (v.getId() == R.id.bib_oberzwehren)
		{
			slideShow(bib_oberzwehren_content);
		}

	}

	private void slideShow(LinearLayout layout)
	{
		if (layout.isShown())
		{
			slide_up(this, layout);
			layout.setVisibility(View.GONE);
		}
		else
		{
			layout.setVisibility(View.VISIBLE);
			slide_down(this, layout);
		}
	}

	public void navigateTo(View view)
	{
		if (view.getId() == R.id.navigateToHopla)
		{
			navigation.navigateTo("Diagonale+10,+34127+Kassel");
		}
		else if (view.getId() == R.id.navigateToBgp)
		{
			navigation.navigateTo("Brüder-Grimm-Platz+4A,+34117+Kassel");
		}
		else if (view.getId() == R.id.navigateToWA)
		{
			navigation.navigateTo("Wilhelmshöher+Allee+73,+34121+Kassel");
		}
		else if (view.getId() == R.id.navigateToKunst)
		{
			navigation.navigateTo("Menzelstraße+13,+34121+Kassel");
		}
		else if (view.getId() == R.id.navigateToOberzwehren)
		{
			navigation.navigateTo("Heinrich-Plett-Straße+40,+34132+Kassel");
		}

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

}