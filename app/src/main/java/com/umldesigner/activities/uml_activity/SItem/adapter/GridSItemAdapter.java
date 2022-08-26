package com.umldesigner.activities.uml_activity.SItem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.R;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.List;

/**
 * item adapter for the tables in the grid
 */
public class GridSItemAdapter extends RecyclerView.Adapter<GridSItemAdapter.UmlRecyclerViewHolder>{
    private final List<? extends SItemPojo> recyclerDataArrayList;
    private SItemPojo curData;
    
    /**
     * createAdapter used for the dialog for editing the sTable
     */
    public GridSItemAdapter(List<? extends SItemPojo> recyclerDataArrayList) {
        this.recyclerDataArrayList = recyclerDataArrayList;
    }
    
    @NonNull
    @Override
    public UmlRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_s_item_row, parent, false);
        return new UmlRecyclerViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull UmlRecyclerViewHolder holder, int position) {
        
        curData = recyclerDataArrayList.get(position);
    
        holder.value.setText(curData.getValue());
        holder.type.setText(curData.getType());
    }
    
    @Override
    public int getItemCount() {
        return recyclerDataArrayList.size();
    }
    
    // View Holder Class to handle Recycler View.
    
    static class UmlRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView value;
        public TextView type;
        
        public UmlRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.value);
            type = itemView.findViewById(R.id.type);
        }
    }
}
