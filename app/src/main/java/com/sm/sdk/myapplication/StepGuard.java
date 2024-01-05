package com.sm.sdk.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.sm.sdk.myapplication.databinding.ActivityStepGuardBinding;
import com.sm.sdk.myapplication.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StepGuard extends AppCompatActivity {

    private String guardData;

    private MaterialButton done;
    RequestQueue queue;
    SharedPreferences preferences = getSharedPreferences("SHARED_PRES", MODE_PRIVATE);
    private String url= preferences.getString("base_url", "https://www.tkig.co.th") +"/demo/e-carryout/api/receive/?UID=4cec00d0875d1294ebfbaf44d62b6041&token=dcc729c721b2eb21cb771e5747fc35d9";

    private ActivityStepGuardBinding binding;

    private final ActivityResultLauncher<String> requestPermissLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    showCamera();
                } else {
                    LogUtil.e(Constant.TAG, "! isGranted");
                }
            });

    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(new ScanContract(), result->{
        if(result.getContents() == null){
            Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show();
        }else{
            setResult(result.getContents());
        }
    });

    private void setResult(String conents){
        binding.qrTrack.setText(conents);
        guardData = conents;
        done.setEnabled(true);
    }

    private void showCamera() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setPrompt("Scan QR Code");
        options.setCameraId(0);
        options.setBeepEnabled(false);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(false);

        qrCodeLauncher.launch(options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViews();

        queue = Volley.newRequestQueue(this);
        done = findViewById(R.id.done_process);
        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String carryout_no = getIntent().getStringExtra("carryout_no");
                String geteWay = getIntent().getStringExtra("GateWay");
                ArrayList<String> imgData = getIntent().getStringArrayListExtra("imgData");
                String cardData;
                if(Objects.equals(getIntent().getStringExtra("caseCardData"), "0")){
                    cardData = getIntent().getStringExtra("cardData");
                }else{
                    cardData = String.valueOf(getIntent().getStringArrayListExtra("cardData"));
                }
                String api_getWay = getIntent().getStringExtra("api_getWay");
                LogUtil.e(Constant.TAG,api_getWay);
                url = url
//                        + "&id=" + carryout_no
//                        + "&gate=" + geteWay
//                        + "&statusReturn=" + api_getWay
//                        + "&imgData=?" + imgData
//                        + "&cardData=" + cardData
//                        + "&guardData=" + guardData
                        ;

                LogUtil.e(Constant.TAG,url);

                if(guardData == null){
                    Toast.makeText(StepGuard.this,"กรุณาสแกน Qr Code",Toast.LENGTH_SHORT).show();
                }else{
                    queue.add(new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String resultGet = object.getString("result_status");
                                if(!resultGet.equals("OK")){
                                    Toast.makeText(StepGuard.this, resultGet, Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(StepGuard.this, "Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(StepGuard.this, CarryOutChoseWay.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } , new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // method to handle errors.
                                Toast.makeText(StepGuard.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // below line we are creating a map for
                            // storing our values in key and value pair.
                            Map<String, String> params = new HashMap<String, String>();

                            // on below line we are passing our key
                            // and value pair to our parameters.
                            if(Objects.equals(api_getWay, "0")){
                                params.put("id", carryout_no);
                                params.put("gate", geteWay);
                                params.put("statusReturn",api_getWay);
                                params.put("imgData", String.valueOf(imgData));
                                params.put("cardData",cardData);
                                params.put("guardData",guardData);
                            }else{
                                params.put("id", carryout_no);
                                params.put("gate", geteWay);
                                params.put("statusReturn",api_getWay);
                                params.put("imgData", String.valueOf(imgData));
                                params.put("cardData",cardData);
                                params.put("guardData",guardData);
                            }

                            LogUtil.e(Constant.TAG, params.toString());

                            // at last we are
                            // returning our params.
                            return params;
                        }
                    });
                }
            }
        });
    }

    private void initViews() {
        binding.cameraScan.setOnClickListener(view -> {
            checkPermissionAndShowActivity(this);
        });
    }

    private void checkPermissionAndShowActivity(Context context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {
            showCamera();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(context, "Carema permission required", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void initBinding() {
        binding = ActivityStepGuardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}