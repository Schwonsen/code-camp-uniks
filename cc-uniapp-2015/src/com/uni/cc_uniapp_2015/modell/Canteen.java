package com.uni.cc_uniapp_2015.modell;

import java.util.ArrayList;
import java.util.List;

public class Canteen {

	public final static String ZENTRALMENSA_URL = "http://www.studentenwerk-kassel.de/188.html";
	public final static String K10_URL = "http://www.studentenwerk-kassel.de/144.html";
	public static String MENSA_71_WA_URL = "http://www.studentenwerk-kassel.de/189.html";
	public static String MENZELSTRASSE_URL = "http://www.studentenwerk-kassel.de/195.html";
	public static String MENSA_H_P_S_URL = "http://www.studentenwerk-kassel.de/187.html";
	public static String MENSA_WITZENHAUSEN = "http://www.studentenwerk-kassel.de/415.html";

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

	public void initDays() {
		listOfDays.add(new Day("Montag"));
		listOfDays.add(new Day("Dienstag"));
		listOfDays.add(new Day("Mittwoch"));
		listOfDays.add(new Day("Donnerstag"));
		listOfDays.add(new Day("Freitag"));
	}
}
