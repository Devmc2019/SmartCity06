package com.devmc.smartcity6.http;

import android.widget.Toast;

import com.devmc.smartcity6.CustomApplication;
import com.devmc.smartcity6.entity.CResponse;
import com.devmc.smartcity6.entity.News;
import com.devmc.smartcity6.entity.NewsCategory;
import com.devmc.smartcity6.entity.NewsDetail;
import com.devmc.smartcity6.entity.Order;
import com.devmc.smartcity6.entity.Rotation;
import com.devmc.smartcity6.entity.Service;
import com.devmc.smartcity6.entity.Upload;
import com.devmc.smartcity6.entity.UserInfo;
import com.devmc.smartcity6.entity.UserLogin;
import com.devmc.smartcity6.util.SharedPre;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/7/3
 * Time: 10:35
 */
public class Http {
    public static CommonService service = new Retrofit.Builder()
            .baseUrl(getApi())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommonService.class);

    public interface CommonService {
        @GET("api/rotation/list")
        Call<Rotation> getRotation(@Query("type") int type);

        @GET("api/service/list")
        Call<Service> getService();

        @GET("press/category/list")
        Call<NewsCategory> getNewsCategory();

        @GET("press/press/list")
        Call<News> getNews();

        @GET("press/press/list")
        Call<News> getNews(@Query("type") int type);

        @GET("press/press/list")
        Call<News> getNews(@Query("title") String title);

        @GET("press/press/{id}")
        Call<NewsDetail> getNewsDetail(@Path("id") int id);

        @POST("api/login")
        Call<UserLogin> login(@Body RequestBody body);

        @GET("api/common/user/getInfo")
        Call<UserInfo> getUserInfo(@Header("Authorization") String token);

        @PUT("api/common/user/resetPwd")
        Call<CResponse> resetPwd(@Header("Authorization") String token, @Body RequestBody body);

        @Multipart
        @POST("common/upload")
        Call<Upload> upload(@Header("Authorization") String token, @Part MultipartBody.Part body);

        @PUT("api/common/user")
        Call<CResponse> updateProfile(@Header("Authorization") String token, @Body RequestBody body);

        @GET("api/allorder/list")
        Call<Order> getOrder(@Header("Authorization") String token);
    }


    public static String getI() {
        return SharedPre.get("ip", "");
    }

    public static String getP() {
        return SharedPre.get("po", "");
    }

    public static String getBase() {
        return "http://" + getI() + ":" + getP();
    }

    public static String getBase(String s) {
        return getBase() + s;
    }

    public static String getApi() {
        return getBase() + "/prod-api/";
    }

    public static String getApi(String s) {
        return getApi() + s;
    }


    public static void toastError() {
        toast("请求错误！");
    }

    public static void toast(String s) {
        Toast.makeText(CustomApplication.context, s, Toast.LENGTH_SHORT).show();
    }
}
