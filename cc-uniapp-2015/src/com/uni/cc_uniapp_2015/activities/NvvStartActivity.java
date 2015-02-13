package com.uni.cc_uniapp_2015.activities;

import java.util.ArrayList;
import java.util.Calendar;

import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.helper.ImageHelper;
import com.uni.cc_uniapp_2015.helper.OnlineHelper;
import com.uni.cc_uniapp_2015.nvvcrawler.CrawlValue;
import com.uni.cc_uniapp_2015.nvvcrawler.Crawler;
import com.uni.cc_uniapp_2015.nvvcrawler.Crawls;
import com.uni.cc_uniapp_2015.nvvcrawler.NvvCrawler;
import com.uni.cc_uniapp_2015.nvvcrawler.Root;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NvvStartActivity extends Activity implements Crawls
{
	public static boolean williCheck;
	public static boolean hoplaCheck;
	public static boolean korbaCheck;

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
	private ColorMatrix colorizerMatrix = new ColorMatrix();
	private BitmapDrawable mBitmapDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nvv);

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

					return true;
				}
			});
		}
		// end animation part

		Calendar c = Calendar.getInstance();
		int hours = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);

		String time = "";
		time = setTimer(hours, minutes, time);

		createThis(hours, minutes);

		if (Root.getInsRoot().getNvvInfos() == null)
		{
			Object[] parameters = { time };
			NvvCrawler nvv = new NvvCrawler(this);
			if (!OnlineHelper.isOnline(this))
			{
				createInterface();
				Toast.makeText(getApplicationContext(),
						"Keine Internetverbindung.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				nvv.fetch(parameters);
			}
		}
		else
		{
			createInterface();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nvv, menu);
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

	public void createInterface()
	{
		if (!OnlineHelper.isOnline(this))
		{
			return;
		}

		CrawlValue crawVal = Root.getInsRoot().getNvvInfos();
		ListView listView = (ListView) findViewById(R.id.listViewNvv);
		ArrayAdapter<String> itemAdapter;
		ArrayList<String> listItems = new ArrayList<String>();

		for (String input : crawVal.getKeys())
		{
			String tramInfos = crawVal.getValue(input).get(0) + " "
					+ crawVal.getValue(input).get(1) + "\nRichtung:"
					+ crawVal.getValue(input).get(2);

			if (listItems.size() == 0)
			{
				listItems.add(tramInfos);
			}
			else
			{
				int currentTime = getTime(crawVal.getValue(input).get(0));
				int listPosition = 0;

				for (String listItem : listItems)
				{
					if (currentTime < getTime(listItem))
					{
						break;
					}
					listPosition++;
				}
				listItems.add(listPosition, tramInfos);
			}
		}
		itemAdapter = new ArrayAdapter<String>(this, R.layout.nvvlistitem,
				listItems);
		listView.setAdapter(itemAdapter);

		listView.setOnTouchListener(new View.OnTouchListener()
		{
			// Setting on Touch Listener for handling the touch inside
			// ScrollView
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// Disallow the touch request for parent scroll on touch of
				// child view
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

	}

	private int getTime(String string)
	{
		int hour = Integer.parseInt(string.substring(0, 2).trim());
		int minute = Integer.parseInt(string.substring(3, 5).trim());

		return hour * 60 + minute;
	}

	@Override
	public void onCrawlFinish(CrawlValue result)
	{
		Root.getInsRoot().setNvvInfos(result);
		createInterface();
	}

	public void createThis(int hours, int minutes)
	{
		// setContentView(R.layout.activity_nvv);
		TimePicker picker = (TimePicker) findViewById(R.id.timePickerNvv);
		picker.setIs24HourView(true);
		picker.setCurrentHour(hours);

		final NvvStartActivity nvvAvtivity = this;
		Button searchButton = (Button) findViewById(R.id.buttonSearchTram);

		searchButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				ListView listView = (ListView) findViewById(R.id.listViewNvv);
				listView.setAdapter(null);

				NvvCrawler nvvCrawl = new NvvCrawler(nvvAvtivity);
				TimePicker picker = (TimePicker) findViewById(R.id.timePickerNvv);
				int hours = picker.getCurrentHour();
				int minutes = picker.getCurrentMinute();

				String time = "";
				time = setTimer(hours, minutes, time);

				Object[] parameters = { time };

				nvvCrawl.fetch(parameters);
			}
		});

		RadioGroup nvvGroup = (RadioGroup) findViewById(R.id.radioNvv);
		nvvGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				if (checkedId == R.id.radioWilli)
				{
					williCheck = true;
					hoplaCheck = false;
					korbaCheck = false;
				}
				else if (checkedId == R.id.radioHopla)
				{
					williCheck = false;
					hoplaCheck = true;
					korbaCheck = false;
				}
				else if (checkedId == R.id.radioKorba)
				{
					williCheck = false;
					hoplaCheck = false;
					korbaCheck = true;
				}

				ListView listView = (ListView) findViewById(R.id.listViewNvv);
				listView.setAdapter(null);

				NvvCrawler nvvCrawl = new NvvCrawler(nvvAvtivity);
				TimePicker picker = (TimePicker) findViewById(R.id.timePickerNvv);
				int hours = picker.getCurrentHour();
				int minutes = picker.getCurrentMinute();

				String time = "";
				time = setTimer(hours, minutes, time);

				Object[] parameters = { time };

				nvvCrawl.fetch(parameters);
			}
		});
	}

	public String setTimer(int hour, int minute, String timer)
	{
		if (hour < 10)
		{
			timer += "0";
		}

		timer += hour + ":";

		if (minute < 10)
		{
			timer += "0";
		}

		return timer += minute;

	}

	public void test(CrawlValue crv)
	{
		Crawler asd = new NvvCrawler(this);
		Object[] parms = { "10:10" }; // <-- time
		asd.fetch(parms);
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

	/**
	 * The enter animation scales the picture in from its previous thumbnail
	 * size/location, colorizing it in parallel. In parallel, the background of
	 * the activity is fading in. When the pictue is in place, the text
	 * description drops down.
	 */
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
		Animation animationFadeIn = AnimationUtils.loadAnimation(this,
				R.anim.fadein);
		mLayout.startAnimation(animationFadeIn);

	}

	@Override
	public void onBackPressed()
	{
		mLayout.setVisibility(View.GONE);
		runExitAnimation(new Runnable()
		{
			public void run()
			{
				finish();
			}
		});
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

}