package com.devmc.smartcity6.entity;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/7/3
 * Time: 16:19
 */
public class CResponse {
    /**
     * msg : 操作成功
     * code : 200
     */

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
