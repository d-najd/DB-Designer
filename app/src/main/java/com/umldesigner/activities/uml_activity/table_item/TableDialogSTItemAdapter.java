package com.umldesigner.activities.uml_activity.table_item;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.umldesigner.R;

import java.util.List;

public class TableDialogSTItemAdapter extends RecyclerView.Adapter<TableDialogSTItemAdapter.UmlRecyclerViewHolder>{
    private final List<SItemData> recyclerDataArrayList;
    private final ViewGroup container;

    /**
     * createAdapter for the items inside the sTable
     */
    public TableDialogSTItemAdapter(List<SItemData> recyclerDataArrayList, ViewGroup container) {
        this.recyclerDataArrayList = recyclerDataArrayList;
        this.container = container;
    }
    
    @NonNull
    @Override
    public UmlRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_s_table_row, parent, false);
        
        return new UmlRecyclerViewHolder(view);
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UmlRecyclerViewHolder holder, int position) {
        SItemData curData = recyclerDataArrayList.get(position);
        holder.itemView.setOnClickListener(new SItemListener(container, curData));


        holder.title.setText("- " + curData.getValue() + ": " + curData.getType());
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
