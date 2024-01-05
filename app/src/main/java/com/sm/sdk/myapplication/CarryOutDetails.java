package com.sm.sdk.myapplication;

import static com.android.volley.VolleyLog.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sm.sdk.myapplication.Data.DataAssetImages;
import com.sm.sdk.myapplication.Data.DataAssets;
import com.sm.sdk.myapplication.Metthod.Method;
import com.sm.sdk.myapplication.Recycler.RecyclerAssetImage;
import com.sm.sdk.myapplication.Recycler.RecyclerAssets;
import com.sm.sdk.myapplication.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarryOutDetails extends AppCompatActivity {

    RequestQueue queue;
    String url;
    private final List<DataAssetImages> imageList = new ArrayList<>();
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

    private RecyclerView assetDetail;
    private RecyclerView imageAsset;

    Button button;

    RadioGroup radioGroup;

    RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carry_out_details);

        queue = Volley.newRequestQueue(this);
        url = Method.Base_url + "/demo/e-carryout/api/getdata/?UID=4cec00d0875d1294ebfbaf44d62b6041&token=dcc729c721b2eb21cb771e5747fc35d9&id=" + getIntent().getStringExtra("carryout_no");;

        button = (Button) findViewById(R.id.carry_out_assets_photo);

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

        assetDetail = findViewById(R.id.recyclerViewAssets);
        assetDetail.setLayoutManager(new LinearLayoutManager(CarryOutDetails.this));

        imageAsset = findViewById(R.id.recyclerViewAssetsImage);
        imageAsset.setHasFixedSize(true);
        imageAsset.setLayoutManager(new LinearLayoutManager(CarryOutDetails.this));

        radioGroup = findViewById(R.id.radioGroup);

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

                    JSONArray arrayImage = object.getJSONArray("result_images");
                    for (int i = 0; i < arrayImage.length(); i++) {
                        String image = arrayImage.getString(i);
                        DataAssetImages dataAssetsImage = new DataAssetImages(image);
                        imageList.add(dataAssetsImage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RecyclerAssets recyclerAssets = new RecyclerAssets(jsonResponses);
                assetDetail.setAdapter(recyclerAssets);

                RecyclerAssetImage imageAdapter = new RecyclerAssetImage(CarryOutDetails.this, imageList);
                imageAsset.setAdapter(imageAdapter);
//                    Log.e(TAG,imageList.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(CarryOutDetails.this,"กรูณาเลือกจุดประสงค์",Toast.LENGTH_SHORT).show();
                }else{
                    int radioID = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioID);
                    String gateWay = getIntent().getStringExtra("GateWay");
                    String carryOutNo = getIntent().getStringExtra("carryout_no");
                    RadioButton radioButton1 = findViewById(R.id.radio_CarryOut);
                    String statusReturn = "0";
                    if(radioButton.getText() == radioButton1.getText()){
                        statusReturn = "1";
                    }

                    Intent intent = new Intent(CarryOutDetails.this, CaptureAssets.class);
                    intent.putExtra("carryout_no", getIntent().getStringExtra("carryout_no"));
                    intent.putExtra("GateWay", getIntent().getStringExtra("GateWay"));
                    intent.putExtra("api_getWay", getIntent().getStringExtra("api_getWay"));
                    intent.putExtra("statusReturn", statusReturn);
                    startActivity(intent);
                }
            }
        });
    }
}
