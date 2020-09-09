package com.example.medicine.MultiselectCustomView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.R;

import java.util.List;


public class Adapter_Showing_Selected_Items extends RecyclerView.Adapter<Adapter_Showing_Selected_Items.MyViewHolder> {

    Context context;
    List<String> selected_itemList;

    public Adapter_Showing_Selected_Items() {
    }

    public Adapter_Showing_Selected_Items(Context context, List<String> selected_itemList) {
        this.context = context;
        this.selected_itemList = selected_itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_showing_selected_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.iv_sei_textview.setText(selected_itemList.get(position));
    }

    public void setData(List<String> selectedItemList){
        selected_itemList = null;
        selected_itemList = selectedItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return selected_itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView iv_sei_textview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_sei_textview = itemView.findViewById(R.id.iv_sei_textview);
        }
    }
}
