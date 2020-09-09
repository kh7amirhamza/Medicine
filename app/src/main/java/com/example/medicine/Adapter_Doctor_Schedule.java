package com.example.medicine;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_Doctor_Schedule extends RecyclerView.Adapter<Adapter_Doctor_Schedule.MyViewHolder> {
    private static final String TAG = "Adapter_Doctor_Schedule";

    Context context;
    DataType_AllDoctorDetails.Schedule schedule;
    int checkedPosition = -1;

    OnClickListener onClickListener;

    public Adapter_Doctor_Schedule() {
    }

    public Adapter_Doctor_Schedule(Context context, DataType_AllDoctorDetails.Schedule schedule) {
        this.context = context;
        this.schedule = schedule;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.itemview_doctor_schedule, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: position: " + position);
        holder.is_txtv_schedule_day.setText(schedule.getSchedulePositionList().get(position).getSchedule_day());
        holder.is_txtv_start_time.setText(schedule.getSchedulePositionList().get(position).getStart_time());
        holder.is_txtv_end_time.setText(schedule.getSchedulePositionList().get(position).getEnd_time());
        holder.is_txtv_status.setText(schedule.getSchedulePositionList().get(position).getStatus());
        holder.iis_txtv_schedule_position.setText("Schedule : " + (position + 1));


        if (checkedPosition == position) {
            holder.is_relativelayout.setBackground(context.getResources().getDrawable(R.drawable.bg_border_green));
            holder.is_imgv_check.setVisibility(View.VISIBLE);
        } else {
            holder.is_relativelayout.setBackground(context.getResources().getDrawable(R.drawable.bg_border_shadow_none));
            holder.is_imgv_check.setVisibility(View.GONE);
        }

        if (position%2!=0){
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorYellowLightPlus));
        }else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorBlueLightPlus));
        }
    }

    @Override
    public int getItemCount() {
        return schedule.getSchedulePositionList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView is_txtv_schedule_day, is_txtv_start_time, is_txtv_end_time, is_txtv_status, iis_txtv_schedule_position;

        RelativeLayout is_relativelayout;
        ImageView is_imgv_check;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            is_txtv_schedule_day = itemView.findViewById(R.id.is_txtv_schedule_day);
            is_txtv_start_time = itemView.findViewById(R.id.is_txtv_start_time);
            is_txtv_end_time = itemView.findViewById(R.id.is_txtv_end_time);
            is_txtv_status = itemView.findViewById(R.id.is_txtv_status);
            iis_txtv_schedule_position = itemView.findViewById(R.id.iis_txtv_schedule_position);
            is_relativelayout = itemView.findViewById(R.id.is_relativelayout);
            is_imgv_check = itemView.findViewById(R.id.is_imgv_check);




                is_imgv_check.setVisibility(View.GONE);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (context.getClass().getSimpleName().equalsIgnoreCase("PlaceAppointmentActivity")) {
                            checkedPosition = getAdapterPosition();
                            onClickListener.onClick(schedule.getSchedulePositionList().get(getAdapterPosition()).getSchedule_day());
                            notifyDataSetChanged();
                        }
                    }
                });
        }
    }

    public interface OnClickListener {
        void onClick(String appo_day);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
