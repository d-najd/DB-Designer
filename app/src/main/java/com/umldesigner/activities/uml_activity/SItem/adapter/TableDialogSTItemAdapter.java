package com.umldesigner.activities.uml_activity.SItem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.SItem.listener.EditSItemListeners;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.List;

public class TableDialogSTItemAdapter extends RecyclerView.Adapter<TableDialogSTItemAdapter.UmlRecyclerViewHolder>{
    private final List<? extends SItemPojo> recyclerDataArrayList;
    private SItemPojo curData;
    
    /**
     * createAdapter for the items inside the sTable
     */
    public TableDialogSTItemAdapter(List<? extends SItemPojo> recyclerDataArrayList) {
        this.recyclerDataArrayList = recyclerDataArrayList;
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
        curData = recyclerDataArrayList.get(position);
        holder.itemView.setOnClickListener(new EditSItemListeners(curData));
        
        holder.title.setText(new StringBuilder().append("- ").append(curData.getValue()).append(": ").append(curData.getType()).toString());
    }
    
    @Override
    public int getItemCount() {
        return recyclerDataArrayList.size();
    }
    
    // View Holder Class to handle Recycler View.
    
    static class UmlRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        
        public UmlRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
        }
    }
}
