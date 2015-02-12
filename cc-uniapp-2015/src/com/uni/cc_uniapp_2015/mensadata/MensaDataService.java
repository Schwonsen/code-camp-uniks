package com.uni.cc_uniapp_2015.mensadata;

import com.uni.cc_uniapp_2015.helper.FileReadingHelper;
import com.uni.cc_uniapp_2015.helper.StorageHelper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Schrom on 09.02.2015.
 */
public class MensaDataService {


    public static String getMensaDataJson(Context context, String nameOfMensa){
        String mensaData;

        mensaData = StorageHelper.getData(context,nameOfMensa);

        return mensaData;
    }

    private static String getZentralMensaJson(Context context) {
        String response = FileReadingHelper.getStringFromFile("mensadataexample","txt",context);
        return response;
    }
}
