package com.example.medicine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_BookAppoiBy_Speaciali extends RecyclerView.Adapter<Adapter_BookAppoiBy_Speaciali.MyViewHolder> {
    private static final String TAG = "Adapter_BookAppoiBy_Spe";
    Context context;
    List<DataTypeAllCategoryOfDoctor> dataTypeAllCategoryOfDoctors;
    String token;
    public Adapter_BookAppoiBy_Speaciali() {
    }

    public Adapter_BookAppoiBy_Speaciali(Context context, List<DataTypeAllCategoryOfDoctor> dataTypeAllCategoryOfDoctors,
                                         String token) {
        this.context = context;
        this.dataTypeAllCategoryOfDoctors = dataTypeAllCategoryOfDoctors;
        this.token = token;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemview_book_appoi_by_speaciali, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bas_imgv_speaciali_icon.setImageResource(R.drawable.cardiology);
        holder.bas_txtv_speaciali_name.setText(dataTypeAllCategoryOfDoctors.get(position).getCat_name());

    }

    @Override
    public int getItemCount() {
        return dataTypeAllCategoryOfDoctors.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView bas_imgv_speaciali_icon;
        TextView bas_txtv_speaciali_name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bas_imgv_speaciali_icon = itemView.findViewById(R.id.bas_imgv_speaciali_icon);
            bas_txtv_speaciali_name = itemView.findViewById(R.id.bas_txtv_speaciali_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,ViewAllDoctorByCatActivity.class)
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