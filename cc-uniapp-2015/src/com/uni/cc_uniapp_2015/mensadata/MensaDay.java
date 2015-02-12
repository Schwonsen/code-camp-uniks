package com.uni.cc_uniapp_2015.mensadata;

import java.util.List;

/**
 * Created by Schrom on 09.02.2015.
 */
public class MensaDay {

    String name ;
    List<MensaMeal> listOfMensaMeals ;

    public MensaDay(String name, List<MensaMeal> listOfMensaMeals) {
        this.name = name;
        this.listOfMensaMeals = listOfMensaMeals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MensaMeal> getListOfMensaMeals() {
        return listOfMensaMeals;
    }

    public void setListOfMensaMeals(List<MensaMeal> listOfMensaMeals) {
        this.listOfMensaMeals = listOfMensaMeals;
    }
}
