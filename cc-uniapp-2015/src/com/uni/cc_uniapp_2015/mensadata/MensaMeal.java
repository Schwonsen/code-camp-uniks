package com.uni.cc_uniapp_2015.mensadata;

import java.util.List;

/**
 * Created by Schrom on 09.02.2015.
 */
public class MensaMeal {

    String name;
    String description;
    List<MensaMealPrice> listOfPrices;

    public MensaMeal(String name, String description, List<MensaMealPrice> listOfPrices) {
        this.name = name;
        this.description = description;
        this.listOfPrices = listOfPrices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MensaMealPrice> getListOfPrices() {
        return listOfPrices;
    }

    public void setListOfPrices(List<MensaMealPrice> listOfPrices) {
        this.listOfPrices = listOfPrices;
    }
}
