package com.uni.cc_uniapp_2015;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.util.Log;

public class URLParser extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer buffer = new StringBuffer();
        try {
            Log.d("JSwa", "Connecting to ["+strings[0]+"]");
            Document doc  = Jsoup.connect(strings[0]).get();
            Log.d("JSwa", "Connected to ["+strings[0]+"]");
            // Get document (HTML page) title
            String title = doc.title();
            Log.d("JSwA", "Title ["+title+"]");
            buffer.append("Title: " + title + "\r\n");

         
// Looking for meals description
            Elements topicList = doc.select("tr.items_row");
            buffer.append("Topic list\r\n");
            Menu menu = new Menu();
            menu.initDays();
            List<Day> days = menu.getListOfDays();
            
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

//Looking for meals prices START
           
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
            Log.e("days", menu.getListOfDays().size() + "");
            Log.e("meals", menu.getListOfDays().get(0).getListOfMeals().size() + "");

            for (Meal meal : menu.getListOfDays().get(0).getListOfMeals()){
            	 Log.e("Monday", meal.getName() + " : " + meal.getDescription());
            }
           
           

        }
        catch(Throwable t) {
            t.printStackTrace();
        }

        return buffer.toString();
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
       Log.e("POST", "POST");
    }
}
