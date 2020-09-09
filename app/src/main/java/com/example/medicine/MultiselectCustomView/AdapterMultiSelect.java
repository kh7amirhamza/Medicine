package com.example.medicine.MultiselectCustomView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.R;


public class AdapterMultiSelect extends RecyclerView.Adapter<AdapterMultiSelect.MyViewHolder> {

    Context context;
    String[] items;
    public OnClickListener onClickListener;

    String[] items_selection;


    public AdapterMultiSelect() {
    }

    public AdapterMultiSelect(Context context,String[] items) {
        this.context = context;
        this.items = items;

        items_selection = new String[items.length];

        for (int i=0; i<items.length;i++){
            items_selection[i] = "unselected";
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_multiselect,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.iv_ms_txtv_item_name.setText(items[position]);

        if (items_selection[position].equalsIgnoreCase("selected")) {
            holder.iv_ms_txtv_layout.setBackground(context.getResources().getDrawable(R.drawable.bg_border_green));
        } else {
            holder.iv_ms_txtv_layout.setBackground(context.getResources().getDrawable(R.drawable.bg_border_shadow));
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView iv_ms_txtv_item_name,iv_ms_txtv_add;
        LinearLayout iv_ms_txtv_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_ms_txtv_item_name = itemView.findViewById(R.id.iv_ms_txtv_item_name);
            iv_ms_txtv_add = itemView.findViewById(R.id.iv_ms_txtv_add);
            iv_ms_txtv_layout = itemView.findViewById(R.id.iv_ms_txtv_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        if (items_selection[getAdapterPosition()].equalsIgnoreCase("selected")){
                            items_selection[getAdapterPosition()] = "unselected";
                          //  Toast.makeText(context, "selected", Toast.LENGTH_SHORT).show();
                        }else if (items_selection[getAdapterPosition()].equalsIgnoreCase("unselected")){
                            items_selection[getAdapterPosition()] = "selected";
                        }
                        else {
                            Toast.makeText(context, "Nothing to Select", Toast.LENGTH_SHORT).show();
                        }
                        onClickListener.onClick(items_selection,getAdapterPosition());
                        notifyDataSetChanged();
                    }

            });
        }
    }
    public interface OnClickListener {
        void onClick(String[] items_selection, int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
