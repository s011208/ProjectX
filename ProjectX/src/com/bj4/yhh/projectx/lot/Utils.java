
package com.bj4.yhh.projectx.lot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class Utils {

    public static final NumberFormat NUMBER_FORMATTER = new DecimalFormat("00");

    public static final ArrayList<Integer> getCombinationList(final int totalNumber) {
        final ArrayList<Integer> rtn = new ArrayList<Integer>();
        HashMap<Integer, ArrayList<Integer>> tempMap = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 1; i <= totalNumber; i++) {
            int s = i / 10;
            int r = i % 10;
            int result = (s + r) % 10;
            ArrayList<Integer> tempList = tempMap.get(result);
            if (tempList == null) {
                tempList = new ArrayList<Integer>();
            }
            tempList.add(i);
            tempMap.put(result, tempList);
        }
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> tempList = tempMap.get(i);
            if (tempList != null) {
                rtn.addAll(tempList);
            }
        }
        return rtn;
    }

    public static final ArrayList<Integer> getLastList(final int totalNumber) {
        final ArrayList<Integer> rtn = new ArrayList<Integer>();
        HashMap<Integer, ArrayList<Integer>> tempMap = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 1; i <= totalNumber; i++) {
            int r = i % 10;
            int result = r;
            ArrayList<Integer> tempList = tempMap.get(result);
            if (tempList == null) {
                tempList = new ArrayList<Integer>();
            }
            tempList.add(i);
            tempMap.put(result, tempList);
        }
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> tempList = tempMap.get(i);
            if (tempList != null) {
                rtn.addAll(tempList);
            }
        }
        return rtn;
    }
}
