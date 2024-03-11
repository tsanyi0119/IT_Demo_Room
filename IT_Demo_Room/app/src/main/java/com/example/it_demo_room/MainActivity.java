package com.example.it_demo_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.it_demo_room.adapter.RecyclerViewAdapter;
import com.example.it_demo_room.room.DataBase;
import com.example.it_demo_room.room.UserData;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    Button button_insert, button_delete;
    EditText edit_name, edit_phone;
    UserData userData;
    List<UserData> userDataResponse = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設置資料庫監視工具
        Stetho.initializeWithDefaults(this);

        button_insert = findViewById(R.id.button_insert);
        button_delete = findViewById(R.id.button_delete);
        edit_name = findViewById(R.id.edit_name);
        edit_phone = findViewById(R.id.edit_phone);
        recyclerView = findViewById(R.id.recyclerview);

        //設定 recyclerView 的 Adapter 和 Manager
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        //設置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加底線樣式
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //onCreate時先抓取本機資料
        getRoomData();

        //新增按鈕點擊事件
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userData = new UserData(edit_name.getText().toString(), edit_phone.getText().toString());
                if(edit_name.getText().toString().matches("") != true && edit_phone.getText().toString().matches("") != true){
                    insertRoomData(userData);
                }
            }
        });

        //刪除按鈕點擊事件
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRoomData();
            }
        });

    }

    //新增資料
    public void insertRoomData(UserData userData) {
        DataBase.getInstance(MainActivity.this).getDao().insertData(userData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        //新增資料成功
                        //新增成功後清空輸入框
                        edit_name.setText("");
                        edit_phone.setText("");
                        //重新獲取資料
                        getRoomData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //新增資料失敗
                    }
                });
    }

    //刪除資料
    public void deleteRoomData() {
        DataBase.getInstance(MainActivity.this).getDao().deleteAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        //刪除資料成功
                        //重新獲取資料
                        getRoomData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //刪除資料失敗
                    }
                });
    }

    //獲取資料
    public void getRoomData() {
        DataBase.getInstance(MainActivity.this).getDao().selectAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<UserData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<UserData> userDataList) {
                        //獲取資料成功
                        userDataResponse = userDataList;
                        //將userDataList傳入recyclerViewAdapter
                        recyclerViewAdapter.setRoomDataList(userDataList);
                        //刷新RecyclerView資料
                        recyclerViewAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                        //獲取資料失敗
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}