package com.umldesigner.activities.uml_activity.SItem;

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
public class SItemGridAdapter extends RecyclerView.Adapter<SItemGridAdapter.UmlRecyclerViewHolder>{
    private final List<? extends SItemPojo> recyclerDataArrayList;
    private final ViewGroup container;
    private SItemPojo curData;
    
    /**
     * createAdapter used for the dialog for editing the sTable
     */
    public SItemGridAdapter(List<? extends SItemPojo> recyclerDataArrayList, ViewGroup container) {
        this.recyclerDataArrayList = recyclerDataArrayList;
        this.container = container;
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

        holder.itemView.setOnClickListener(new SItemListener(container, curData));
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
