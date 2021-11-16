package com.example.collegeinformationsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends AppCompatActivity implements ItemOnListener{
    private ImageView add_data;
    private StaffAdapter Adapter;
    private static final String TAG = "Activity Lifecycle";
    private List<Staff> notesstaffList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button data;
    private DataBaseHelper db;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        recyclerView = findViewById(R.id.recycler_view);
        add_data=findViewById(R.id.add_data);
        db = new DataBaseHelper(this);
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(StaffActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.adddetails, viewGroup, false);
                builder.setView(dialogView);
                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                Button upadte = dialogView.findViewById(R.id.update);
                EditText name = dialogView.findViewById(R.id.name);
                EditText dept =dialogView.findViewById(R.id.dept);

                upadte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createStaff(name.getText().toString(),dept.getText().toString());
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        Adapter = new StaffAdapter(getApplicationContext(),notesstaffList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(Adapter);
    }
    private void createStaff(String name,String dept) {
        // inserting note in db and getting
        // newly inserted note id
        Staff staff = new Staff();
        staff.setName(name);
        staff.setDepartment(dept);
        long id = db.insertstaff(staff);
        // get the newly inserted note from db
        Staff s= db.getstaff(id);
        // adding new note to array list at 0 position
        notesstaffList.add(0, s);
        // refreshing the list
        Adapter.notifyDataSetChanged();

    }

    @Override
    public void onnameClick(int position) {
        Intent intent=new Intent(StaffActivity.this, StudentView.class);
        intent.putExtra("id",notesstaffList.get(position).getId());
        intent.putExtra("name",notesstaffList.get(position).getName());
        intent.putExtra("dept",notesstaffList.get(position).getDepartment());
        startActivity(intent);

    }

    @Override
    public void oneditClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StaffActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.staff_data, viewGroup, false);
        builder.setView(dialogView);
        //getSupportActionBar().hide();
        AlertDialog alertDialog = builder.create();
        final EditText inputNote = dialogView.findViewById(R.id.name);
        final EditText inputdept = dialogView.findViewById(R.id.dept);
        inputNote.setText(notesstaffList.get(position).getName());
        inputdept.setText(notesstaffList.get(position).getDepartment());
        data= dialogView.findViewById(R.id.data);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStaff(inputNote.getText().toString(),inputdept.getText().toString(),position);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
    private void updateStaff(String name, String dept ,int position) {
        Staff staff = notesstaffList.get(position);
        // updating note text
        staff.setName(name);
        staff.setDepartment(dept);
        // updating note in db
        db.updateStaff(staff);
        // refreshing the list
        notesstaffList.set(position, staff);
        Adapter.notifyItemChanged(position);
    }

    @Override
    public void ondltClick(int position) {
        {
            deleteStaff(position);
        }

    }
    private void deleteStaff(int position) {
        // deleting the note from db
        db.deleteStaff(notesstaffList.get(position));
        // removing the note from the list
        notesstaffList.remove(position);
        Adapter.notifyItemRemoved(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "OnResume Called");
        notesstaffList.clear();
        notesstaffList.addAll(db.getAllStaff());
        Adapter.notifyDataSetChanged();
    }
    }
