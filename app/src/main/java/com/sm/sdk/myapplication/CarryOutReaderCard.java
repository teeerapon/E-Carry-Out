package com.sm.sdk.myapplication;

import static com.android.volley.VolleyLog.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.sm.sdk.myapplication.utils.LogUtil;
import com.sm.sdk.myapplication.utils.Utility;
import com.sm.sdk.myapplication.wrapper.CheckCardCallbackV2Wrapper;
import com.sunmi.pay.hardware.aidlv2.AidlConstantsV2;
import com.sunmi.pay.hardware.aidlv2.readcard.CheckCardCallbackV2;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class CarryOutReaderCard extends BaseAppCompatActivity {
    private MaterialButton mBtnTotal;
    private MaterialButton mBtnSuccess;
    private MaterialButton mBtnFail;
    private TextView mTvTrack1;
    private TextView mTvTrack2;
    private TextView mTvTrack3;
    private int mTotalTime;
    private int mSuccessTime;
    private int mFailTime;

    private MaterialButton nextStepGuard;

    private String track1;
    private String track2;
    private String track3;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carry_out_reader_card);
        initViews();
        initView();
        checkCard();
    }

    private void initViews() {
        View view = findViewById(R.id.ic_view);
        view.setOnClickListener(this);

        view = findViewById(R.id.capture_view);
        view.setOnClickListener(this);
    }

    private void initView() {

        mBtnTotal = findViewById(R.id.mb_total);
        mBtnSuccess = findViewById(R.id.mb_success);
        mBtnFail = findViewById(R.id.mb_fail);
        mTvTrack1 = findViewById(R.id.tv_track1);
        mTvTrack2 = findViewById(R.id.tv_track2);
        mTvTrack3 = findViewById(R.id.tv_track3);
        nextStepGuard = findViewById(R.id.next_step_guard);

        nextStepGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carryout_no = getIntent().getStringExtra("carryout_no");
                String geteWay = getIntent().getStringExtra("GateWay");
                String statusReturn = getIntent().getStringExtra("statusReturn");
                ArrayList<String> imgData = getIntent().getStringArrayListExtra("imgData");

                if(track1 == null || track2== null || track3== null){
                    Toast.makeText(CarryOutReaderCard.this,"กรุณารูดบัตร",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CarryOutReaderCard.this, StepGuard.class);
                    intent.putExtra("GateWay", geteWay);
                    intent.putExtra("carryout_no", carryout_no);
                    intent.putExtra("statusReturn", statusReturn);
                    intent.putStringArrayListExtra("imgData",imgData);
                    intent.putExtra("api_getWay", getIntent().getStringExtra("api_getWay"));
                    intent.putExtra("cardData","$"+track1+"$"+track2+"$"+track3);
                    intent.putExtra("caseCardData","0");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
         if(id == R.id.ic_view){
            openActivity(ICActivity.class);
        }else if(id == R.id.capture_view){
            openActivity(CaptureCardData.class);
        }
    }

    /** start check card */
    private void checkCard() {
        try {
            addStartTimeWithClear("checkCard()");
            MyApplication.app.readCardOptV2.checkCard(AidlConstantsV2.CardType.MAGNETIC.getValue(), mCheckCardCallback, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final CheckCardCallbackV2 mCheckCardCallback = new CheckCardCallbackV2Wrapper() {
        /**
         * Find magnetic card
         *
         * @param info return data，contain the following keys:
         *             <br/>cardType: card type (int)
         *             <br/>TRACK1: track 1 data (String)
         *             <br/>TRACK2: track 2 data (String)
         *             <br/>TRACK3: track 3 data (String)
         *             <br/>track1ErrorCode: track 1 error code (int)
         *             <br/>track2ErrorCode: track 2 error code (int)
         *             <br/>track3ErrorCode: track 3 error code (int)
         *             <br/> track error code is one of the following values:
         *             <ul>
         *             <li>0 - No error</li>
         *             <li>-1 - Track has no data</li>
         *             <li>-2 - Track parity check error</li>
         *             <li>-3 - Track LRC check error</li>
         *             </ul>
         */
        @Override
        public void findMagCard(Bundle info) throws RemoteException {
            addEndTime("checkCard()");
            LogUtil.e(Constant.TAG, "findMagCard,bundle:" + Utility.bundle2String(info));
            handleResult(info);
            showSpendTime();
        }

        @Override
        public void findICCard(String atr) throws RemoteException {
            addEndTime("checkCard()");
            LogUtil.e(Constant.TAG, "findICCard,atr:" + atr);
            showSpendTime();
        }

        @Override
        public void findRFCard(String uuid) throws RemoteException {
            addEndTime("checkCard()");
            LogUtil.e(Constant.TAG, "findRFCard,uuid:" + uuid);
            showSpendTime();
        }

        @Override
        public void onError(int code, String message) throws RemoteException {
            addEndTime("checkCard()");
            String error = "onError:" + message + " -- " + code;
            LogUtil.e(Constant.TAG, error);
            showToast(error);
            handleResult(null);
            showSpendTime();
        }
    };

    private void handleResult(Bundle bundle) {
        if (isFinishing()) {
            return;
        }
        handler.post(() -> {
            if (bundle == null) {
                showResult(false, "", "", "");
                return;
            }

            track1 = Utility.null2String(bundle.getString("TRACK1"));
            track1 = track1.split("\\$")[2] + track1.split("\\$")[1] + track1.split("\\$")[0];
            track1 = track1.replace("^","");

            track2 = Utility.null2String(bundle.getString("TRACK2"));
            track2 = track2.substring(30,32) +"/"+track2.substring(28,30)+"/"+track2.substring(24,28);

            track3 = Utility.null2String(bundle.getString("TRACK2"));
            track3 = track3.substring(6,19);
            //磁道错误码：0-无错误，-1-磁道无数据，-2-奇偶校验错，-3-LRC校验错
            int code1 = bundle.getInt("track1ErrorCode");
            int code2 = bundle.getInt("track2ErrorCode");
            int code3 = bundle.getInt("track3ErrorCode");
            LogUtil.e(TAG, String.format(Locale.getDefault(),
                    "track1ErrorCode:%d,track1:%s\ntrack2ErrorCode:%d,track2:%s\ntrack3ErrorCode:%d,track3:%s",
                    code1, track1, code2, track2, code3, track3));
            if ((code1 != 0 && code1 != -1) || (code2 != 0 && code2 != -1) || (code3 != 0 && code3 != -1)) {
                showResult(false, track1, track2, track3);
            } else {
                showResult(true, track1, track2, track3);
            }
            // 继续检卡
            if (!isFinishing()) {
                handler.postDelayed(this::checkCard, 500);
            }
        });
    }

    private void showResult(boolean success, String track1, String track2, String track3) {
        mTotalTime += 1;
        if (success) {
            mSuccessTime += 1;
        } else {
            mFailTime += 1;
        }
        mTvTrack1.setText(track1);
        mTvTrack2.setText(track2);
        mTvTrack3.setText(track3);

        String temp = getString(R.string.card_total) + " " + mTotalTime;
        mBtnTotal.setText(temp);
        temp = getString(R.string.card_success) + " " + mSuccessTime;
        mBtnSuccess.setText(temp);
        temp = getString(R.string.card_fail) + " " + mFailTime;
        mBtnFail.setText(temp);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        cancelCheckCard();
        super.onDestroy();
    }

    private void cancelCheckCard() {
        try {
            MyApplication.app.readCardOptV2.cancelCheckCard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}