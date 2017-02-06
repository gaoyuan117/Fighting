package com.xiangying.fighting.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.jock.pickerview.R;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBManager {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "regions.db"; //保存的数据库文件名
    public static final String PACKAGE_NAME = "com.xiangying.fighting";
    public static final String DB_PATH ="data/" +
            Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME+"/databases";  //在手机里存放数据库的位置

    private SQLiteDatabase database;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    public SQLiteDatabase openDatabase(){

        return this.database;
    }

    private SQLiteDatabase openDatabase(String dbfile) {
        try {
//            Logger.v("DAtabase:" + "DB   文件是否存在   1111--》" + new File(dbfile).exists());

            if (!(new File(dbfile).exists())) { //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库


                File path = new File(DB_PATH);
                path.mkdirs();

                File file = new File(DB_PATH + "/" + DB_NAME);
                file.createNewFile();

                InputStream is = this.context.getResources().openRawResource(
                        R.raw.regions); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
//            Logger.v("DAtabase:" + "DB   文件是否存在   222--》" + new File(dbfile).exists());


            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
            return db;
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    public void closeDatabase() {
        this.database.close();
    }

}