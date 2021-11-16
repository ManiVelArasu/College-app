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

public class DepartmentActivity extends AppCompatActivity implements ItemOnClickListener {
    private ImageView add_data;
    private DeptAdapter DAdapter;
    private static final String TAG = "Activity Lifecycle";
    private List<Dept> notesdeptList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button data;
    private DataBaseHelper db;
    androidx.appcompat.widget.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        recyclerView = findViewById(R.id.recycler_view);
        add_data=findViewById(R.id.add_data);
        searchView=findViewById(R.id.searchview);
        db = new DataBaseHelper(this);
        searchView.setOnQueryTextListener(
                new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.e(TAG,"myText ==>"+newText);
                        DAdapter.getFilter().filter(newText);
                        return true;
                    }
                }
        );
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DepartmentActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_dept, viewGroup, false);
                builder.setView(dialogView);
                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                Button upadte = dialogView.findViewById(R.id.update);
                EditText name = dialogView.findViewById(R.id.name);
                upadte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createDept(name.getText().toString());
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        DAdapter = new DeptAdapter(getApplicationContext(),notesdeptList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(DAdapter);

    }
private void createDept(String name) {
    Dept dept = new Dept();
    dept.setDept(name);
    long id = db.insertdept(dept);
    // get the newly inserted note from db
    Dept s = db.getdept(id);
    // adding new note to array list at 0 position
    notesdeptList.add(0, s);
    // refreshing the list
    DAdapter.notifyDataSetChanged();

}



    @Override
    public void onnameClick(int position) {
        Intent intent=new Intent(DepartmentActivity.this, DeptView.class);
        intent.putExtra("id",notesdeptList.get(position).getId());
        intent.putExtra("dept",notesdeptList.get(position).getDept());
        startActivity(intent);


    }

    @Override
    public void oneditClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DepartmentActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.deptdata, viewGroup, false);
        builder.setView(dialogView);
        //getSupportActionBar().hide();
        AlertDialog alertDialog = builder.create();
        final EditText inputName = dialogView.findViewById(R.id.name);
        inputName.setText(notesdeptList.get(position).getDept());
        data= dialogView.findViewById(R.id.data);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDept(inputName.getText().toString(),position);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
    private void updateDept(String name,int position) {
        Dept dept = notesdeptList.get(position);
        // updating note text
        dept.setDept(name);
        // updating note in db
        db.updateDept(dept);
        // refreshing the list
        notesdeptList.set(position, dept);
        DAdapter.notifyItemChanged(position);
    }

    @Override
    public void ondltClick(int position) {
        deleteDept(position);

    }
    private  void deleteDept(int position){
        db.deleteDept(notesdeptList.get(position));
        notesdeptList.remove(position);
        DAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"OnResume Called");
        notesdeptList.clear();
        notesdeptList.addAll(db.getAllDept());
        DAdapter.notifyDataSetChanged();
    }
}