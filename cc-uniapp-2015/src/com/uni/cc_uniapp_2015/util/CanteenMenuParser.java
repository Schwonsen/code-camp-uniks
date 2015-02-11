package com.uni.cc_uniapp_2015.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.uni.cc_uniapp_2015.modell.Canteen;
import com.uni.cc_uniapp_2015.modell.Day;
import com.uni.cc_uniapp_2015.modell.Meal;

import android.os.AsyncTask;
import android.util.Log;

public class CanteenMenuParser extends AsyncTask<String, Void, String>{
	
    @Override
    protected String doInBackground(String... strings) {
    	Canteen canteen = null;
    	String result = "";
        try {
            Log.d("JSwa", "Connecting to ["+strings[0]+"]");
            Document doc  = Jsoup.connect(strings[0]).get();
            Log.d("JSwa", "Connected to ["+strings[0]+"]");
            canteen = new Canteen();
            //parse canteen name
            String canteenName = doc.select("tr.thead").first().getElementsByTag("h1").text();
            canteen.setName(canteenName != "" ? canteenName : "K10"); //k10 has image as canteen name
			// parse meals
            Elements topicList = doc.select("tr.items_row");    
            canteen.initDays();
            List<Day> days = canteen.getListOfDays();
            for (Element row : topicList) {
            	String mealName = null;
            	Elements colls = row.getElementsByTag("td");
            	int idx = 0;
            	for (Element coll : colls) {
            		if (idx == 0) {
            			mealName = coll.text();
            		} else {
            			Meal meal = new Meal();
            			meal.setName(mealName);
            			meal.setDescription(coll.text());
            			days.get(idx - 1).addMeal(meal);
            		}	
            		idx++;
            	}
            }
            //parse meal prices
            Elements priceList = doc.select("tr.price_row");
            int mealIdx = 0;
            for (Element row : priceList) {
            	Elements colls = row.getElementsByTag("td");
            	int idx = 0;
            	for (Element coll : colls) {
            		if (idx > 0) {
            			Day day = days.get(idx - 1);
            			Meal meal = day.getListOfMeals().get(mealIdx);
            			String[] prices = coll.text().replace("€", "").split("/");
            			if (prices.length == 3){
            				meal.setPriceStud(prices[0]);
                			meal.setPriceEmpl(prices[1]);
                			meal.setPriceOther(prices[2]);
            			}
            		}	
            		idx++;
            	}
                mealIdx++;
            }
            result = generateJson(canteen);
        }
        catch(Throwable t) {
        	result = "{'error' : 'no internet connection'}";
        	Log.e("result", result);
            t.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);    
        Log.e("onPostExecute", s);
    }
    
    private String generateJson(Canteen canteen){
    	String result = "{'error' : 'no data'}";
    	if (null != canteen){
    		
            JSONObject canteenObject = new JSONObject();
            try {
            	canteenObject.put("canteenName", canteen.getName());
				JSONArray days = new JSONArray();
				for (Day day : canteen.getListOfDays()) {		
					
	            	JSONObject dayObject = new JSONObject();            	
	            	JSONArray meals = new JSONArray();
	            	
	            	for(Meal meal : day.getListOfMeals()) {
	            		
	            		JSONObject mealObject = new JSONObject();
	            		mealObject.put("nameOfMeal", meal.getName().length() > 0 ? meal.getName() : "");
	            		mealObject.put("descriptionOfMeal", meal.getDescription().length() > 0 ? meal.getDescription() : "");
	            		JSONArray prices = new JSONArray();
	            		
	            		JSONObject priceStudent = new JSONObject();
	            		JSONObject priceOther = new JSONObject();
	            		JSONObject pricEmployee = new JSONObject();
	            		
	            		priceStudent.put("type", "student");
	            		priceStudent.put("price",  meal.getPriceStud().length() > 0 ?  meal.getPriceStud() : "");
	            		
	            		pricEmployee.put("type", "employee");
	            		pricEmployee.put("price", meal.getPriceEmpl().length() > 0 ?  meal.getPriceEmpl() : "");
	            		
	            		priceOther.put("type", "other");
	            		priceOther.put("price", meal.getPriceOther().length() > 0 ?  meal.getPriceOther() : "" );
	            		
	            		prices.put(priceStudent);
	            		prices.put(pricEmployee);
	            		prices.put(priceOther);
	            		
	            		mealObject.put("priceList", prices);
	            		meals.put(mealObject);
	            	}
	            	
	            	dayObject.put("listOfMeals", meals);
	            	dayObject.put("nameOfDay", day.getName());
	            	days.put(dayObject);
	            }
				canteenObject.put("days", days);
			} catch (JSONException e) {
				e.printStackTrace();
			}

    		result = canteenObject.toString();
    	}

        return result;
    }
}
