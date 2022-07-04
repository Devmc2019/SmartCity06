package com.devmc.smartcity6.ui.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.News;
import com.devmc.smartcity6.entity.Order;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.http.User;

import retrofit2.Call;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        list = findViewById(R.id.list);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // ORDER
        Http.service.getOrder(User.getT()).enqueue(new CbkImp<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                super.onResponse(call, response);
                if (response.body().getCode() == 200) {
                    ArrayAdapter<Order.RowsBean> adapter = new ArrayAdapter<Order.RowsBean>(
                            OrderActivity.this,
                            R.layout.item_order,
                            R.id.title,
                            response.body().getRows()
                    ) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView title;
                            TextView sub;
                            TextView t1;
                            TextView t2;
                            TextView t3;
                            TextView t4;

                            title = view.findViewById(R.id.title);
                            sub = view.findViewById(R.id.sub);
                            t1 = view.findViewById(R.id.t1);
                            t2 = view.findViewById(R.id.t2);
                            t3 = view.findViewById(R.id.t3);
                            t4 = view.findViewById(R.id.t4);

                            Order.RowsBean data = response.body().getRows().get(position);

                            title.setText(data.getOrderTypeName());
                            sub.setText(data.getOrderStatus());
                            t1.setText(data.getOrderNo());
                            t2.setText(data.getPayTime());
                            t3.setText(data.getName());
                            t4.setText(data.getAmount() + "元");

                            return view;
                        }
                    };
                    list.setAdapter(adapter);


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