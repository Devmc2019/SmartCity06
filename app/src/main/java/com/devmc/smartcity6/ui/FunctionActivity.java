package com.devmc.smartcity6.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.devmc.smartcity6.R;

public class FunctionActivity extends AppCompatActivity {
    private TextView t1;

    public static void start(Context context, String data) {
        Intent in = new Intent(context, FunctionActivity.class);
        in.putExtra("data", data);
        context.startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        t1 = findViewById(R.id.t1);

        String data = getIntent().getStringExtra("data");
        t1.setText(data);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(data);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}