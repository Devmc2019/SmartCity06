package com.devmc.smartcity6.ui.news;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.News;
import com.devmc.smartcity6.entity.NewsCategory;
import com.devmc.smartcity6.entity.Rotation;
import com.devmc.smartcity6.entity.Service;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.ui.service.ServiceControl;
import com.devmc.smartcity6.widget.CGridView;
import com.devmc.smartcity6.widget.CListView;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    private TabLayout tabs;
    private ListView news;
    private List<NewsCategory.DataBean> categoryList;
    private List<News.RowsBean> newsList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        tabs = view.findViewById(R.id.tabs);
        news = view.findViewById(R.id.news);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // tabs
        Http.service.getNewsCategory().enqueue(new CbkImp<NewsCategory>() {
            @Override
            public void onResponse(Call<NewsCategory> call, Response<NewsCategory> response) {
                super.onResponse(call, response);
                if (response.body().getCode() == 200) {
                    categoryList = response.body().getData();
                    for (NewsCategory.DataBean item : categoryList) {
                        tabs.addTab(tabs.newTab().setText(item.getName()));
                    }
                    tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            setNews(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });


                } else {
                    Http.toastError();
                }
            }
        });


        // news
        Http.service.getNews().enqueue(new CbkImp<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                super.onResponse(call, response);
                if (response.body().getCode() == 200) {
                    newsList = response.body().getRows();
                    setNews(0);
                } else {
                    Http.toastError();
                }
            }
        });


    }

    private void setNews(int position) {
        List<News.RowsBean> rowsBeans = new ArrayList<>();

        for (News.RowsBean item : newsList) {
            if (Integer.parseInt(item.getType()) == categoryList.get(position).getId()) {
                rowsBeans.add(item);
            }
        }

        if (isAdded()) {
            NewsControl.setList(requireActivity(), news, rowsBeans);
        }

    }
}