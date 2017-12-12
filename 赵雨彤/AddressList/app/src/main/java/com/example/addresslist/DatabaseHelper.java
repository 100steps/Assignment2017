package com.example.addresslist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = "DatabaseHelper";
    //数据库版本
    private static final int VERSION = 1;

    //复写三个构造函数
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DatabaseHelper(Context context, String name) {
        this(context,name,VERSION);
    }
    public DatabaseHelper(Context context, String name, int version) {
        this(context,name,null,version);
    }

    //创建数据库时调用onCreate方法
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG,"create a database");
        //初始化创造一个student表，内有name,tel,number三个数值
        sqLiteDatabase.execSQL("create table student(name varchar(10),tel varchar(11),number varchar(12))");
    }

    //更新数据库时调用onUpgrade方法
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG,"upgrade a database");
    }
}