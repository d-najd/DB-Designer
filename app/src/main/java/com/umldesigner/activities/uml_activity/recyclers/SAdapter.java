package com.umldesigner.activities.uml_activity.recyclers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.R;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;

import java.util.ArrayList;

public class SAdapter extends RecyclerView.Adapter<SAdapter.UmlRecyclerViewHolder>{
    private ArrayList<SItemData> recyclerDataArrayList;
    private Context context;
    private SItemData curData;
    private UmlRecyclerViewHolder curHolder;
    
    public SAdapter(ArrayList<SItemData> recyclerDataArrayList, Context context) {
        this.recyclerDataArrayList = recyclerDataArrayList;
        this.context = context;
    }
    
    @NonNull
    @Override
    public UmlRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_s_table_row, parent, false);
        return new UmlRecyclerViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull UmlRecyclerViewHolder holder, int position) {
        Log.d("Execute", "onBindViewHolder with parameters " + holder.toString() + ", " + position);
        
        curData = recyclerDataArrayList.get(position);
        curHolder = holder;
        
        holder.itemView.getX();
        Log.d("Execute", "x pos "+ holder.itemView.getX());
        holder.title.setText(new StringBuilder().append("- ").append(curData.getValue()).append(": ").append(curData.getType()).toString());
    }
    
    @Override
    public int getItemCount() {
        return recyclerDataArrayList.size();
    }
    
    // View Holder Class to handle Recycler View.
    
    class UmlRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        
        public UmlRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
        }
    }
}
