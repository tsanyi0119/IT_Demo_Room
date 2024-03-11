package com.example.it_demo_room.room;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@androidx.room.Dao
public interface Dao {
    //資料表名稱
    String tableName = "UserTable";

    //新增資料的方法
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertData(UserData userData);

    //撈取資料的方法
    @Query("SELECT * FROM " + tableName)
    Maybe<List<UserData>> selectAllData();

    //刪除資料的方法
    @Query("DELETE  FROM "+tableName)
    Completable deleteAllData();
}
