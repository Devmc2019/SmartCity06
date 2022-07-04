package com.devmc.smartcity6.ui.news;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.devmc.smartcity6.MainActivity;
import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.News;
import com.devmc.smartcity6.entity.Service;
import com.devmc.smartcity6.http.Http;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/7/3
 * Time: 11:02
 */
public class NewsControl {
    public static void setList(Context context, ListView listView, List<News.RowsBean> rowsBeans) {
        ArrayAdapter<News.RowsBean> adapter = new ArrayAdapter<News.RowsBean>(
                context,
                R.layout.item_news,
                R.id.t1,
                rowsBeans
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView i1;
                TextView t1;
                TextView t2;
                TextView t3;
                TextView t4;
                i1 = view.findViewById(R.id.i1);
                t1 = view.findViewById(R.id.t1);
                t2 = view.findViewById(R.id.t2);
                t3 = view.findViewById(R.id.t3);
                t4 = view.findViewById(R.id.t4);

                News.RowsBean data = rowsBeans.get(position);

                Glide.with(i1)
                        .load(Http.getBase(data.getCover()))
                        .error(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(i1);
                t1.setText(data.getTitle());
                t2.setText(
                        data.getContent()
                                .replaceAll("<p>", "")
                                .replaceAll("</p>", "")
                                .replaceAll("<br>", "")
                                .replaceAll("<br/>", "")
                                .replaceAll("<strong>", "")
                                .replaceAll("&nbsp;", "")
                );
                t3.setText(data.getPublishDate());
                t4.setText(data.getCommentNum() + "评");

                return view;
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News.RowsBean data = rowsBeans.get(position);
                NewsDetailActivity.start(context, data.getId());
            }
        });
    }
}
