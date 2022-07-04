package com.devmc.smartcity6.ui.service;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
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
import com.devmc.smartcity6.ui.news.NewsControl;
import com.devmc.smartcity6.ui.news.NewsSearchActivity;
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
 * Use the {@link ServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragment extends Fragment {
    private GridView services;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServiceFragment() {
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
    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        services = view.findViewById(R.id.services);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // service
        Http.service.getService().enqueue(new CbkImp<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                super.onResponse(call, response);
                if (response.body().getCode() == 200) {

                    List<Service.RowsBean> rowsBeans = response.body().getRows();

                   if (isAdded()){
                       ServiceControl.setList(requireActivity(), services, rowsBeans, 4);
                   }

                } else {
                    Http.toastError();
                }
            }
        });

    }

}