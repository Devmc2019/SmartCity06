package com.devmc.smartcity6.entity;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/6/28
 * Time: 17:15
 */
public class UserInfo {

    /**
     * msg : 操作成功
     * code : 200
     * user : {"userId":1115869,"userName":"userme","nickName":"逐梦me","email":"userme@163.com","phonenumber":"15840616810","sex":"0","avatar":"/profile/upload/2022/03/18/60b3b2f1-126d-4d94-a600-2a1504c4f3dd.jpg","idCard":"21088219980705165","balance":1000,"score":1000}
     */

    private String msg;
    private int code;
    /**
     * userId : 1115869
     * userName : userme
     * nickName : 逐梦me
     * email : userme@163.com
     * phonenumber : 15840616810
     * sex : 0
     * avatar : /profile/upload/2022/03/18/60b3b2f1-126d-4d94-a600-2a1504c4f3dd.jpg
     * idCard : 21088219980705165
     * balance : 1000
     * score : 1000
     */

    private UserBean user;

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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        private int userId;
        private String userName;
        private String nickName;
        private String email;
        private String phonenumber;
        private String sex;
        private String avatar;
        private String idCard;
        private int balance;
        private int score;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
