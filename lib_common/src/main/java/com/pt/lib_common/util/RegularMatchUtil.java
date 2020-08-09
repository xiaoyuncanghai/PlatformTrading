package com.pt.lib_common.util;

import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Author: Jeffer on 2017/4/5 17:19.
 * Email: jeffer7150@163.com
 * Description:
 */

public class RegularMatchUtil {

    /**
     * 验证密码
     * @param str
     * @return
     */
    public static boolean matchPwd(String str){
        String reg = "[a-zA-Z0-9_]{6,16}";
        if(TextUtils.isEmpty(str))
            return false;
        if (str.matches(reg))
            return true;
        else
            return false;
    }

    /**
     * 验证用户名
     * @param str
     * @return
     */
    public static boolean matchUsername(String str){
        String reg = "[a-zA-Z0-9_@.]{6,30}";
        if(TextUtils.isEmpty(str))
            return false;
        if (str.matches(reg))
            return true;
        else
            return false;
    }

    /**
     * 验证字符串是否为空
     * @param strList
     * @return
     */
    public static boolean matchStringEmpty(String... strList){
        if (strList==null){
            return true;
        }
        for (String s : strList) {
            if (TextUtils.isEmpty(s)){
                return true;
            }
        }
        return false;
    }


    /**
     * 验证手机号
     * @param phoneNum
     * @return
     */
    public static boolean matchPhone(String phoneNum){
        String reg = "^1[0-9]{10}$";
        if(TextUtils.isEmpty(phoneNum))
            return false;
        if (phoneNum.matches(reg))
            return true;
        else
            return false;
    }



    /**
     * 简化金额显示
     * @param moneyNumber
     * @return
     */
    public static String simplifyMoney(String moneyNumber){
        String money;
        double dMoney = Double.parseDouble(moneyNumber);
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.DOWN);
        if (dMoney>10000){
            money = nf.format(dMoney/10000) +" w";
        }else if (dMoney>1000 && dMoney<10000){
            money = nf.format(dMoney/1000) +" k";
        }else{
            money = nf.format(dMoney);
        }
        return money;
    }

    /**
     * 隐藏手机号
     * @param phone
     * @return
     */
    public static String hidePhone(String phone){
        if (TextUtils.isEmpty(phone)){
            return "";
        }else{
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
    }

    /**
     * 隐藏邮箱
     * @param email
     * @return
     */
    public static String hideEmail(String email){
        if (TextUtils.isEmpty(email)){
            return "";
        }else{
            return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
        }
    }

    /**
     * 隐藏银行卡号
     * @param bank
     * @return
     */
    public static String hideBank(String bank){
        if (TextUtils.isEmpty(bank)){
            return "";
        }else{
            return bank.substring(0,6)+"***"+bank.substring(bank.length()-4);
        }
    }

    /**
     * 格式化U币
     * @param text
     * @return
     */
    public static String formatTotalMoney(String text) {
        double value = Double.parseDouble(text);
        NumberFormat nf = NumberFormat.getNumberInstance();

        return nf.format(value) + "";
    }

    public static String formatDiskSizeUnit(String byteSize){
        String money;
        double dMoney = Double.parseDouble(byteSize);
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.DOWN);
        if (dMoney>=1024 && dMoney<1048576){
            money = nf.format(dMoney/1024) +" KB";
        }else if (dMoney>=1048576 && dMoney<1073741824){
            money = nf.format(dMoney/1048576) +" MB";
        }else if (dMoney<=1024){
            money = nf.format(dMoney)+" B";
        }else{
            money = nf.format(dMoney/1073741824)+" GB";
        }
        return money;
    }

}
