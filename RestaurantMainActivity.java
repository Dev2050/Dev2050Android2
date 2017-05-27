package com.wolt.devname.restaurantopeninghours;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class RestaurantMainActivity extends AppCompatActivity {

    private RestaurantWorkingHoursParser restaurantWorkingHoursParser;
    ArrayList<HashMap<String, String>> parsedOpeninghoursArrayList;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantWorkingHoursParser = new RestaurantWorkingHoursParser(getApplicationContext());
        parsedOpeninghoursArrayList = new ArrayList<HashMap<String, String>>();
        try{
            JSONObject jsonObject = new JSONObject(restaurantWorkingHoursParser.parseJSON());
            JSONArray jsonArray1 = jsonObject.getJSONArray("monday");
            parsedOpeninghoursArrayList = restaurantWorkingHoursParser.parseJSONArray(jsonArray1, "Monday");
            JSONArray jsonArray2 = jsonObject.getJSONArray("tuesday");
            parsedOpeninghoursArrayList = restaurantWorkingHoursParser.parseJSONArray(jsonArray2, "Tuesday");
            JSONArray jsonArray3 = jsonObject.getJSONArray("wednesday");
            parsedOpeninghoursArrayList = restaurantWorkingHoursParser.parseJSONArray(jsonArray3, "Wednesday");
            JSONArray jsonArray4 = jsonObject.getJSONArray("thursday");
            parsedOpeninghoursArrayList = restaurantWorkingHoursParser.parseJSONArray(jsonArray4,"Thursday");
            JSONArray jsonArray5 = jsonObject.getJSONArray("friday");
            parsedOpeninghoursArrayList = restaurantWorkingHoursParser.parseJSONArray(jsonArray5, "Friday");
            JSONArray jsonArray6 = jsonObject.getJSONArray("saturday");
            parsedOpeninghoursArrayList = restaurantWorkingHoursParser.parseJSONArray(jsonArray6, "Saturday");
            JSONArray jsonArray7 = jsonObject.getJSONArray("sunday");
            parsedOpeninghoursArrayList = restaurantWorkingHoursParser.parseJSONArray(jsonArray7, "Sunday");
        }catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        setContentView(R.layout.activity_restaurant_main);
        lv = (ListView) findViewById(R.id.list);
        ListAdapter adapter = new SimpleAdapter(RestaurantMainActivity.this, parsedOpeninghoursArrayList,
                R.layout.list_item, new String[] {"status"},
                new int[]{R.id.status});
        lv.setAdapter(adapter);
    }

}
