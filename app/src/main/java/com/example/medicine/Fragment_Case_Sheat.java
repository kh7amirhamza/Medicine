package com.example.medicine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Case_Sheat extends Fragment {

    Toolbar fcs_toolbar;
    List<DataType_Case_Sheet> dataType_case_sheets;
    RecyclerView fcs_recy_case_sheet;

    public Fragment_Case_Sheat() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_case__sheat, container, false);

        fcs_toolbar = view.findViewById(R.id.fcs_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(fcs_toolbar);

        dataType_case_sheets = new ArrayList<>();
        dataType_case_sheets.add(new DataType_Case_Sheet("Dr. Siyam Khan","22-jun-2020","11:19 AM"));
        dataType_case_sheets.add(new DataType_Case_Sheet("Dr. Rakibul Islam","06-march-2019","04:50 PM"));


        fcs_recy_case_sheet = view.findViewById(R.id.fcs_recy_case_sheet);
        fcs_recy_case_sheet.setHasFixedSize(true);
        fcs_recy_case_sheet.setLayoutManager(new LinearLayoutManager(getContext()));

        Adapter_Case_Sheet adapter_case_sheet = new Adapter_Case_Sheet();
        fcs_recy_case_sheet.setAdapter(adapter_case_sheet);

        return view;
    }



    public class Adapter_Case_Sheet extends RecyclerView.Adapter<Adapter_Case_Sheet.MyViewHolder>{

        public Adapter_Case_Sheet() {
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.itemview_fcs_case_sheet,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.fcs_txtv_doctor_name.setText("Doctor: "+dataType_case_sheets.get(position).getDoctor_name());
            holder.fcs_txtv_date.setText(dataType_case_sheets.get(position).getDate());
            holder.fcs_txtv_time.setText(dataType_case_sheets.get(position).getTime());
        }

        @Override
        public int getItemCount() {
            return dataType_case_sheets.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView fcs_txtv_doctor_name,fcs_txtv_date,fcs_txtv_time;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                fcs_txtv_doctor_name = itemView.findViewById(R.id.fcs_txtv_doctor_name);
                fcs_txtv_date = itemView.findViewById(R.id.fcs_txtv_date);
                fcs_txtv_time = itemView.findViewById(R.id.fcs_txtv_time);
            }
        }
    }



    // Data type for case sheet
    public class DataType_Case_Sheet{

        String doctor_name, date, time;

        public DataType_Case_Sheet(String doctor_name, String date, String time) {
            this.doctor_name = doctor_name;
            this.date = date;
            this.time = time;
        }

        public String getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
