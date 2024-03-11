package com.example.it_demo_room.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//設定表格名稱
@Entity(tableName = "UserTable")
public class UserData {

    //設置自動累加id
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String phone;

    public UserData(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    //建立getter and setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
