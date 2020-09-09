package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class SpecificCategory extends AppCompatActivity {

    private List<SpecificCategoryModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private SpesificCategoryAdapter adapter;


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_specific_category);

        // = findViewById(R.id.specificCategoryToolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("category");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list.add(new SpecificCategoryModel(R.drawable.doctor_a,"Dr. Habiba Ahmed Rika",
                "Cardiology,Medicine", "MBBS,BCS,MD(Cardiology)",
                "Assistan Professor","National Institiute of Cardiovascular Deseases",
                "700"));
        list.add(new SpecificCategoryModel(R.drawable.doctor_e,
                "Dr. Kaji Md Rubayet Anower","Cardiology,Medicine",
                ""+getString(R.string.dr_kaji_eduQulification),
                "Cardiologist and Medicine Specialist ",
                ""+getString(R.string.drKaji_institute),"600"));
        list.add(new SpecificCategoryModel(R.drawable.doctor_c,
                "Dr. Tarana Khanom Juthi","Cardiology, Endicronology",
                ""+getString(R.string.drSadi_edu_qualification),
                "Senior Medical Officer","National Heart Foundation Hospital",
                "1000"));
        list.add(new SpecificCategoryModel(R.drawable.doctor_a,"Dr. Habiba Ahmed Rika",
                "Cardiology,Medicine", "MBBS,BCS,MD(Cardiology)",
                "Assistan Professor","National Institiute of Cardiovascular Deseases",
                "700"));
        list.add(new SpecificCategoryModel(R.drawable.doctor_e,
                "Dr. Kaji Md Rubayet Anower","Cardiology,Medicine",
                ""+getString(R.string.dr_kaji_eduQulification),
                "Cardiologist and Medicine Specialist ",
                ""+getString(R.string.drKaji_institute),"600"));
        list.add(new SpecificCategoryModel(R.drawable.doctor_c,
                "Dr. Tarana Khanom Juthi","Cardiology, Endicronology",
                ""+getString(R.string.drSadi_edu_qualification),
                "Senior Medical Officer","National Heart Foundation Hospital",
                "1000"));


        adapter = new SpesificCategoryAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}