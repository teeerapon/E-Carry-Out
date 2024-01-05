package com.sm.sdk.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sm.sdk.myapplication.R;

public class setBaseAPI extends AppCompatActivity {

    EditText base_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_base_api);
        base_url = findViewById(R.id.base_url);

        SharedPreferences preferences = getSharedPreferences("SHARED_PRES", MODE_PRIVATE);
        String baseURL = preferences.getString("base_url", "https://www.tkig.co.th");
        base_url.setText(baseURL);

        Button save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newBase_url = base_url.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("base_url", newBase_url);
                editor.apply();

                Toast.makeText(setBaseAPI.this,"New URI = " +newBase_url,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(setBaseAPI.this, CarryOutChoseWay.class);
                startActivity(intent);
            }
        });
    }
}