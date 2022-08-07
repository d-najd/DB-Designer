package com.umldesigner.activities.uml_activity.views.table;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.recyclers.SAdapter;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.pojos.BasePojo;

import java.util.ArrayList;

import lombok.Getter;

public class STableView extends ConstraintLayout implements SObject {
    
    private final SSettingsSingleton umlSettingsInstance;
    @Getter
    private STableData data;
    private View v;

    @Getter
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    
    private ViewGroup container;
    
    /**
     * creates UmlTableView at given position and with given listeners if given
     * <pre>
     *      _______________
     *     |    title     |
     *     |--------------|
     *     | field1       |
     *     | field2       |
     *     | field...     |
     *      _______________
     * </pre>
     *
     * @param           builder which holds all of the data
     * @implNote        builder is used because we want to make sure STableView is made from the
     *                  builder
     */
    public STableView(STableBuilder builder){
        super(builder.getContext());
        
        //prep
        umlSettingsInstance = SSettingsSingleton.getInstance();
        container = builder.getContainer();
        
        //setting up the data
        setData(new STableData(
                umlSettingsInstance.getNextId(),
                builder.getX(),
                builder.getY(),
                builder.getTitle(),
                builder.getItems()));
        umlSettingsInstance.allViewTagsPut(data.getId(), this);
    
        //inflating the view
        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        v = inflater.inflate(R.layout.card_s_table, this, true);
    
        //setting the fields inside the table
        TextView titleTextView = v.findViewById(R.id.title);
        this.setId(data.getId());
        this.setX(data.getX()); //setting the absolute position
        this.setY(data.getY());
        this.setElevation(SSettingsSingleton.TABLE_ELEVATION);
        this.setMaxWidth((int) SSettingsSingleton.TABLE_WIDTH);
        this.setMinWidth((int) SSettingsSingleton.TABLE_WIDTH);
        this.setMinimumWidth((int) SSettingsSingleton.TABLE_WIDTH);
        this.setBackgroundColor(2131034697);
        titleTextView.setText(data.getTitle());
   
        //setting up the recyclerview
        RecyclerView umlTableRecyclerView = v.findViewById(R.id.uml_table_recyclerView);
    
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        umlTableRecyclerView.setLayoutManager(layoutManager);
        
        if (builder.getItems() != null){
            SAdapter adapter = new SAdapter(builder.getItems(), v.getContext());
            umlTableRecyclerView.setAdapter(adapter);
        }
        
        updateData(); // making sure the data gets updated in case I forget in the future
    }
    
    @Override
    public void move(float x, float y) {
        Log.d("Execute", "move with parameters " + x + ", " + y);
    
        float newX = Math.round((x - this.getWidth() / 2f) / (umlSettingsInstance.getSpacing())) * umlSettingsInstance.getSpacing();
        float newY = Math.round((y - this.getHeight() / 2f) / (umlSettingsInstance.getSpacing())) * umlSettingsInstance.getSpacing();
        
        this.setX(newX);
        this.setY(newY);
        
        updateData();
    }
    
    @Override
    public void destroy() {
        container.removeView(this);
    }
    
    @Override
    public <T extends BasePojo & BaseDataInterface> void setData(T data) {
        Log.d("Execute", "setData with parameter " + data.toString());
        
        this.data = (STableData) data;
    }
    
    @Override
    public void updateData() {
        Log.d("Execute", "updateData");
        
        //prep
        TextView titleTextView = v.findViewById(R.id.title);
        
        //updating the data
        data.setTitle(titleTextView.getText().toString());
        data.setX(this.getX());
        data.setY(this.getY());
        data.notifyObservers();
    }
    
    /**
     * sets the title of the Table View but does not guarantee updating the data, use {@link #updateData()} for that
     *
     * @param title the title that we are setting the view to
     * @see #updateData()
     */
    public void setTitle(String title) {
        Log.d("Execute", "setTitle with parameter " + title);
        
        TextView titleTextView = v.findViewById(R.id.title);
        titleTextView.setText(title);
    }
    
    /**
     * sets the itemData to given ArrayList but does not guarantee updating the data, use {@link #updateData()} for that
     *
     * @param itemDataArrayList the given itemArrayList
     * @see #updateData()
     */
    public void setSItemData(ArrayList<SItemData> itemDataArrayList) {
        Log.d("Execute", "setSItemData with parameters " + itemDataArrayList.toString());
        
        recyclerView = v.findViewById(R.id.uml_table_recyclerView);
        SAdapter adapter = new SAdapter(itemDataArrayList, v.getContext());
        
        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        
        data.setItems(itemDataArrayList);
        data.setRecyclerView(recyclerView);
    }
}