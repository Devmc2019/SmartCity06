package com.devmc.smartcity6.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.CResponse;
import com.devmc.smartcity6.entity.Upload;
import com.devmc.smartcity6.entity.UserLogin;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.http.User;
import com.devmc.smartcity6.util.SharedPre;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {


    private ImageView iv1;
    private EditText e1;
    private EditText e2;
    private EditText e3;
    private RadioButton rb1;
    private RadioButton rb2;
    private Button b1;
    private String avatar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        iv1 = findViewById(R.id.iv1);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        b1 = findViewById(R.id.b1);

        if (User.user != null) {
            Glide.with(iv1)
                    .load(Http.getApi(User.user.getAvatar()))
                    .error(R.mipmap.ic_launcher)
                    .circleCrop()
                    .into(iv1);
            e1.setText(User.user.getUserName());
            e2.setText(User.user.getNickName());
            e3.setText(User.user.getPhonenumber());
            if (User.user.getSex().equals("0")) {
                rb1.setChecked(true);
            } else {
                rb2.setChecked(true);
            }
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e2.getText().toString();
                String s2 = e3.getText().toString();

                if (!s1.equals("") && !s2.equals("")) {
                    JSONObject jo = new JSONObject();
                    try {
                        jo.put("nickName", s1);
                        jo.put("phonenumber", s2);
                        String sex = rb1.isChecked() ? "0" : "1";
                        jo.put("sex", sex);
                        if (!avatar.equals("")) {
                            jo.put("avatar", avatar);
                        }

                        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jo.toString());

                        Http.service.updateProfile(User.getT(), body).enqueue(new CbkImp<CResponse>() {
                            @Override
                            public void onResponse(Call<CResponse> call, Response<CResponse> response) {
                                super.onResponse(call, response);
                                Http.toast(response.body().getMsg());
                                if (response.body().getCode() == 200) {
                                    User.user.setSex(sex);
                                    User.user.setNickName(s1);
                                    User.user.setPhonenumber(s2);
                                    if (!avatar.equals("")) {
                                        User.user.setAvatar(avatar);
                                    }


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

        // iv1
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            File file = new File(getExternalCacheDir(), "tmp.png");
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
                bos.close();

                RequestBody rb = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), rb);

                Http.service.upload(User.getT(), body).enqueue(new CbkImp<Upload>() {
                    @Override
                    public void onResponse(Call<Upload> call, Response<Upload> response) {
                        super.onResponse(call, response);
                        Http.toast(response.body().getMsg());
                        if (response.body().getCode() == 200) {

                            avatar = response.body().getFileName();
                            Glide.with(iv1)
                                    .load(Http.getApi(avatar))
                                    .error(R.mipmap.ic_launcher)
                                    .circleCrop()
                                    .into(iv1);

                        } else {
                            Http.toastError();
                        }

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}