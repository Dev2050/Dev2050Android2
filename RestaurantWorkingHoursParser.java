package com.wolt.devname.restaurantopeninghours;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class RestaurantWorkingHoursParser {
    public RestaurantProperties restaurantProperties;
    public ArrayList<HashMap<String, String>> openingHoursArrayList;
    public HashMap<String, String> openingHoursList;
    public Context context;
    public RestaurantWorkingHoursParser(Context context){
        //super();
        this.context = context;
        restaurantProperties = new RestaurantProperties();
        openingHoursArrayList = new ArrayList<HashMap<String, String>>();
        openingHoursList = new HashMap<String, String>();
    }
    //Parser method
    public ArrayList<HashMap<String, String>> parseJSONArray(JSONArray jsonArray, String day){
        if(jsonArray.length()!=0){
            try{
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject jsonInsider = jsonArray.getJSONObject(i);

                    String theType = jsonInsider.getString("type");
                    restaurantProperties.setTheType(theType);
                    if(jsonArray.length()==1 && theType.equalsIgnoreCase("open")){
                        restaurantProperties.setJsonArrayLengthEqOne(true);
                        restaurantProperties.setSpecialDay(day);
                    }
                    else if(jsonArray.length()==1 && theType.equalsIgnoreCase("close")){
                        restaurantProperties.setJsonArrayLengthEqOne(false);
                    }
                    else if((theType.equalsIgnoreCase("close") && restaurantProperties.getJsonArrayLengthEqOne())){
                        String closingTime = jsonInsider.getString("value");
                        restaurantProperties.setClosingTime(closingTime);
                        String specialDay = restaurantProperties.getSpecialDay();
                        specialDayOperation(specialDay);

                        if(theType.equalsIgnoreCase("close") && jsonArray.length() == 2){
                            closingTime = jsonInsider.getString("value");
                            restaurantProperties.setClosingTime(closingTime);
                            commonTask(day);
                        }
                    }
                    else if(theType.equalsIgnoreCase("close") && jsonArray.length() == 2){
                        String closingTime = jsonInsider.getString("value");
                        restaurantProperties.setClosingTime(closingTime);
                        commonTask(day);
                    }
                    else if(theType.equalsIgnoreCase("close") && jsonArray.length()>2 && restaurantProperties.getJsonArrayLengthGrTwo()){
                        String closingTime = jsonInsider.getString("value");
                        restaurantProperties.setClosingTime(closingTime);
                        commonTask(day);
                    }
                    else if(theType.equalsIgnoreCase("open") && jsonArray.length() != 1){
                        if(jsonArray.length()>2){
                            restaurantProperties.setJsonArrayLengthGrTwo(true);
                        }
                        String openingTime = jsonInsider.getString("value");
                        restaurantProperties.setOpeningTime(openingTime);

                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }else{
            defaultOpeningStatus(day);
        }
        return openingHoursArrayList;
    }
    //This method assigns default values for closed working day
    public void defaultOpeningStatus(String day){
        openingHoursList = new HashMap<String, String>();
        restaurantProperties.setNewDay(day.concat(": ").concat("Closed"));
        openingHoursList.put("status", restaurantProperties.getNewDay());
        openingHoursArrayList.add(openingHoursList);

    }
    //This method is used to execute common tasks and avoid duplication
    public void commonTask(String day){
        String openingTime = restaurantProperties.getOpeningTime();
        String closingTime = restaurantProperties.getClosingTime();
        restaurantProperties.setNewDay(day.concat(": ").concat(openingTime).concat(" - ").concat(closingTime));
        //Adding the values into the ArrayList
        openingHoursList = new HashMap<String, String>();
        openingHoursList.put("status", restaurantProperties.getNewDay());
        openingHoursArrayList.add(openingHoursList);

    }
    //This method is used to handle special long days having only opening hours
    public void specialDayOperation(String specialDay){
        String openingTime = restaurantProperties.getOpeningTime();
        String closingTime = restaurantProperties.getClosingTime();
        String theSpecialDay = specialDay.concat(": ").concat(openingTime).concat(" - ").concat(closingTime);
        //Adding the values into the ArrayList
        openingHoursList = new HashMap<String, String>();
        openingHoursList.put("status", theSpecialDay);
        openingHoursArrayList.add(openingHoursList);
        restaurantProperties.setJsonArrayLengthEqOne(false);
    }
    //This method is general parse method
    public String parseJSON(){
        String json = null;
        try{
            InputStream istream = context.getAssets().open("restaurantopeninghours.json");
            int size = istream.available();
            byte[] buffer = new byte[size];
            istream.read(buffer);
            istream.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
