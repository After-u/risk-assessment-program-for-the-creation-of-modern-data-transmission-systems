package com.RiskPO.Logic;

import java.util.ArrayList;
import java.util.List;

public class Logic {
    public List<Double> fromInttoDouble(int[] intArray){
        List<Double> doubleList = new ArrayList<>();
        for (int i = 0; i < intArray.length; i++) {
            doubleList.add((double) intArray[i]);
        }
        return doubleList;
    }
}
