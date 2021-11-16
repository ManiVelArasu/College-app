package com.example.collegeinformationsystem;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class DeptAdapter extends RecyclerView.Adapter<DeptAdapter.MyViewHolder> implements Filterable {
    private static final String TAG = DeptAdapter.class.getSimpleName();
    private Context context;
    private List<Dept> notesdeptList;
    private List<Dept> notesFilterList;
    private ItemOnClickListener itemonClickListener;
    public DeptAdapter(Context context, List<Dept> notesdeptList, ItemOnClickListener itemOnClickListener) {
        this.context = context;
        this.notesdeptList = notesdeptList;
        this.notesFilterList = notesdeptList;
        this.itemonClickListener = itemOnClickListener;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    notesFilterList = notesdeptList;
                }
                else{
                    ArrayList<Dept> filterList = new ArrayList<>();
                    for(Dept row : notesdeptList)
                    {
                        if(row.getDept().toLowerCase().contains(charString)){
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
                notesFilterList = (ArrayList<Dept>)results.values;
                notifyDataSetChanged();
            }
        };
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Ename;
        private ImageView dlt,edit;
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
                    int pos = notesdeptList.indexOf(notesFilterList.get(getAdapterPosition()));
                    itemonClickListener.onnameClick(pos);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = notesdeptList.indexOf(notesFilterList.get(getAdapterPosition()));
                    itemonClickListener.oneditClick(pos);
                }
            });
            dlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = notesdeptList.indexOf(notesFilterList.get(getAdapterPosition()));
                    itemonClickListener.ondltClick(pos);
                }
            });
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_dept, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(notesFilterList.size() > 0){
            Dept note = notesFilterList.get(position);
            holder.Ename.setText(note.getDept());
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