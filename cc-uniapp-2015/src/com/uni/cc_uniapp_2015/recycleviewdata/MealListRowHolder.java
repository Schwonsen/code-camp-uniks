package com.uni.cc_uniapp_2015.recycleviewdata;
import com.uni.cc_uniapp_2015.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MealListRowHolder extends RecyclerView.ViewHolder {
	protected ImageView icon;
	protected TextView nameOfMeal;
	protected TextView descriptionOfMeal;
	protected TextView studentPrice;
	protected TextView employeePrice;
	protected TextView otherPrice;

	public MealListRowHolder(View view) {
		super(view);
		this.icon = (ImageView) view.findViewById(R.id.mealicon);
		this.nameOfMeal = (TextView) view.findViewById(R.id.nameOfMeal);
		this.descriptionOfMeal = (TextView) view.findViewById(R.id.descriptionOfMeal);
		this.studentPrice = (TextView) view.findViewById(R.id.studentPrice);
		this.employeePrice = (TextView) view.findViewById(R.id.employeePrice);
		this.otherPrice = (TextView) view.findViewById(R.id.otherPrice);
	}
}