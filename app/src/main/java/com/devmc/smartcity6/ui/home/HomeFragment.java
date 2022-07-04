package com.devmc.smartcity6.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.News;
import com.devmc.smartcity6.entity.NewsCategory;
import com.devmc.smartcity6.entity.Rotation;
import com.devmc.smartcity6.entity.Service;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.ui.news.NewsControl;
import com.devmc.smartcity6.ui.news.NewsDetailActivity;
import com.devmc.smartcity6.ui.news.NewsSearchActivity;
import com.devmc.smartcity6.ui.service.ServiceControl;
import com.devmc.smartcity6.widget.CGridView;
import com.devmc.smartcity6.widget.CListView;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private EditText search;
    private Banner banner;
    private CGridView services;
    private TabLayout tabs;
    private CListView news;
    private List<NewsCategory.DataBean> categoryList;
    private List<News.RowsBean> newsList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        search = view.findViewById(R.id.search);
        banner = view.findViewById(R.id.banner);
        services = view.findViewById(R.id.services);
        tabs = view.findViewById(R.id.tabs);
        news = view.findViewById(R.id.news);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // banner
        Http.service.getRotation(2).enqueue(new CbkImp<Rotation>() {
            @Override
            public void onResponse(Call<Rotation> call, Response<Rotation> response) {
                super.onResponse(call, response);
                if (response.body().getCode() == 200) {
                    if (isAdded()) {
                        banner.addBannerLifecycleObserver(requireActivity())
                                .setIndicator(new CircleIndicator(requireActivity()))
                                .setAdapter(new BannerImageAdapter<Rotation.RowsBean>(response.body().getRows()) {
                                    @Override
                                    public void onBindView(BannerImageHolder holder, Rotation.RowsBean rowsBean, int i, int i1) {
                                        Glide.with(holder.imageView)
                                                .load(Http.getBase(rowsBean.getAdvImg()))
                                                .error(R.mipmap.ic_launcher)
                                                .centerCrop()
                                                .into(holder.imageView);
                                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                NewsDetailActivity.start(requireContext(), response.body().getRows().get(i).getTargetId());
                                            }
                                        });
                                    }
                                });
                    }
                } else {
                    Http.toastError();
                }
            }
        });


        // service
        Http.service.getService().enqueue(new CbkImp<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                super.onResponse(call, response);
                if (response.body().getCode() == 200) {

                    List<Service.RowsBean> rowsBeans = response.body().getRows();
                    Service.RowsBean more = new Service.RowsBean();
                    more.setServiceName("更多服务");
                    more.setLink("more");
                    rowsBeans = rowsBeans.subList(0, 7);
                    rowsBeans.add(more);

                    if (isAdded()) {
                        ServiceControl.setList(requireActivity(), services, rowsBeans, 4);
                    }

                } else {
                    Http.toastError();
                }
            }
        });


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

        // search
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String s = v.getText().toString();
                    if (!s.equals("")) {
                        NewsSearchActivity.start(requireContext(), s);
                        v.setText("");
                        return true;
                    } else {
                        Http.toast("请输入信息！");
                    }
                }
                return false;
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
            NewsControl.setList(requireActivity(), news, rowsBeans.subList(0, Math.min(6, rowsBeans.size())));
        }

    }
}