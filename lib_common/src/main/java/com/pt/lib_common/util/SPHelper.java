package com.pt.lib_common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.pt.lib_common.base.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Set;

/**
 * 类名称：SharedPreferencesHelper
 * 类描述：SharedPreferences缓存类
 */
public class SPHelper {
	private final static String APP_SHP_KEY = "yuu1flsd_info";
	private final static String PERSONAL_SHP_KEY = "yuu1flsd_user";

	public static Editor getEditor(boolean ispersonal) {
		return ispersonal ? BaseApplication.getInstance()
				.getSharedPreferences(PERSONAL_SHP_KEY, Context.MODE_PRIVATE)
				.edit() : BaseApplication.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE).edit();
	}

	public static SharedPreferences getSharedPreferences(boolean ispersonal) {
		return ispersonal ? BaseApplication.getInstance().getSharedPreferences(
				PERSONAL_SHP_KEY, Context.MODE_PRIVATE) : BaseApplication.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE);
	}

	public static boolean putString(String key, String value) {
		return getEditor(false).putString(key, value).commit();
	}
	public static boolean putString(String key, String value, boolean ispersonal) {
		return getEditor(ispersonal).putString(key, value).commit();
	}

	public static boolean putStringSet(String key, Set<String> value) {
		return getEditor(false).putStringSet(key,value).commit();
	}

	public static Set<String> getStringSet(String key, Set<String> defValue) {
		return getSharedPreferences(false).getStringSet(key,defValue);
	}

	public static String getString(String key, String defValue) {
		return getSharedPreferences(false).getString(key, defValue);
	}
	public static String getString(String key, String defValue, boolean ispersonal) {
		return getSharedPreferences(ispersonal).getString(key, defValue);
	}

	public static boolean putBoolean(String key, Boolean value) {
		return getEditor(false).putBoolean(key, value).commit();
	}

	public static Boolean getBoolean(String key, Boolean _default) {
		return getSharedPreferences(false).getBoolean(key, _default);
	}

	public static boolean remove(String key) {
		return getEditor(false).remove(key).commit();
	}

	public static boolean remove(String key , boolean ispersonal) {
		return getEditor(ispersonal).remove(key).commit();
	}

	public static boolean clearall() {
		getEditor(true).clear().commit();
		return getEditor(false).clear().commit();
	}

	public static boolean clearall(boolean ispersonal) {
		return getEditor(ispersonal).clear().commit();
	}

	public static boolean putInt(String key, Integer value) {
		return getEditor(false).putInt(key, value).commit();
	}

	public static boolean putInt(String key, Integer value, boolean ispersonal) {
		return getEditor(ispersonal).putInt(key, value).commit();
	}

	public static Integer getInt(String key, Integer value) {
		return getSharedPreferences(false).getInt(key, value);
	}

	public static Integer getInt(String key, Integer value, boolean ispersonal) {
		return getSharedPreferences(ispersonal).getInt(key, value);
	}

	public static boolean putLong(String key, Long value) {
		return getEditor(false).putLong(key, value).commit();
	}

	public static Long getLong(String key, Long value) {
		return getSharedPreferences(false).getLong(key, value);
	}

	public static boolean putObject(String key, Object object)
			throws IOException {
		return putObject(key, object, false);
	}

	public static boolean putObject(String key, Object object,
                                    boolean ispersonal) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		String objectBase64 = new String(Base64.encode(baos.toByteArray(),
				Base64.DEFAULT));
		return ispersonal ? BaseApplication.getInstance()
				.getSharedPreferences(PERSONAL_SHP_KEY, Context.MODE_PRIVATE)
				.edit().putString(key, objectBase64).commit() : BaseApplication
				.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE).edit()
				.putString(key, objectBase64).commit();
	}

	public static Object getObject(String key, Object _default)
			throws StreamCorruptedException, IOException,
            ClassNotFoundException {
		return getObject(key, _default, false);
	}

	public static Object getObject(String key, Object _default,
                                   boolean ispersonal) throws StreamCorruptedException, IOException,
            ClassNotFoundException {
		String objectBase64 = ispersonal ? BaseApplication.getInstance()
				.getSharedPreferences(PERSONAL_SHP_KEY, Context.MODE_PRIVATE)
				.getString(key, null) : BaseApplication.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE)
				.getString(key, null);
		if (objectBase64 != null) {
			byte[] bytearray = Base64.decode(objectBase64, Base64.DEFAULT);
			ByteArrayInputStream bis = new ByteArrayInputStream(bytearray);
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();
		} else {
			return _default;
		}
	}

	public static Drawable getDrawable(String key) {
		Context context = BaseApplication.getInstance();
		SharedPreferences shp = context.getSharedPreferences(APP_SHP_KEY,
				Context.MODE_PRIVATE);
		String imageBase64 = shp.getString(key, null);
		if (imageBase64 != null) {
			byte[] bytearray = Base64.decode(imageBase64, Base64.DEFAULT);
			ByteArrayInputStream bis = new ByteArrayInputStream(bytearray);
			Drawable drawable = Drawable.createFromStream(bis, "image");
			return drawable;
		} else {
			return null;
		}
	}

	public synchronized static void setDrawable(String key, Drawable headicon)
			throws IOException {
		Context context = BaseApplication.getInstance();
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		((BitmapDrawable) headicon).getBitmap().compress(CompressFormat.JPEG,
				50, bo);
		byte[] bytearray = bo.toByteArray();
		String imageBase64 = new String(
				Base64.encode(bytearray, Base64.DEFAULT));
		Editor editor = context.getSharedPreferences(APP_SHP_KEY,
				Context.MODE_PRIVATE).edit();
		editor.putString(key, imageBase64);
		editor.commit();
		bo.close();
	}

}
