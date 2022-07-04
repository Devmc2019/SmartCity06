package com.devmc.smartcity6.entity;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/7/3
 * Time: 16:33
 */
public class Upload {

    /**
     * code : 200
     * fileName : test.txt
     * url : /profile/upload/file/test.txt
     * msg : 操作成功
     */

    private int code;
    private String fileName;
    private String url;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
