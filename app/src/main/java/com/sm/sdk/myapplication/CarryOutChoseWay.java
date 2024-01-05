package com.sm.sdk.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CarryOutChoseWay extends AppCompatActivity {

    Button buttonIn;

    Button buttonOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carry_out_chose_way);
        SharedPreferences preferences = getSharedPreferences("SHARED_PRES", MODE_PRIVATE);
        String FirstID = preferences.getString("FirstID", null);
        String password = preferences.getString("password", null);

        if(FirstID == null || password == null){
            Intent intent = new Intent(CarryOutChoseWay.this, Login.class);
            startActivity(intent);
        }

        buttonIn = findViewById(R.id.buttonIn);

        buttonIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getIntent().removeExtra("api_getWay");
                Intent intent = new Intent(CarryOutChoseWay.this, MainActivity.class);
                intent.putExtra("api_getWay", "1");
                startActivity(intent);
            }
        });

        buttonOut = findViewById(R.id.buttonOut);

        buttonOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getIntent().removeExtra("api_getWay");
                Intent intent = new Intent(CarryOutChoseWay.this, MainActivity.class);
                intent.putExtra("api_getWay", "0");
                startActivity(intent);
            }
        });

        Button setAPI = findViewById(R.id.setAPI);
        setAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarryOutChoseWay.this, setBaseAPI.class);
                startActivity(intent);
            }
        });
    }
}