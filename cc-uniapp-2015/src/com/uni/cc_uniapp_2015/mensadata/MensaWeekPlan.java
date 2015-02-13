package com.uni.cc_uniapp_2015.mensadata;

import java.util.List;

public class MensaWeekPlan {

	List<MensaDay> listOfMensaDays;
	String date;

	public MensaWeekPlan (List<MensaDay> listOfMensaDays , String date ){
		this.listOfMensaDays=listOfMensaDays;
		this.date = date;
	}

	public List<MensaDay> getListOfMensaDays() {
		return listOfMensaDays;
	}

	public void setListOfMensaDays(List<MensaDay> listOfMensaDays) {
		this.listOfMensaDays = listOfMensaDays;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
