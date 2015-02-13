package com.uni.cc_uniapp_2015.modell;

import java.util.ArrayList;
import java.util.List;

public class Day {
	String name;
	List<Meal> listOfMeals = new ArrayList<Meal>();

	public Day(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
