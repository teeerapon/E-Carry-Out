package com.sm.sdk.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sm.sdk.myapplication.Metthod.Method;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] item = {"MARK", "PANG", "MIKE"};
    final ArrayList<String> listName = new ArrayList<String>();
    private static final String TAG = "ActivityMain";

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    RequestQueue queue;
    String url;

    String gateWay;

    Button buttonGetway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        url = Method.Base_url + "/demo/e-carryout/api/gate/?UID=4cec00d0875d1294ebfbaf44d62b6041&token=dcc729c721b2eb21cb771e5747fc35d9";

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, item);

        buttonGetway = findViewById(R.id.buttonGetway);
        buttonGetway.setEnabled(false);

        buttonGetway.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarryOutNo.class);
                intent.putExtra("GateWay", gateWay.split(",")[1]);
                startActivity(intent);
            }
        });

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("result_data");
                    Log.e(TAG, "onResponse: "+array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        listName.add(object1.getString("name"));
                    }
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

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, listName);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Log.e(TAG, "onFailure: " + item);
                buttonGetway.setEnabled(true);
                gateWay = item;
            }
        });
    }
}