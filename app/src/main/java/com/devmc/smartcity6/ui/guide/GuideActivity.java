package com.devmc.smartcity6.ui.guide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.devmc.smartcity6.MainActivity;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.R;
import com.devmc.smartcity6.util.SharedPre;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.Arrays;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private Banner banner;
    private Button b1;
    private Button b2;
    private String mIp = "";
    private String mPort = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);


        banner = findViewById(R.id.banner);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);


        // banner
        List<Integer> integers = Arrays.asList(
                R.drawable.welcome_1,
                R.drawable.welcome_2,
                R.drawable.welcome_3,
                R.drawable.welcome_4,
                R.drawable.welcome_5
        );
        banner.addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this))
                .setAdapter(new BannerImageAdapter<Integer>(integers) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer integer, int i, int i1) {
                        Glide.with(holder.imageView)
                                .load(integer)
                                .error(R.mipmap.ic_launcher)
                                .centerCrop()
                                .into(holder.imageView);
                    }
                })
                .addOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                        boolean show = i == 4;
                        b1.setVisibility(show ? View.VISIBLE : View.GONE);
                        b2.setVisibility(show ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onPageSelected(int i) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });


        // b1
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GuideActivity.this);
                builder.setTitle("网络设置");
                View view = LayoutInflater.from(GuideActivity.this).inflate(R.layout.item_guide_dialog, null, false);
                EditText e1;
                EditText e2;
                e1 = view.findViewById(R.id.e1);
                e2 = view.findViewById(R.id.e2);
                builder.setView(view);
                builder.setCancelable(false);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s1 = e1.getText().toString();
                        String s2 = e2.getText().toString();
                        if (!s1.equals("") && !s2.equals("")) {
                            mIp = s1;
                            mPort = s2;
                        } else {
                            Http.toast("请输入信息！");
                        }
                    }
                });
                builder.show();
            }
        });


        // b2
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIp.equals("") && !mPort.equals("")) {
                    SharedPre.put("ip", mIp);
                    SharedPre.put("po", mPort);
                    SharedPre.put("st", true);
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();
                } else {
                    Http.toast("请输入信息！");
                }
            }
        });
    }
}