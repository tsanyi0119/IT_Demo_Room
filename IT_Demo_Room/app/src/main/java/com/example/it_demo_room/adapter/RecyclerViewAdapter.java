package com.example.it_demo_room.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it_demo_room.R;
import com.example.it_demo_room.room.UserData;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<UserData>mDataList = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Num,tv_Name,tv_Phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Num = itemView.findViewById(R.id.tv_Num);
            tv_Name = itemView.findViewById(R.id.tv_Name);
            tv_Phone = itemView.findViewById(R.id.tv_Phone);
        }
        void setShowData(int position){
            //設定顯示資料
            tv_Num.setText(String.valueOf(mDataList.get(position).getId()));
            tv_Name.setText(mDataList.get(position).getName());
            tv_Phone.setText(mDataList.get(position).getPhone());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //設定顯示資料
        holder.setShowData(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //傳入MainActivity中的userDataList
    public void setRoomDataList(List<UserData>userDataList){
        mDataList = userDataList;
        notifyDataSetChanged();
    }


}
