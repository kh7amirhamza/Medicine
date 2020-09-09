package com.example.medicine.MultiselectCustomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.medicine.R;


public class MultiSelect extends Dialog {

    RecyclerView ams_recyclerview,ams_recy_selected_items;
    List<String> selected_itemList;
    Adapter_Showing_Selected_Items adapter_showing_selected_items;

    TextView ams_txtv_save;

    OnClickListener onClickListener;
    String[] items;

    public MultiSelect(@NonNull Context context,String[] items) {
        super(context);
        this.items = items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiselect);



        ams_recyclerview = findViewById(R.id.ams_recyclerview);
        ams_recyclerview.setHasFixedSize(true);
        ams_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        ams_recy_selected_items = findViewById(R.id.ams_recy_selected_items);
        ams_recy_selected_items.setHasFixedSize(true);
        ams_recy_selected_items.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));

        selected_itemList = new ArrayList<>();
        adapter_showing_selected_items = new Adapter_Showing_Selected_Items(getContext(),selected_itemList);
        ams_recy_selected_items.setAdapter(adapter_showing_selected_items);

        ams_txtv_save = findViewById(R.id.ams_txtv_save);


        AdapterMultiSelect adapterMultiSelect = new AdapterMultiSelect(getContext(),items);
        ams_recyclerview.setAdapter(adapterMultiSelect);
        adapterMultiSelect.setOnClickListener(new AdapterMultiSelect.OnClickListener() {
            @Override
            public void onClick(String[] items_selections,int position) {
                Toast.makeText(getContext(), "Clicked: "+items[position], Toast.LENGTH_SHORT).show();


                List<String> newSelectedItem = new ArrayList<>();

                for (int i = 0; i < items.length; i++) {
                    if (items_selections[i].equalsIgnoreCase("selected")){
                        newSelectedItem.add(items[i]);
                    }
                }
               ams_txtv_save.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       onClickListener.onClick(newSelectedItem);
                   }
               });
               adapter_showing_selected_items.setData(newSelectedItem);
            }
        });
    }

    public interface OnClickListener{
        void onClick(List<String> selected_Item_List);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
