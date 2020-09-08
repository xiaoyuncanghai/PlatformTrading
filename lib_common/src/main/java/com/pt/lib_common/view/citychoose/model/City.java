package com.pt.lib_common.view.citychoose.model;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author Bro0cL on 2016/1/26.
 */
public class City {
    private String name;
    private String pinyin;
    private String code;
    private String aid;

    public City(String name, String pinyin, String code) {
        this.name = name;
        this.pinyin = pinyin;
        this.code = code;
    }

    public City(String name, String pinyin, String code, String aid) {
        this.name = name;
        this.pinyin = pinyin;
        this.code = code;
        this.aid = aid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    /***
     * 获取悬浮栏文本，（#、定位、热门 需要特殊处理）
     * @return
     */
    public String getSection(){
        if (TextUtils.isEmpty(pinyin)) {
            return "#";
        } else {
            String c = pinyin.substring(0, 1);
            String pattern = Pattern.quote("" + "[a-zA-Z]");
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(c);
            if (m.matches()) {
                return c.toUpperCase();
            }
            //在添加定位和热门数据时设置的section就是‘定’、’热‘开头
            else if (TextUtils.equals(c, "定") || TextUtils.equals(c, "热"))
                return pinyin;
            else
                return "#";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
