package com.sm.sdk.myapplication;

import static com.android.volley.VolleyLog.TAG;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CaptureAssets extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    final ArrayList<DataImageCapture> imageList = new ArrayList<>();
    final ArrayList<String> imageListBase64 = new ArrayList<>();


    private RecyclerView recyclerViewImage;



    Button button2;
    Button button3;
    RequestQueue queue;
    String url;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_carry_2);

        recyclerViewImage = findViewById(R.id.listImageCapture);
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(this));

        button2 = findViewById(R.id.button2);
        button2.setVisibility(View.GONE);

        button3 = findViewById(R.id.button3);
        button3.setVisibility(View.GONE);

        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageList.remove(imageList.size()-1);
                imageListBase64.remove(imageList.size()-1);
                RecyclerCaptureImage imageAdapter = new RecyclerCaptureImage(CaptureAssets.this, imageList);
                recyclerViewImage.setAdapter(imageAdapter);
                if(imageList.size() == 0){
                    button2.setVisibility(View.GONE);
                    button3.setVisibility(View.GONE);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                queue = Volley.newRequestQueue(CaptureAssets.this);
                url = Method.Base_url + "/demo/e-carryout/api/ receive/?UID=4cec00d0875d1294ebfbaf44d62b6041&token=dcc729c721b2eb21cb771e5747fc35d9&id=32&gate=12&statusReturn=0&imgData=imgData&cardData=cardData&guardData=guardData";

                String carryout_no = getIntent().getStringExtra("carryout_no");
                String gate = getIntent().getStringExtra("GateWay");
                assert carryout_no != null;
                Log.e(TAG,"Carry Out ID: "+carryout_no.split(",")[2]);
                Log.e(TAG,"Gate: "+gate);
                Log.e(TAG,"Data Image: "+imageListBase64);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");
            DataImageCapture captureImage = new DataImageCapture(bitmapImage);
            imageList.add(captureImage);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            assert bitmapImage != null;
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            imageListBase64.add(Base64.encodeToString(byteArray, Base64.DEFAULT));
        }
        RecyclerCaptureImage imageAdapter = new RecyclerCaptureImage(CaptureAssets.this, imageList);
        recyclerViewImage.setAdapter(imageAdapter);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
    }
}