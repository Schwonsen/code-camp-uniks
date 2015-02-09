package com.uni.cc_uniapp_2015;

import java.util.ArrayList;
import java.util.List;

public class Day {
	String day;
	List<Meal> listOfMeals = new ArrayList<Meal>();
	
	public Day(String day){
		this.day = day;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<Meal> getListOfMeals() {
		return listOfMeals;
	}
	public void addMeal(Meal meal){
		listOfMeals.add(meal);
	}
	public void setListOfMeals(List<Meal> listOfMeals) {
		this.listOfMeals = listOfMeals;
	}

}
