package com.pt.lib_common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author: Jeffer on 2018/4/17 17:06.
 * Email: jeffer7150@163.com
 * Description:
 */

public class CommonLoginUtil {

        public static void switchTab(int index){
            Class dexfiletest2 = null;  //初始化类：包名+类名
            try {
                dexfiletest2 = Class.forName("com.yuu1.fulisudi.MainActDelegate");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Method method = null;
            try {
                method = dexfiletest2.getMethod("switchTab",int.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            Object methodobject1 = null;  //调用静态方法则不需要实例化这步
            try {
                methodobject1 = dexfiletest2.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                method.invoke(methodobject1,index); //反射调用
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
}
