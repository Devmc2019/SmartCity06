package com.devmc.smartcity6.http;

import com.devmc.smartcity6.entity.UserInfo;
import com.devmc.smartcity6.util.SharedPre;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/7/3
 * Time: 16:04
 */
public class User {
    public static UserInfo.UserBean user;

    public static String getU() {
        return SharedPre.get("u", "");
    }

    public static String getP() {
        return SharedPre.get("p", "");
    }

    public static String getT() {
        return SharedPre.get("t", "");
    }

    public static boolean isLogin() {
        return !getU().equals("") && !getP().equals("") && !getT().equals("");
    }

    public static  void  logout(){
        SharedPre.put("u" ,"");
        SharedPre.put("p" ,"");
        SharedPre.put("t" ,"");
    }
}
