package com.sm.sdk.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sm.sdk.myapplication.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.UUID;

public class Login extends AppCompatActivity {

    private static final String TAG = "ActivityMain";

    EditText idMain, passwordID;

    RequestQueue queue;

    private final String url = "http://one-tkig.com/product_key/my_api/read/?UID=1e55394265b11e870e08f9e72b65728c&TOKEN=f38cd84380d55c7cb0320558c998a36f&serial_no=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences("SHARED_PRES", MODE_PRIVATE);
        String FirstID = preferences.getString("FirstID", null);
        final String[] password = {preferences.getString("password", null)};

        String randomID = String.valueOf(UUID.randomUUID().getMostSignificantBits());
        if (FirstID == null) {
            FirstID = randomID.substring(1,17);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstID", FirstID);
            editor.apply();
        }

        idMain = findViewById(R.id.idMain);
        idMain.setText(FirstID);

        passwordID = findViewById(R.id.password);

        Button signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password[0] = passwordID.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("password", password[0]);
                editor.apply();

                StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Toast.makeText(Login.this, object.toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });
                queue.add(request);
            }
        });
    }
}
