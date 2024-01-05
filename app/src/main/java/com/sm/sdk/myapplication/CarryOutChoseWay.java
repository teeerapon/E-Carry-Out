package com.sm.sdk.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    }
}