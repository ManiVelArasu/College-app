package com.example.collegeinformationsystem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.MyViewHolder> implements Filterable {
    private static final String TAG = StaffAdapter.class.getSimpleName();
    private Context context;
    private List<Staff> notesstaffList;
    private List<Staff> notesFilterList;
    private ItemOnListener itemonListener;
    public StaffAdapter(Context context, List<Staff> notesstaffList, ItemOnListener itemOnListener) {
        this.context = context;
        this.notesstaffList = notesstaffList;
        this.notesFilterList = notesstaffList;
        this.itemonListener = itemOnListener;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    notesFilterList = notesstaffList;
                }
                else{
                    ArrayList<Staff> filterList = new ArrayList<>();
                    for(Staff row : notesstaffList)
                    {

                        if(row.getName().toLowerCase().contains(charString) || row.getDepartment().contains(charString)){
                            filterList.add(row);

                        }
                    }
                    notesFilterList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = notesFilterList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notesFilterList = (ArrayList<Staff>)results.values;
                notifyDataSetChanged();
            }
        };
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Ename;
        private ImageButton dlt,edit;
        private RelativeLayout relative;
        public MyViewHolder(View view) {
            super(view);
            Ename = view.findViewById(R.id.Ename);
            dlt=view.findViewById(R.id.dlt);
            edit=view.findViewById(R.id.edit);
            relative=view.findViewById(R.id.relative);
            relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //itemonClickListener.onnameClick(getAdapterPosition());

                    int pos = notesstaffList.indexOf(notesFilterList.get(getAdapterPosition()));
                    itemonListener.onnameClick(pos);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = notesstaffList.indexOf(notesFilterList.get(getAdapterPosition()));
                    itemonListener.oneditClick(pos);
                }
            });
            dlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = notesstaffList.indexOf(notesFilterList.get(getAdapterPosition()));
                    itemonListener.ondltClick(pos);
                }
            });
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_staff, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(notesFilterList.size() > 0){
            Staff staff = notesFilterList.get(position);
            holder.Ename.setText(staff.getName());
        }
        else{
            Log.e(TAG,"No data");
        }
    }

    @Override
    public int getItemCount() {
        return notesFilterList.size();
    }

}