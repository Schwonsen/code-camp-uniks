package com.uni.cc_uniapp_2015.mensadata;

import android.content.Context;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.uni.cc_uniapp_2015.helper.JsonHelper;
import com.uni.cc_uniapp_2015.modell.Canteen;
import com.uni.cc_uniapp_2015.settings.MySettings;
import com.uni.cc_uniapp_2015.util.CanteenMenuParser;
/**
 * Created by Schrom on 09.02.2015.
 */
public class MensaDataManager {


    static public Map<String,MensaWeekPlan> mensaWeekPlanMap ;


    public static void initialize(Context context){


        mensaWeekPlanMap = new LinkedHashMap<String,MensaWeekPlan>();
        List<String> listOfMensas = MySettings.getListOfMensas();
        for (String singleMensaName : listOfMensas){
            String singleMensaDataJson = MensaDataService.getMensaDataJson(context, singleMensaName);
            //Toast.makeText(context,""+singleMensaName+": "+singleMensaDataJson,Toast.LENGTH_SHORT).show();
            MensaWeekPlan mySingleMensaWeekPlan = JsonHelper.getWeekPlanFromJson(singleMensaDataJson);
            //Toast.makeText(context,"put "+singleMensaName+" "+mySingleMensaWeekPlan,Toast.LENGTH_SHORT).show();

            mensaWeekPlanMap.put(singleMensaName,mySingleMensaWeekPlan);
        }


        //Toast.makeText(context,mensaWeekPlanMap.get(MySettings.zentralMensaName).getListOfMensaDays().get(1).getName(),Toast.LENGTH_SHORT).show();
    }

    public static void crawlData(Context context){

        new CanteenMenuParser(context,MySettings.k10Name).execute(new String[]{Canteen.K10_URL});
        new CanteenMenuParser(context,MySettings.zentralMensaName).execute(new String[]{Canteen.ZENTRALMENSA_URL});
        new CanteenMenuParser(context,MySettings.mensaIngSchule).execute(new String[]{Canteen.MENSA_71_WA_URL});


    }
}
