package com.uni.cc_uniapp_2015.activities;


import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.helper.ImageHelper;
import com.uni.cc_uniapp_2015.mensadata.MensaDataManager;
import com.uni.cc_uniapp_2015.tabs.SlidingTabsColorsFragment;

import animation.ShadowLayout;


/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MensaStartActivity extends FragmentActivity {

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    private ColorMatrix colorizerMatrix = new ColorMatrix();
    private BitmapDrawable mBitmapDrawable;

    Button moButton;
    Button diButton;
    Button miButton;
    Button doButton;
    Button frButton;

    public static List<Button> buttonViewList;

    public static TextView myFakeTextView;
    public static View clickedView;

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
    private ShadowLayout mShadowLayout;
    private RelativeLayout mBackgroundLayout;
    private LinearLayout mLayout;
    private int mOriginalOrientation;

    private View.OnClickListener myDayOnClickListener = new View.OnClickListener() {
        public void onClick(View view) {

            if(myFakeTextView!=null){
                clickedView=view;
                myFakeTextView.setText(myFakeTextView.getText().toString());
            }




        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensa_activity);

        //animation part
        mImageView = (ImageView) findViewById(R.id.mImageView);

        //animation part
        mTextView = (TextView) findViewById(R.id.mTextView);

        mBackgroundLayout = (RelativeLayout) findViewById(R.id.mybackground);
        mLayout = (LinearLayout) findViewById(R.id.mlayout);
        mBackground = new ColorDrawable(Color.parseColor("#ffffff"));
        mBackgroundLayout.setBackgroundDrawable(mBackground);
        Bundle bundle = getIntent().getExtras();
        Bitmap bitmap = ImageHelper.getBitmapFromFolder(this, "drawable",
                bundle.getString(LauncherActivity.PACKAGENAME + ".imagename"));
        mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        Log.d("","imageName from bundle: "+bundle.getString(LauncherActivity.PACKAGENAME + ".imagename"));
        //mImageView.setBackgroundDrawable(ImageHelper.getBitmapDrawableFromFolder(this, "drawable",
        //       bundle.getString(LauncherActivity.PACKAGENAME + ".imagename")));
        mImageView.setBackgroundDrawable(mBitmapDrawable);
        String description = bundle.getString(LauncherActivity.PACKAGENAME + ".description");
        final int thumbnailTop = bundle.getInt(LauncherActivity.PACKAGENAME + ".top");
        final int thumbnailLeft = bundle.getInt(LauncherActivity.PACKAGENAME + ".left");
        final int thumbnailWidth = bundle.getInt(LauncherActivity.PACKAGENAME + ".width");
        final int thumbnailHeight = bundle.getInt(LauncherActivity.PACKAGENAME + ".height");
        mOriginalOrientation = bundle.getInt(LauncherActivity.PACKAGENAME + ".orientation");

        mShadowLayout = (ShadowLayout) findViewById(R.id.shadowLayout);

        if (savedInstanceState == null) {
            ViewTreeObserver observer = mImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    mImageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / mImageView.getWidth();
                    mHeightScale = (float) thumbnailHeight / mImageView.getHeight();

                    runEnterAnimation();

                    return true;
                }
            });
        }
        //end animation part

        myFakeTextView = new TextView(this);

        MensaDataManager.initialize(this);
        MensaDataManager.crawlData(this);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsColorsFragment fragment = new SlidingTabsColorsFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        // Toast.makeText(this,"aaa",Toast.LENGTH_SHORT).show();
        moButton = (Button) this.findViewById(R.id.mo_button);
        diButton = (Button) this.findViewById(R.id.di_button);
        miButton = (Button) this.findViewById(R.id.mi_button);
        doButton = (Button) this.findViewById(R.id.do_button);
        frButton = (Button) this.findViewById(R.id.fr_button);



        buttonViewList= new ArrayList<Button>();
        buttonViewList.add(moButton);
        buttonViewList.add(diButton);
        buttonViewList.add(miButton);
        buttonViewList.add(doButton);
        buttonViewList.add(frButton);

        for (Button button : buttonViewList){
            button.setOnClickListener(myDayOnClickListener);
        }
        clickedView = moButton;
        moButton.post(new Runnable(){
            @Override
            public void run() {
                mLayout.setVisibility(View.VISIBLE);
                Animation animationFadeIn = AnimationUtils.loadAnimation(MensaStartActivity.this, R.anim.fadein);
                mLayout.startAnimation(animationFadeIn);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void run() {
                        moButton.performClick();
                    }} , 100);
            }
        });


        mLayout.setVisibility(View.VISIBLE);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void run() {
                mLayout.bringToFront();
            }} , 100);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }






    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location, colorizing it in parallel. In parallel, the background of the
     * activity is fading in. When the pictue is in place, the text description
     * drops down.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void runEnterAnimation() {
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
        mImageView.animate().setDuration(duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).
                withEndAction(new Runnable() {
                    public void run() {
                        // Animate the description in after the image animation
                        // is done. Slide and fade the text in from underneath
                        // the picture.
                        mTextView.setTranslationY(-mTextView.getHeight());
                        mTextView.animate().setDuration(duration/2).
                                translationY(0).alpha(1).
                                setInterpolator(sDecelerator);
                    }
                });

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
        bgAnim.setDuration(duration);
        bgAnim.start();

        // Animate a color filter to take the image from grayscale to full color.
        // This happens in parallel with the image scaling and moving into place.
        ObjectAnimator colorizer = ObjectAnimator.ofFloat(this,
                "saturation", 0, 1);
        colorizer.setDuration(duration);
        colorizer.start();

        // Animate a drop-shadow of the image
        ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(mShadowLayout, "shadowDepth", 0, 1);
        shadowAnim.setDuration(duration);
        shadowAnim.start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void runExitAnimation(final Runnable endAction) {
        final long duration = (long) (ANIM_DURATION * LauncherActivity.sAnimatorScale);

        // No need to set initial values for the reverse animation; the image is at the
        // starting size/location that we want to start from. Just animate to the
        // thumbnail size/location that we retrieved earlier

        // Caveat: configuration change invalidates thumbnail positions; just animate
        // the scale around the center. Also, fade it out since it won't match up with
        // whatever's actually in the center
        final boolean fadeOut;
        if (getResources().getConfiguration().orientation != mOriginalOrientation) {
            mImageView.setPivotX(mImageView.getWidth() / 2);
            mImageView.setPivotY(mImageView.getHeight() / 2);
            mLeftDelta = 0;
            mTopDelta = 0;
            fadeOut = true;
        } else {
            fadeOut = false;
        }

        // First, slide/fade text out of the way
        mTextView.animate().translationY(-mTextView.getHeight()).alpha(0).
                setDuration(duration/2).setInterpolator(sAccelerator).
                withEndAction(new Runnable() {
                    public void run() {
                        // Animate image back to thumbnail size/location
                        mImageView.animate().setDuration(duration).
                                scaleX(mWidthScale).scaleY(mHeightScale).
                                translationX(mLeftDelta).translationY(mTopDelta).
                                withEndAction(endAction);
                        if (fadeOut) {
                            mImageView.animate().alpha(0);
                        }
                        // Fade out background
                        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0);
                        bgAnim.setDuration(duration);
                        bgAnim.start();

                        // Animate the shadow of the image
                        ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(mShadowLayout,
                                "shadowDepth", 1, 0);
                        shadowAnim.setDuration(duration);
                        shadowAnim.start();

                        // Animate a color filter to take the image back to grayscale,
                        // in parallel with the image scaling and moving into place.
                        ObjectAnimator colorizer =
                                ObjectAnimator.ofFloat(this,
                                        "saturation", 1, 0);
                        colorizer.setDuration(duration);
                        colorizer.start();
                    }
                });


    }

    @Override
    public void onBackPressed() {
        mLayout.setVisibility(View.GONE);
        runExitAnimation(new Runnable() {
            public void run() {
                // *Now* go ahead and exit the activity
                finish();
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        try{
            if(myFakeTextView!=null){
                myFakeTextView.setText(myFakeTextView.getText().toString());
            }}catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void finish() {
        super.finish();

        // override transitions to skip the standard window animations
        overridePendingTransition(0, 0);
    }

    public void setSaturation(float value) {
        colorizerMatrix.setSaturation(value);
        ColorMatrixColorFilter colorizerFilter = new ColorMatrixColorFilter(colorizerMatrix);
        mBitmapDrawable.setColorFilter(colorizerFilter);
    }


}
