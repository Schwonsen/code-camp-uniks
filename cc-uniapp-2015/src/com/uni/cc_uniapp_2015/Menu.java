package com.uni.cc_uniapp_2015;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	String name;
	
	List<Day> listOfDays = new ArrayList<Day>();

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Day> getListOfDays() {
		return listOfDays;
	}

	public void setListOfDays(List<Day> listOfDays) {
		this.listOfDays = listOfDays;
	}
	

}
