package com.wolt.fissha.restaurantopeninghours;

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
        parsedOpeninghoursArrayList = restaurantWorkingHoursParser.getParsedItems();
        setContentView(R.layout.activity_restaurant_main);
        lv = (ListView) findViewById(R.id.list);
        ListAdapter adapter = new SimpleAdapter(RestaurantMainActivity.this, parsedOpeninghoursArrayList,
                R.layout.list_item, new String[] {"status"},
                new int[]{R.id.status});
        lv.setAdapter(adapter);
    }

}
