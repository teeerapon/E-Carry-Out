package com.sm.sdk.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sm.sdk.myapplication.Data.DataImageCapture;
import com.sm.sdk.myapplication.Recycler.RecyclerCaptureImage;
import com.sm.sdk.myapplication.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class CaptureAssets extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    final ArrayList<DataImageCapture> imageList = new ArrayList<>();
    final ArrayList<String> imageListBase64 = new ArrayList<>();


    private RecyclerView recyclerViewImage;


    Button button2;
    Button button3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_image);

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
                if (imageList.size() <= 1 ) {
                    imageList.clear();
                    RecyclerCaptureImage imageAdapter = new RecyclerCaptureImage(CaptureAssets.this, imageList);
                    recyclerViewImage.setAdapter(imageAdapter);
                    button2.setVisibility(View.GONE);
                    button3.setVisibility(View.GONE);
                }else{
                    imageList.remove(imageList.size() - 1);
                    imageListBase64.remove(imageList.size() - 1);
                    RecyclerCaptureImage imageAdapter = new RecyclerCaptureImage(CaptureAssets.this, imageList);
                    recyclerViewImage.setAdapter(imageAdapter);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String carryout_no = getIntent().getStringExtra("carryout_no");
                String geteWay = getIntent().getStringExtra("GateWay");
                String statusReturn = getIntent().getStringExtra("statusReturn");

                if(imageList.size() == 0){
                    Toast.makeText(CaptureAssets.this,"กรุณาถ่ายรูปอย่างน้อย 1 รูป",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CaptureAssets.this, CarryOutReaderCard.class);
                    intent.putExtra("GateWay", geteWay);
                    intent.putExtra("carryout_no", carryout_no);
                    intent.putExtra("statusReturn", statusReturn);
                    intent.putExtra("api_getWay", getIntent().getStringExtra("api_getWay"));
                    intent.putStringArrayListExtra("imgData",imageListBase64);
                    startActivity(intent);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bitmapImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            DataImageCapture captureImage = new DataImageCapture(bitmapImage);
            imageList.add(captureImage);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            assert bitmapImage != null;
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            imageListBase64.add(Base64.encodeToString(byteArray, Base64.DEFAULT));
        }
        RecyclerCaptureImage imageAdapter = new RecyclerCaptureImage(CaptureAssets.this, imageList);
        recyclerViewImage.setAdapter(imageAdapter);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
    }
}
