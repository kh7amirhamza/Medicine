package com.example.medicine.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.DataType.DoctorShortDetailsGet;
import com.example.medicine.DataType_AllDoctorDetails;
import com.example.medicine.DoctorDetailsActivity;
import com.example.medicine.R;

import java.util.List;

public class DoctorShortDetailsAdapter extends RecyclerView.Adapter<DoctorShortDetailsAdapter.MyViewHolder> {
    private static final String TAG = "Adapter_BookAppoiBy_Spe";
    Context context;

    List<DoctorShortDetailsGet> doctorShortDetailsGets;
    String catName;
    String jsonResponse;
    String token;


    public DoctorShortDetailsAdapter() {
    }

    public DoctorShortDetailsAdapter(Context context, List<DoctorShortDetailsGet> doctorShortDetailsGets, String catName, String jsonResponse, String token) {
        this.context = context;
        this.doctorShortDetailsGets = doctorShortDetailsGets;
        this.catName = catName;
        this.jsonResponse = jsonResponse;
        this.token = token;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemview_doctor_preview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       holder.iv_dp_doctor_image.setImageResource(R.drawable.doctor_a);
       holder.iv_dp_doctor_name.setText(doctorShortDetailsGets.get(position).getName());
       holder.iv_dp_doctor_category.setText(doctorShortDetailsGets.get(position).getCategory());
       holder.iv_dp_doctor_department.setText(doctorShortDetailsGets.get(position).getDepartment());
       holder.iv_dp_doctor_designation.setText(doctorShortDetailsGets.get(position).getDesignation());
       holder.iv_dp_doctor_institute.setText(doctorShortDetailsGets.get(position).getInstitute());
       holder.iv_dp_doctor_consultation_fee.setText(doctorShortDetailsGets.get(position).getConsultation_fee());
    }

    @Override
    public int getItemCount() {
        return doctorShortDetailsGets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ImageView iv_dp_doctor_image;
         TextView iv_dp_doctor_name, iv_dp_doctor_category, iv_dp_doctor_department, iv_dp_doctor_designation,
                 iv_dp_doctor_institute, iv_dp_doctor_consultation_fee;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_dp_doctor_image = itemView.findViewById(R.id.iv_dp_doctor_image);
            iv_dp_doctor_name = itemView.findViewById(R.id.iv_dp_doctor_name);
            iv_dp_doctor_category = itemView.findViewById(R.id.iv_dp_doctor_category);
            iv_dp_doctor_department = itemView.findViewById(R.id.iv_dp_doctor_department);
            iv_dp_doctor_designation = itemView.findViewById(R.id.iv_dp_doctor_designation);
            iv_dp_doctor_institute = itemView.findViewById(R.id.iv_dp_doctor_institute);
            iv_dp_doctor_consultation_fee = itemView.findViewById(R.id.iv_dp_doctor_consultation_fee);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked for Doctor Details", Toast.LENGTH_SHORT).show();
                   // int doctorId = 0;
                    Log.d(TAG, "onClick: doctorId: "+ doctorShortDetailsGets.get(getAdapterPosition()).getId());
                    context.startActivity(new Intent(context, DoctorDetailsActivity.class)
                            .putExtra("jsonResponse", jsonResponse)
                            .putExtra("doctorId",Integer.parseInt( doctorShortDetailsGets.get(getAdapterPosition()).getId()))
                            .putExtra("token",token)
                            .putExtra("catName",doctorShortDetailsGets.get(getAdapterPosition()).getCategory())
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                }
            });
        }
    }
}

    /*
    sorting collection of data by character
                        Collections.sort(sorted_doctor_details_list, new Comparator<DataType_DoctorDetails>() {

                            @Override
                            public int compare(DataType_DoctorDetails lhs, DataType_DoctorDetails rhs) {
                                return lhs.doctor_name.compareTo(rhs.doctor_name);
                            }
                        });


                        */