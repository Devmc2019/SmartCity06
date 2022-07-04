package com.devmc.smartcity6.ui.news;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.News;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.ui.FunctionActivity;

import retrofit2.Call;
import retrofit2.Response;

public class NewsSearchActivity extends AppCompatActivity {
    private ListView news;

    public static void start(Context context, String data) {
        Intent in = new Intent(context, NewsSearchActivity.class);
        in.putExtra("data", data);
        context.startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);

        news = findViewById(R.id.news);

        String data = getIntent().getStringExtra("data");


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(data);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // search
        Http.service.getNews(data).enqueue(new CbkImp<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                super.onResponse(call, response);
                if (response.body().getCode() == 200) {
                    NewsControl.setList(NewsSearchActivity.this, news, response.body().getRows());
                } else {
                    Http.toastError();
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