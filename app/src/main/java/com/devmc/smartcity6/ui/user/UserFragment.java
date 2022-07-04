package com.devmc.smartcity6.ui.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devmc.smartcity6.MainActivity;
import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.UserInfo;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.http.User;
import com.devmc.smartcity6.ui.guide.GuideActivity;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private static final String TAG = "UserFragment";
    private LinearLayout ct;
    private ImageView i1;
    private TextView t1;
    private ListView list;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);


        ct = view.findViewById(R.id.ct);
        i1 = view.findViewById(R.id.i1);
        t1 = view.findViewById(R.id.t1);
        list = view.findViewById(R.id.list);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // list
        List<String> strings = Arrays.asList(
                "个人信息",
                "订单列表",
                "修改密码",
                "意见反馈",
                "退出登录"
        );
        list.setAdapter(new ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                strings
        ));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (User.isLogin()) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(requireContext(), ProfileActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(requireContext(), OrderActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(requireContext(), PasswordActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(requireContext(), FeedbackActivity.class));
                            break;
                        case 4:
                            User.logout();
                            Http.toast("退出登录成功！");
                            onResume();
                            break;
                    }
                } else {
                    Http.toast("请登录！");
                }
            }
        });


        // login
        ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!User.isLogin()) {
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!User.isLogin()) {
            Glide.with(i1)
                    .load(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .circleCrop()
                    .into(i1);
            t1.setText("点击登录");
        } else if (User.user == null) {
            Http.service.getUserInfo(User.getT()).enqueue(new CbkImp<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    super.onResponse(call, response);
                    if (response.body().getCode() == 200) {
                        User.user = response.body().getUser();
                        onResume();
                    } else {
                        Http.toastError();
                        User.logout();
                    }
                }
            });
        } else {
            Glide.with(i1)
                    .load(Http.getApi(User.user.getAvatar()))
                    .error(R.mipmap.ic_launcher)
                    .circleCrop()
                    .into(i1);
            t1.setText(User.user.getNickName() == null ? User.user.getUserName() : User.user.getNickName());
        }

    }
}