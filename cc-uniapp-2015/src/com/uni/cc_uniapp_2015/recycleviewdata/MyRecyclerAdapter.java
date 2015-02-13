package com.uni.cc_uniapp_2015.recycleviewdata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




import java.util.List;

import com.uni.cc_uniapp_2015.R;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MealListRowHolder> {

	public static final String TAG = "MyRecyclerAdapter";

	private List<MealItem> mealItemList;
	private Context mContext;

	public MyRecyclerAdapter(Context context, List<MealItem> feedItemList) {
		this.mealItemList = feedItemList;
		this.mContext = context;
	}

	@Override
	public MealListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myrecyclerviewlayout, null);
		MealListRowHolder mh = new MealListRowHolder(v);
		return mh;
	}

	@Override
	public void onBindViewHolder(MealListRowHolder mealListRowHolder, int i) {
		MealItem feedItem = mealItemList.get(i);

		//mealListRowHolder.icon.setBackgroundResource();
		mealListRowHolder.nameOfMeal.setText(feedItem.getNameOfMeal());
		mealListRowHolder.descriptionOfMeal.setText(feedItem.getDescriptionOfMeal());

		mealListRowHolder.studentPrice.setText(feedItem.getStudentPrice());
		mealListRowHolder.employeePrice.setText(feedItem.getEmployeePrice());
		mealListRowHolder.otherPrice.setText(feedItem.getOtherPrice());
	}

	@Override
	public int getItemCount() {
		return (null != mealItemList ? mealItemList.size() : 0);
	}
}
