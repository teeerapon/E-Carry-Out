package com.sm.sdk.myapplication;

import static com.android.volley.VolleyLog.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sm.sdk.myapplication.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class CarryOutNo extends AppCompatActivity {
    final ArrayList<String> listCarryout_no = new ArrayList<String>();
    String carryNo;
    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;
    RequestQueue queue;
    String url;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carry_out_no);

        SharedPreferences preferences = getSharedPreferences("SHARED_PRES", MODE_PRIVATE);

        autoCompleteTextView = findViewById(R.id.auto_complete_carry_out_no);
        adapterItems = new ArrayAdapter<String>(CarryOutNo.this, R.layout.list_item, listCarryout_no);

        queue = Volley.newRequestQueue(this);
        url = preferences.getString("base_url", "https://www.tkig.co.th") + "/demo/e-carryout/api/list/?UID=4cec00d0875d1294ebfbaf44d62b6041&token=dcc729c721b2eb21cb771e5747fc35d9&gate=" + getIntent().getStringExtra("GateWay");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("result_data");
                    LogUtil.e(Constant.TAG, array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        Log.e(TAG, object1.getString("carryout_no"));
                        listCarryout_no.add(object1.getString("carryout_no"));
                    }
                    adapterItems = new ArrayAdapter<String>(CarryOutNo.this, R.layout.list_item, listCarryout_no);
                    autoCompleteTextView.setAdapter(adapterItems);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
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
                carryNo = item;
                Log.e(TAG, "onFailure: " + item);

            }
        });

        button = (Button) findViewById(R.id.next_step_carry_out1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carryNo == null){
                    Toast.makeText(CarryOutNo.this,"กรุณาเลือก Carry Out No",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CarryOutNo.this, CarryOutDetails.class);
                    intent.putExtra("carryout_no", carryNo.split(",")[2]);
                    intent.putExtra("GateWay", getIntent().getStringExtra("GateWay"));
                    intent.putExtra("api_getWay", getIntent().getStringExtra("api_getWay"));
                    startActivity(intent);
                }
            }
        });

    }
}
