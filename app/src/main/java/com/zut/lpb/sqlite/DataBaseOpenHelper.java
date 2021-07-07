package com.zut.lpb.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    //数据库版本号
    private static Integer Version = 1;

    public DataBaseOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseOpenHelper(Context context,String name,int version)
    {
        this(context,name,null,version);
    }

    public  DataBaseOpenHelper (Context context,String name)
    {
        this(context, name, Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        String sql = "create table user(" +
                "id integer primary key autoincrement," +
                "name varchar(64)," +
                "password varchar(64))";
        db.execSQL(sql);
        //创建视频表
        String videoSql = "create table video(" +
                "id integer primary key autoincrement," +
                "name varchar(255)," +
                "type varchar(2)," + //0为视频，1为音频
                "create_time varchar(255)," +
                "video_cover varchar(255)," + //视频封面的链接
                "url varchar(255))";
        db.execSQL(videoSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
