package com.devmc.smartcity6.ui.news;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.NewsDetail;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;

import retrofit2.Call;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {
    private WebView web;


    public static void start(Context context, int data) {
        Intent in = new Intent(context, NewsDetailActivity.class);
        in.putExtra("data", data);
        context.startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        web = findViewById(R.id.web);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        int data = getIntent().getIntExtra("data", 0);
        if (data != 0) {
            Http.service.getNewsDetail(data).enqueue(new CbkImp<NewsDetail>() {
                @Override
                public void onResponse(Call<NewsDetail> call, Response<NewsDetail> response) {
                    super.onResponse(call, response);

                    if (response.body().getCode() == 200) {

                        if (actionBar != null) {
                            actionBar.setTitle(response.body().getData().getTitle());
                        }
                        String html ="<html><head><style>img{width:100%}</style></head><body>" + response.body().getData().getContent() + "</body></html>";
                        web.loadData(html.replaceAll("/prod-api/", Http.getApi()), null, null);

                    } else {
                        Http.toastError();
                    }
                }
            });
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}