/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uni.cc_uniapp_2015.recycleviewdata;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.activities.MensaStartActivity;
import com.uni.cc_uniapp_2015.helper.IntegerHelper;
import com.uni.cc_uniapp_2015.mensadata.MensaDataManager;
import com.uni.cc_uniapp_2015.mensadata.MensaDay;
import com.uni.cc_uniapp_2015.mensadata.MensaMeal;
import com.uni.cc_uniapp_2015.mensadata.MensaMealPrice;
import com.uni.cc_uniapp_2015.mensadata.MensaWeekPlan;

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 */
public class ContentFragment extends Fragment {

	public static final String TAG = "ContentFragment";

	private static final String KEY_TITLE = "title";
	private static final String KEY_INDICATOR_COLOR = "indicator_color";
	private static final String KEY_DIVIDER_COLOR = "divider_color";

	private RecyclerView mRecyclerView;
	private MyRecyclerAdapter adapter;
	private List<MealItem> feedItemList;
	View rootView;

	String mensaName;
	/**
	 * @return a new instance of {@link ContentFragment}, adding the parameters into a bundle and
	 * setting them as arguments.
	 */
	public static ContentFragment newInstance(CharSequence title, int indicatorColor,
			int dividerColor) {
		Bundle bundle = new Bundle();
		bundle.putCharSequence(KEY_TITLE, title);
		bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
		bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);

		ContentFragment fragment = new ContentFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	public void updateDay(View view) {
		//Toast.makeText(getActivity()," teest ",Toast.LENGTH_SHORT).show();
		for (Button button : MensaStartActivity.buttonViewList) {
			button.setTextColor(Color.parseColor("#222222"));
			button.setBackgroundResource(R.drawable.daybackgroundtab);
		}
		Button myButton = (Button) view;
		//myButton.setTextColor(Color.parseColor("#229922"));
		myButton.setTextColor(Color.parseColor("#ffffff"));
		myButton.setBackgroundResource(R.drawable.daybackground_clickedtab);
		sortlist(view);
		String dayNumber = view.getTag().toString();
		initializeFeedItemList(mensaName, dayNumber);
		Handler handler1 = new Handler();
		handler1.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.notifyDataSetChanged();
			}
		} , 300);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.pager_item, container, false);

		Bundle args = getArguments();

		mensaName = (String)args.getCharSequence(KEY_TITLE);
		initializeFeedItemList(mensaName,"0");
		//initialize receiverView
		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

		MensaStartActivity.myFakeTextView.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				updateDay(MensaStartActivity.clickedView);
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		Log.d(TAG, "TEST2: "+layoutManager+" "+getActivity()+" "+feedItemList.size());
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		adapter = new MyRecyclerAdapter(getActivity(), feedItemList);
		mRecyclerView.setAdapter(adapter);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);	
	}

	public void sortlist(View view){

		Random rng = new Random(); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new LinkedHashSet<Integer>();
		int s=0;
		while (generated.size() < feedItemList.size()&&s<100)//i<100 for preventing not terminating
		{
			s=s+1;
			Integer next = rng.nextInt(feedItemList.size()) ;
			// As we're adding to a set, this will automatically do a containment check
			generated.add(next);
		}

		List<MealItem> feedItemListSave = new ArrayList<MealItem>();
		feedItemListSave.addAll(feedItemList);
		feedItemList.clear();
		for(int newlinenumber : generated){
			feedItemList.add(feedItemListSave.get(newlinenumber));
		}
		final List<Integer> generatedList = new ArrayList<Integer>();
		generatedList.addAll(generated);
		final int end = feedItemList.size() - 1;
		for (int i = 0; i < end; i++) {
			final int k = i;
			if (i!=generatedList.get(i)){
				adapter.notifyItemMoved(i, generatedList.get(i));
				Handler handler1 = new Handler();
				handler1.postDelayed(new Runnable() {
					@TargetApi(Build.VERSION_CODES.HONEYCOMB)
					@Override
					public void run() {
						View myView = (View) mRecyclerView.getChildAt(k);
						if (myView!=null) {
							Log.d(TAG, "getting view " + myView + " for position " + k);
							ObjectAnimator animY = ObjectAnimator.ofFloat(myView, "translationY", -60f, 0f);
							animY.setDuration(1000);//1sec
							animY.setInterpolator(new BounceInterpolator());
							animY.setRepeatCount(0);
							animY.start();
						}
					}} , 70*i);}
		}
		Handler handler2 = new Handler();
		handler2.postDelayed(new Runnable() {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run() {
				mRecyclerView.scrollToPosition(0);
			}
		} , 200);
	}

	public void initializeFeedItemList(String mensaName,String dayNumber){
		if (null == feedItemList) {
			feedItemList = new ArrayList<MealItem>();
		}
		feedItemList.clear();
		try{
			Log.d(TAG,"MENSA NAME: "+mensaName);
			MensaWeekPlan myMensaWeekPlan = MensaDataManager.mensaWeekPlanMap.get(mensaName);

			Log.d(TAG,"MENSA NAME: "+myMensaWeekPlan);
			List<MensaDay> myListOfMensaDays = myMensaWeekPlan.getListOfMensaDays();
			MensaDay mySingleMensaDay = myListOfMensaDays.get(IntegerHelper.getIntegerOf(dayNumber));
			List<MensaMeal> mySingleMensaMealList = mySingleMensaDay.getListOfMensaMeals();
			for (MensaMeal singleMensaMeal: mySingleMensaMealList) {
				List<MensaMealPrice> listOfMensaMealPrices = singleMensaMeal.getListOfPrices();
				MealItem item = new MealItem();
				item.setNameOfMeal(singleMensaMeal.getName());
				item.setDescriptionOfMeal(singleMensaMeal.getDescription());
				item.setStudentPrice(listOfMensaMealPrices.get(0).getValue());
				item.setEmployeePrice(listOfMensaMealPrices.get(1).getValue());
				item.setOtherPrice(listOfMensaMealPrices.get(2).getValue());
				feedItemList.add(item);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
