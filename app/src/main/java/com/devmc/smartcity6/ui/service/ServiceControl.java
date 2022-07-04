package com.devmc.smartcity6.ui.service;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.devmc.smartcity6.MainActivity;
import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.Service;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.ui.FunctionActivity;
import com.devmc.smartcity6.ui.analysis.AnalysisActivity;
import com.devmc.smartcity6.ui.guide.GuideActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/7/3
 * Time: 11:02
 */
public class ServiceControl {
    public static void setList(Context context, GridView gridView, List<Service.RowsBean> rowsBeans, int column) {
        ArrayAdapter<Service.RowsBean> adapter = new ArrayAdapter<Service.RowsBean>(
                context,
                R.layout.item_service,
                R.id.t1,
                rowsBeans
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView i1;
                TextView t1;
                i1 = view.findViewById(R.id.i1);
                t1 = view.findViewById(R.id.t1);

                Service.RowsBean data = rowsBeans.get(position);

                if (data.getLink().equals("more")) {
                    Glide.with(i1)
                            .load(R.drawable.ic_baseline_more_24)
                            .error(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(i1);
                } else {
                    Glide.with(i1)
                            .load(Http.getBase(data.getImgUrl()))
                            .error(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(i1);
                }
                t1.setText(data.getServiceName());

                return view;
            }
        };
        gridView.setNumColumns(column);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service.RowsBean data = rowsBeans.get(position);
                switch (data.getLink()) {
                    case "more":
                        MainActivity.navController.navigate(R.id.n2);
                        break;
                    case "analyse/index":
                       context. startActivity(new Intent(context, AnalysisActivity.class));
                        break;
                    default:
                        FunctionActivity.start(context, data.getServiceName());
                        break;
                }
            }
        });
    }
}
