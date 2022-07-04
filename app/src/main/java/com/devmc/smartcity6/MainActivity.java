package com.devmc.smartcity6;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.devmc.smartcity6.entity.UserInfo;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.devmc.smartcity6.http.User;
import com.devmc.smartcity6.ui.guide.GuideActivity;
import com.devmc.smartcity6.util.SharedPre;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SharedPre.get("st", false)) {
            startActivity(new Intent(MainActivity.this, GuideActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupWithNavController(navView, navController);

            if (User.isLogin()) {
                Http.service.getUserInfo(User.getT()).enqueue(new CbkImp<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        super.onResponse(call, response);
                        if (response.body().getCode() == 200) {
                            User.user = response.body().getUser();
                        } else {
                            User.logout();
                        }
                    }
                });
            }
        }
    }
}