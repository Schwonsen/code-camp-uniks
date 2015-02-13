package com.uni.cc_uniapp_2015.helper;

public class IntegerHelper {
	/**
	 * @param stringFromWhichYouWantToGetTheIntegerValue
	 *            stringFromWhichYouWantToGetTheDoubleValue
	 * @return double value of string
	 */
	public static Integer getIntegerOf(
			String stringFromWhichYouWantToGetTheIntegerValue) {
		Integer integerValueOfString = null;
		try {
            integerValueOfString = Integer
					.valueOf(stringFromWhichYouWantToGetTheIntegerValue.replace(
							",", ".").replaceAll("[^0-9.]", ""));
		} catch (Exception e) {
			System.out.println("getting double value of "
					+ stringFromWhichYouWantToGetTheIntegerValue + " failed ");
		}
		
		return integerValueOfString;
	}
}
