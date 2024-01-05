package com.sm.sdk.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sm.sdk.myapplication.utils.LogUtil;
import com.sm.sdk.myapplication.utils.PreferencesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    final ArrayList<String> listName = new ArrayList<String>();
    private static final String TAG = "ActivityMain";

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    RequestQueue queue;
    String url;

    String gateWay;

    Button buttonGetway;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("SHARED_PRES", MODE_PRIVATE);

        gateWay = preferences.getString("CarryOutGateWay", null);

        queue = Volley.newRequestQueue(this);
        url = preferences.getString("base_url", "https://www.tkig.co.th") + "/demo/e-carryout/api/gate/?UID=4cec00d0875d1294ebfbaf44d62b6041&token=dcc729c721b2eb21cb771e5747fc35d9";

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);

        buttonGetway = findViewById(R.id.buttonGetway);

        buttonGetway.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (gateWay == null) {
                    Toast.makeText(MainActivity.this, "กรุณาเลือกประตู", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, CarryOutNo.class);
                    intent.putExtra("GateWay", gateWay.split(",")[1]);
                    intent.putExtra("api_getWay", getIntent().getStringExtra("api_getWay"));
                    startActivity(intent);
                }
            }
        });

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("result_data");
                    Log.e(TAG, "onResponse: " + array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        listName.add(object1.getString("name"));
                    }
                    if(preferences.getString("CarryOutGateWay", null) != null){
                        autoCompleteTextView.setText(gateWay);
                    }
                    adapterItems = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, listName);
                    autoCompleteTextView.setAdapter(adapterItems);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("CarryOutGateWay", item);
                editor.apply();
                Log.e(TAG, "onFailure: " + item);
                gateWay = item;
            }
        });
    }
}