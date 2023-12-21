package com.sm.sdk.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarryOutDetails extends AppCompatActivity {

    RequestQueue queue;
    String url;
    private final List<DataAssetsImage> imageList = new ArrayList<>();
    ;
    final ArrayList<DataAssets> jsonResponses = new ArrayList<>();

    TextView frstData;
    TextView result_sutterdoor;
    TextView result_no;
    TextView result_requester;
    TextView result_company;

    TextView secondData;
    TextView result_carryout_person;
    TextView result_carryout_company;
    TextView result_carryout_car;
    TextView result_propose;
    TextView result_return;

    RecyclerView recyclerView;
    RecyclerView recyclerViewImage;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carry_out_details);

        String gateWay = getIntent().getStringExtra("GateWay");
        String carryOutNo = getIntent().getStringExtra("carryout_no");

        frstData = findViewById(R.id.FrstData);
        result_sutterdoor = findViewById(R.id.result_sutterdoor);
        result_no = findViewById(R.id.result_no);
        result_requester = findViewById(R.id.result_requester);
        result_company = findViewById(R.id.result_company);

        secondData = findViewById(R.id.secondData);
        result_carryout_person = findViewById(R.id.result_carryout_person);
        result_carryout_company = findViewById(R.id.result_carryout_company);
        result_carryout_car = findViewById(R.id.result_carryout_car);
        result_propose = findViewById(R.id.result_propose);
        result_return = findViewById(R.id.result_return);

        recyclerView.findViewById(R.id.recyclerViewAssets);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CarryOutDetails.this));

        recyclerViewImage.findViewById(R.id.recyclerViewAssetsImage);
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(CarryOutDetails.this));


        queue = Volley.newRequestQueue(this);
        url = Method.Base_url + "/demo/e-carryout/api/getdata/?UID=4cec00d0875d1294ebfbaf44d62b6041&token=dcc729c721b2eb21cb771e5747fc35d9&id=" + carryOutNo;

        button = (Button) findViewById(R.id.carry_out_assets_photo);



        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    frstData.setText("ข้อมูลผู้ร้องขอ");
                    result_sutterdoor.setText("ทางเข้าออก: " + object.getString("result_sutterdoor"));
                    result_no.setText("หมายเลย Carry Out no: " + object.getString("result_no"));
                    result_requester.setText("ผู้ร้องขอ: " + object.getString("result_requester"));
                    result_company.setText("บริษัท: " + object.getString("result_company"));

                    secondData.setText("ข้อมูลผู้นำออก");
                    result_carryout_person.setText("ผู้นำออก: " + object.getString("result_carryout_person"));
                    result_carryout_company.setText("บริษัท: " + object.getString("result_carryout_company"));
                    result_carryout_car.setText("ทะเบียนรถ: " + object.getString("result_carryout_car"));
                    result_propose.setText("วัตถุประสงค์: " + object.getString("result_propose"));
                    result_return.setText("วันที่นำกลับ: " + object.getString("result_return"));

                    JSONArray array = object.getJSONArray("result_asset_data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        String Description = "ชื่อรายการ: " + object1.getString("Description");
                        String Qty = "จำนวน: " + object1.getString("Qty");
                        String serial_no = "หมายเลขเครื่อง: " + object1.getString("serial_no");
                        String fixed_no = "หมายเลขทรัพย์สิน: " + object1.getString("fixed_no");
                        String remark = "หมายเหตุ: " + object1.getString("remark");
                        String AssetsData = "ข้อมูลทรัพย์สินที่: " + (i + 1);
                        jsonResponses.add(new DataAssets(Description, Qty, serial_no, fixed_no, remark, AssetsData));
                    }
                    RecyclerAssets recyclerAssets = new RecyclerAssets(jsonResponses);
                    recyclerView.setAdapter(recyclerAssets);

                    JSONArray arrayImage = object.getJSONArray("result_images");
                    for (int i = 0; i < arrayImage.length(); i++) {
                        String image = arrayImage.getString(i);
                        DataAssetsImage dataAssetsImage = new DataAssetsImage(image);
                        imageList.add(dataAssetsImage);
                    }

                    RecyclerAssetsImage imageAdapter = new RecyclerAssetsImage(CarryOutDetails.this, imageList);
                    recyclerViewImage.setAdapter(imageAdapter);

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
    }
}