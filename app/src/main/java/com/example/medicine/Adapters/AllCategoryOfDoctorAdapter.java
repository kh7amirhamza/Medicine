package com.example.medicine.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.DataType.AllCategoryOfDoctor;
import com.example.medicine.R;
import com.example.medicine.ViewAllDoctorByCatActivity;

import java.util.List;

public class AllCategoryOfDoctorAdapter extends RecyclerView.Adapter<AllCategoryOfDoctorAdapter.MyViewHolder> {
    private static final String TAG = "Adapter_BookAppoiBy_Spe";
    Context context;


    List<AllCategoryOfDoctor> dataTypeAllCategoryOfDoctors;
    String token;
    public AllCategoryOfDoctorAdapter() {
    }

    public AllCategoryOfDoctorAdapter(Context context, List<AllCategoryOfDoctor> dataTypeAllCategoryOfDoctors,
                                      String token) {
        this.context = context;
        this.dataTypeAllCategoryOfDoctors = dataTypeAllCategoryOfDoctors;
        this.token = token;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.doctors_category_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.image.setImageResource(R.drawable.cardiology);
        holder.title.setText(dataTypeAllCategoryOfDoctors.get(position).getCat_name());

    }

    @Override
    public int getItemCount() {
        return dataTypeAllCategoryOfDoctors.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.doctorsCategoryIconID);
            title = itemView.findViewById(R.id.doctorsCategoryNameID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, ViewAllDoctorByCatActivity.class)
                    .putExtra("token",token)
                    .putExtra("catId",String.valueOf(dataTypeAllCategoryOfDoctors.get(getAdapterPosition()).getId()))
                    .putExtra("catName",dataTypeAllCategoryOfDoctors.get(getAdapterPosition()).getCat_name()));
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