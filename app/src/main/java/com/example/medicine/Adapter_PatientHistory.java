package com.example.medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_PatientHistory extends RecyclerView.Adapter<Adapter_PatientHistory.MyViewHolder> {


    Context context;
    List<DataType_PatientHistory> dataType_patientHistories;

    public Adapter_PatientHistory() {
    }

    public Adapter_PatientHistory(Context context, List<DataType_PatientHistory> dataType_patientHistories) {
        this.context = context;
        this.dataType_patientHistories = dataType_patientHistories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemview_patient_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataType_PatientHistory history = dataType_patientHistories.get(position);
        holder.iph_txtv_title.setText(history.getTitle());
        holder.iph_txtv_short_description.setText(history.getCases_sort_history());
        holder.iph_txtv_doctor_name_desi_insti.setText(history.getDoctor_name()+", "+history.getDoctor_desination()+","+history.getDoctor_institute());
        holder.iph_txtv_time.setText(history.getTime());
    }

    @Override
    public int getItemCount() {
        return dataType_patientHistories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView iph_txtv_title;
        TextView iph_txtv_short_description;
        TextView iph_txtv_doctor_name_desi_insti;
        TextView iph_txtv_time;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iph_txtv_title = itemView.findViewById(R.id.iph_txtv_title);
            iph_txtv_short_description = itemView.findViewById(R.id.iph_txtv_short_description);
            iph_txtv_doctor_name_desi_insti = itemView.findViewById(R.id.iph_txtv_doctor_name_desi_insti);
            iph_txtv_time = itemView.findViewById(R.id.iph_txtv_time);
        }
    }
}
