package com.devmc.smartcity6.ui.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.CResponse;
import com.devmc.smartcity6.entity.UserLogin;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.http.User;
import com.devmc.smartcity6.util.SharedPre;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {
    private EditText e1;
    private EditText e2;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        b1 = findViewById(R.id.b1);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();

                if (!s1.equals("") && !s2.equals("")) {
                    JSONObject jo = new JSONObject();
                    try {
                        jo.put("oldPassword", s1);
                        jo.put("newPassword", s2);
                        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jo.toString());

                        Http.service.resetPwd(User.getT(), body).enqueue(new CbkImp<CResponse>() {
                            @Override
                            public void onResponse(Call<CResponse> call, Response<CResponse> response) {
                                super.onResponse(call, response);
                                Http.toast(response.body().getMsg());
                                if (response.body().getCode() == 200) {
                                    SharedPre.put("p", s2);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 500);
                                } else {
                                    Http.toastError();
                                }
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Http.toast("请输入信息！");
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}