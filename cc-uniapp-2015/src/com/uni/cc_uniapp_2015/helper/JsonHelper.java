package com.uni.cc_uniapp_2015.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uni.cc_uniapp_2015.mensadata.MensaDay;
import com.uni.cc_uniapp_2015.mensadata.MensaMeal;
import com.uni.cc_uniapp_2015.mensadata.MensaMealPrice;
import com.uni.cc_uniapp_2015.mensadata.MensaWeekPlan;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Schrom on 09.02.2015.
 */
public class JsonHelper {

    public static MensaWeekPlan getWeekPlanFromJson(String json){
        MensaWeekPlan myWeekPlan = null;
        try {

        JSONObject mensaWeekplanJsonObject = new JSONObject(json);
        JSONArray listOfDaysJsonObject = mensaWeekplanJsonObject.getJSONArray("days");
        String date = DateHelper.getCurrentDateString();

        List<MensaDay> myListOfMensaDays = new ArrayList<MensaDay>();
        Integer numberOfDays = listOfDaysJsonObject.length();
        for(int i=0 ; i < numberOfDays ; i++ ) {
            JSONObject singleDayJsonObject = (JSONObject) listOfDaysJsonObject.get(i);
            String mySingleNameOfDay = singleDayJsonObject.get("nameOfDay").toString();
            List<MensaMeal> mySingleListOfMensaMeals = new ArrayList<MensaMeal>();

            JSONArray mySingleListOfMensaMealsJsonArray = singleDayJsonObject.getJSONArray("listOfMeals");

            int numberOfMeals = 3 ;
            for(int k=0 ; k < numberOfMeals ; k++ ) {
                List<MensaMealPrice> mySingleMensaMealPriceList= new ArrayList<MensaMealPrice>();
                JSONObject singleMealJsonObject = (JSONObject) mySingleListOfMensaMealsJsonArray.get(k);
                String mySingleMensaMealName= singleMealJsonObject.get("nameOfMeal").toString();
                String mySingleMensaMealDescription= singleMealJsonObject.get("descriptionOfMeal").toString();
                JSONArray mySingleMensaMealPriceListJsonArray = singleMealJsonObject.getJSONArray("priceList");


                int numberOfPrices = 3;
                for(int p=0 ; p < numberOfPrices ; p++ ) {
                    JSONObject mySinglePriceJsonObject = mySingleMensaMealPriceListJsonArray.getJSONObject(p);
                    String singleMealPriceValue = mySinglePriceJsonObject.getString("price");
                    String singleMealPriceType = mySinglePriceJsonObject.getString("type");
                    MensaMealPrice mySingleMensaMealPrice = new MensaMealPrice (singleMealPriceValue,singleMealPriceType);
                    mySingleMensaMealPriceList.add(mySingleMensaMealPrice);
                }

                MensaMeal mySingleMensaMeal = new MensaMeal(mySingleMensaMealName,mySingleMensaMealDescription,mySingleMensaMealPriceList);
                mySingleListOfMensaMeals.add(mySingleMensaMeal);
            }
            MensaDay mySingleMensaDay = new MensaDay(mySingleNameOfDay,mySingleListOfMensaMeals);

            myListOfMensaDays.add(mySingleMensaDay);
        }

        myWeekPlan = new MensaWeekPlan(myListOfMensaDays,date);


        }catch (Exception e){
        	e.printStackTrace();
        }
        return myWeekPlan;
    }



}
