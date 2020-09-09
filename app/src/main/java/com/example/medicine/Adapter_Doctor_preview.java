package com.example.medicine;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Doctor_preview extends RecyclerView.Adapter<Adapter_Doctor_preview.MyViewHolder> {
    private static final String TAG = "Adapter_Doctor_preview";

    Context context;
    List<DataType_AllDoctorDetails> dataType_allDoctorDetailsList, copydataType_allDoctorDetailsList;
    String catName;
    //int cat_position;
    String jsonResponse;
    String token;

    public Adapter_Doctor_preview() {
    }

    public Adapter_Doctor_preview(Context context, List<DataType_AllDoctorDetails> copydataType_allDoctorDetailsList,
                                  String catName, String jsonResponse,String token) {
        this.context = context;
        this.dataType_allDoctorDetailsList = new ArrayList<>();
        this.copydataType_allDoctorDetailsList = copydataType_allDoctorDetailsList;
        this.catName = catName;
        this.jsonResponse = jsonResponse;
        this.token = token;

        // this.cat_position = cat_position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemview_doctor_preview_2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        /*holder.dp_imgv_speaciali_icon.setImageResource(R.drawable.cardiology);
        *//*set doctor name*//*
        holder.dp_txtv_doctor_name.setText(dataType_allDoctorDetailsList.get(position).getGender());
        holder.dp_txtv_speaciali_in.setText(catName);
        holder.dp_txtv_work_place.setText(dataType_allDoctorDetailsList.get(position).getInstitute());*/
    }

    public void setFilter(List<DataType_AllDoctorDetails> dataType_allDoctorDetailsList, String dayOfWeek) {

        Log.d(TAG, "setFilter: alldoctorDetails: Length: " + dataType_allDoctorDetailsList.size());
        List<DataType_AllDoctorDetails> filterDataType_allDoctorDetailsList = new ArrayList<>();
        Log.d(TAG, "setFilter: copydoctorDetails: Length: " + filterDataType_allDoctorDetailsList.size());

        for (int i = 0; i < dataType_allDoctorDetailsList.size(); i++) {
            for (int j = 0; j < dataType_allDoctorDetailsList.get(i).getSchedule().getSchedulePositionList().size(); j++) {
                String day = dataType_allDoctorDetailsList.get(i).getSchedule().getSchedulePositionList().get(j).getSchedule_day();
                Log.d(TAG, "setFilter: day: " + day);
                Log.d(TAG, "setFilter: dayOfWeek: " + dayOfWeek);
                if (dayOfWeek.toLowerCase().equalsIgnoreCase(day.toLowerCase())) {
                    filterDataType_allDoctorDetailsList.add(dataType_allDoctorDetailsList.get(i));
                }
            }
        }
        setData(filterDataType_allDoctorDetailsList);
    }

    public void setData(List<DataType_AllDoctorDetails> dataType_allDoctorDetailsList) {
        this.dataType_allDoctorDetailsList = dataType_allDoctorDetailsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataType_allDoctorDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       // ImageView dp_imgv_speaciali_icon;
       // TextView dp_txtv_doctor_name, dp_txtv_speaciali_in, dp_txtv_work_place;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        /*    dp_imgv_speaciali_icon = itemView.findViewById(R.id.dp_imgv_speaciali_icon);
            dp_txtv_doctor_name = itemView.findViewById(R.id.dp_txtv_doctor_name);
            dp_txtv_speaciali_in = itemView.findViewById(R.id.dp_txtv_speaciali_in);
            dp_txtv_work_place = itemView.findViewById(R.id.dp_txtv_work_place);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked for Doctor Details", Toast.LENGTH_SHORT).show();
                   // int doctorId = 0;
                    Log.d(TAG, "onClick: doctorId: "+ dataType_allDoctorDetailsList.get(getAdapterPosition()).getId());
                    context.startActivity(new Intent(context, DoctorDetailsActivity.class)
                            .putExtra("jsonResponse", jsonResponse)
                            .putExtra("doctorId",Integer.parseInt( dataType_allDoctorDetailsList.get(getAdapterPosition()).getId()))
                            .putExtra("token",token)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                }
            });*/
        }
    }
}
