package com.pt.lib_common.bean;

/**
 * Author: Jeffer on 2017/7/3 10:33.
 * Email: jeffer7150@163.com
 * Description:
 */

public class UserBean {
        /**
         * uid : 318520862
         * detail_address :
         * hpic : http://192.168.8.121//data/avatar/avatar_6.png
         * nickname : 游侠318520862
         * qq :
         * receiver : yylc13157032394
         * sex : 0
         * phone : 13157032394
         * username : yylc13157032394
         * score : 200
         * expend : 200
         * code : 112370
         * invite_num : 0
         * today_profit : 200
         * token : 0cefDtsbX8AyyDcIaC7XWoGDn1MLEZBbyI9CyZOXPDXbAUTzn7k884ZBsx/RthxUDDiQTOm5vohD02w
         */
    private boolean hasLogin = false;
    private String uid;

    private String detail_address;

    private String birthday;

    private String hpic;

    private String nickname;

    private String qq;

    private String receiver;

    private String sex;

    private String phone;

    private String username;

    private String score;

    private String expend;

    private String address;

    private String alipay;

    private String token;


    public UserBean() {
    }

    public UserBean(String uid, String birthday , String detail_address, String hpic, String nickname, String sex, String phone, String username, String token) {
        this.uid = uid;
        this.birthday = birthday;
        this.detail_address = detail_address;
        this.hpic = hpic;
        this.nickname = nickname;
        this.sex = sex;
        this.phone = phone;
        this.username = username;
        this.token = token;
    }

    public boolean isHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHpic() {
        return hpic;
    }

    public void setHpic(String hpic) {
        this.hpic = hpic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExpend() {
        return expend;
    }

    public void setExpend(String expend) {
        this.expend = expend;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
