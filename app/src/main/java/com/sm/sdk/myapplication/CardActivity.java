package com.sm.sdk.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CardActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_card));
        initView();
    }

    private void initView() {
        View view = findViewById(R.id.card_mag);
        TextView leftText = view.findViewById(R.id.left_text);
        view.setOnClickListener(this);
        leftText.setText("รูดบัตรประชาชน");

        view = findViewById(R.id.card_ic);
        leftText = view.findViewById(R.id.left_text);
        view.setOnClickListener(this);
        leftText.setText("เสียบบัตรประชาชน");

        view = findViewById(R.id.capture);
        leftText = view.findViewById(R.id.left_text);
        view.setOnClickListener(this);
        leftText.setText("ถ่ายรูปบัตร");
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if(id == R.id.card_mag){
            openActivity(CarryOutReaderCard.class);
        }else if(id == R.id.card_ic){
            openActivity(ICActivity.class);
        }else if(id == R.id.capture){
            openActivity(ICActivity.class);
        }
//        switch (id) {
//            case R.id.card_mag:
//                openActivity(CarryOutReaderCard.class);
//                break;
//            case R.id.card_ic:
//                openActivity(ICActivity.class);
//                break;
//        }
    }


}
