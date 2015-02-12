package com.uni.cc_uniapp_2015.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Schrom on 09.02.2015.
 */
public class DateHelper {

    public static String getCurrentDateString(){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }



}
