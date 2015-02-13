package com.uni.cc_uniapp_2015.helper;

import com.uni.cc_uniapp_2015.R;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Schrom on 13.02.2015.
 */
public class AnimationHelper
{

	public static void test(Context context)
	{
		Button mButton = (Button) ((Activity) context)
				.findViewById(R.id.buttonSearchTram);
		mButton.setVisibility(View.GONE);
	}

	/**
	 * The enter animation scales the picture in from its previous thumbnail
	 * size/location, colorizing it in parallel. In parallel, the background of
	 * the activity is fading in. When the pictue is in place, the text
	 * description drops down.
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void runEnterAnimation(Context context, View mImageView,
			int animationDuration, double sAnimatorScale, float mWidthScale,
			float mHeightScale, int mLeftDelta, int mTopDelta, View mBackground)
	{
		final long duration = (long) (animationDuration * sAnimatorScale);
		final TimeInterpolator sDecelerator = new DecelerateInterpolator();
		// Set starting values for properties we're going to animate. These
		// values scale and position the full size version down to the thumbnail
		// size/location, from which we'll animate it back up

		mImageView.setPivotX(0);
		mImageView.setPivotY(0);
		mImageView.setScaleX(mWidthScale);
		mImageView.setScaleY(mHeightScale);
		mImageView.setTranslationX(mLeftDelta);
		mImageView.setTranslationY(mTopDelta);

		// Animate scale and translation to go from thumbnail to full size
		ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0,
				255);
		bgAnim.setDuration(duration);
		bgAnim.start();

		// Animate a color filter to take the image from grayscale to full
		// color.
		// This happens in parallel with the image scaling and moving into
		// place.
		ObjectAnimator colorizer = ObjectAnimator.ofFloat(context,
				"saturation", 0, 1);
		colorizer.setDuration(duration);
		colorizer.start();

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void runExitAnimation(final Runnable endAction,
			Context context, View mImageView, int animationDuration,
			double sAnimatorScale, float mWidthScale, float mHeightScale,
			int mLeftDelta, int mTopDelta, View mBackground,
			int mOriginalOrientation)
	{
		final long duration = (long) (animationDuration * sAnimatorScale);

		final boolean fadeOut;
		if (context.getResources().getConfiguration().orientation != mOriginalOrientation)
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

		// Animate image back to thumbnail size/location
		mImageView.animate().setDuration(duration).scaleX(mWidthScale)
				.scaleY(mHeightScale).translationX(mLeftDelta)
				.translationY(mTopDelta).withEndAction(endAction);
		if (fadeOut)
		{
			mImageView.animate().alpha(0);
		}
		// Fade out background
		ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0);
		bgAnim.setDuration(duration);
		bgAnim.start();

		// Animate a color filter to take the image back to grayscale,
		// in parallel with the image scaling and moving into place.
		ObjectAnimator colorizer = ObjectAnimator.ofFloat(context,
				"saturation", 1, 0);
		colorizer.setDuration(duration);
		colorizer.start();

	}
}
