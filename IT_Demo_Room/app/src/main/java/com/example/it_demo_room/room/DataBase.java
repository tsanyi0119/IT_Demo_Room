package com.example.it_demo_room.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class},version = 1,exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    //資料庫名稱
    public static final String DB = "UserData.db";
    private static volatile DataBase instance;

    public static DataBase getInstance(Context context){
        //確認是否存在，避免重複消耗資源
        //fallbackToDestructiveMigration()，會直接把table重建
         if (instance==null){
             instance = Room.databaseBuilder(context,DataBase.class,DB)
                     .fallbackToDestructiveMigration()
                     .build();
         }
         return  instance;
    }

    abstract public Dao getDao();
}
