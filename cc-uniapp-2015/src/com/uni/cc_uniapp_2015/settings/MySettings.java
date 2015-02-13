package com.uni.cc_uniapp_2015.settings;

import java.util.ArrayList;
import java.util.List;

public class MySettings {

	public static final String zentralMensaName = "Zentral Mensa";
	public static final  String k10Name = "Bistro K10";
	public static final  String mensaIngSchule = "Mensa Ingschule";

	public static List<String> getListOfMensas (){
		List<String> listOfMensas = new ArrayList<String>();
		listOfMensas.add(zentralMensaName);
		listOfMensas.add(k10Name);
		listOfMensas.add(mensaIngSchule);
		return listOfMensas;
	}
}
