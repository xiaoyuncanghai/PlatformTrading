package com.pt.lib_common.view.citychoose.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.apkfuns.logutils.LogUtils;
import com.github.promeg.pinyinhelper.Pinyin;
import com.pt.lib_common.view.citychoose.model.City;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.pt.lib_common.view.citychoose.db.DBConfig.COLUMN_C_AID;
import static com.pt.lib_common.view.citychoose.db.DBConfig.COLUMN_C_CODE;
import static com.pt.lib_common.view.citychoose.db.DBConfig.COLUMN_C_NAME;
import static com.pt.lib_common.view.citychoose.db.DBConfig.COLUMN_C_PID;
import static com.pt.lib_common.view.citychoose.db.DBConfig.DB_NAME_V1;
import static com.pt.lib_common.view.citychoose.db.DBConfig.LATEST_DB_NAME;
import static com.pt.lib_common.view.citychoose.db.DBConfig.TABLE_NAME;

/**
 * Author Bro0cL on 2016/1/26.
 */
public class DBManager {
    private static final int BUFFER_SIZE = 1024;

    private String DB_PATH;
    private Context mContext;

    public DBManager(Context context) {
        this.mContext = context;
        DB_PATH = File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + context.getPackageName() + File.separator + "databases" + File.separator;
        copyDBFile();
    }

    private void copyDBFile(){
        File dir = new File(DB_PATH);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //如果旧版数据库存在，则删除
        File dbV1 = new File(DB_PATH + DB_NAME_V1);
        if (dbV1.exists()){
            dbV1.delete();
        }
        //创建新版本数据库
        File dbFile = new File(DB_PATH + LATEST_DB_NAME);
        if (!dbFile.exists()){
            InputStream is;
            OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(LATEST_DB_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = is.read(buffer, 0, buffer.length)) > 0){
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<City> getAllCities(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + LATEST_DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        List<City> result = new ArrayList<>();
        City city;
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_C_NAME));
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_C_CODE));
            String pinyin = Pinyin.toPinyin(name, "");
            city = new City(name, pinyin, code);
            result.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(result, new CityComparator());
        LogUtils.d("result = "+result.size());
        return result;
    }

    public List<City> searchCity(final String keyword){
        String sql = "select * from " + TABLE_NAME + " where "
                + COLUMN_C_NAME + " like ? ";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + LATEST_DB_NAME, null);
        Cursor cursor = db.rawQuery(sql, new String[]{"%"+keyword+"%"});
        List<City> result = new ArrayList<>();
        while (cursor.moveToNext()){
            String parent_id = cursor.getString(cursor.getColumnIndex(COLUMN_C_PID));
            //当parent_id不等于0再加到集合里面去
            if (!parent_id.equals("0")) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_C_NAME));
                String pinyin = Pinyin.toPinyin(name, "");
                String code = cursor.getString(cursor.getColumnIndex(COLUMN_C_CODE));
                City city = new City(name, pinyin, code);
                result.add(city);
            }
        }
        cursor.close();
        db.close();
        CityComparator comparator = new CityComparator();
        Collections.sort(result, comparator);
        return result;
    }

    /**
     * 查找省的消息
     * @return
     */
    public List<City> getCityByParentId(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + LATEST_DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from addr where PARENT_ID=?", new String[]{String.valueOf(0)});
        List<City> result = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_C_NAME));
            String pinyin = Pinyin.toPinyin(name, "");
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_C_CODE));
            String aid = cursor.getString(cursor.getColumnIndex(COLUMN_C_AID));
            City city = new City(name, pinyin, code, aid);
            result.add(city);
        }
        cursor.close();
        db.close();
        CityComparator comparator = new CityComparator();
        Collections.sort(result, comparator);
        return result;
    }

    /**
     * 通过省查找对应下面市的消息
     * @return
     */
    public List<City> getCityByProvince(){
        String[] parent_id = new String[getCityByParentId().size()];
        //获取到省份, 得到省份下的所有的城市
        StringBuilder builder = new StringBuilder(COLUMN_C_PID + " in (");
        for (int i = 0; i < getCityByParentId().size(); i++) {
            parent_id[i] = getCityByParentId().get(i).getAid();
            if (i != getCityByParentId().size()-1) {
                builder.append("? ,");
            } else {
                builder.append("?");
            }
        }
        builder.append(")");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + LATEST_DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from addr where "+ builder, parent_id);
        List<City> result = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_C_NAME));
            String pinyin = Pinyin.toPinyin(name, "");
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_C_CODE));
            String aid = cursor.getString(cursor.getColumnIndex(COLUMN_C_AID));
            City city = new City(name, pinyin, code, aid);
            result.add(city);
        }
        cursor.close();
        db.close();
        CityComparator comparator = new CityComparator();
        Collections.sort(result, comparator);
        return result;
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<City>{
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }
}
