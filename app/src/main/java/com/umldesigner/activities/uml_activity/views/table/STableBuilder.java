package com.umldesigner.activities.uml_activity.views.table;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.umldesigner.activities.uml_activity.grid.SDragListeners;
import com.umldesigner.activities.uml_activity.listeners.table.STableListeners;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.ASettings;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class STableBuilder {
    private final Context context;
    private final float x;
    private final float y;
    private final String title;
    private final SDragListeners listeners;
    private ArrayList<SItemData> items;
    @Getter(AccessLevel.NONE)
    private final STableView sTableView;
    private final ViewGroup container;
    
    /**
     * builder used for creating schema tables
     * @param container a container in which the view is located at
     * @param title title for the table
     * @param x position in grid pieces?
     * @param y position in grid pieces?
     */
    public STableBuilder(
            @NonNull ViewGroup container, // I HATE THIS PIECE OF ****, WHOEVER MADE THIS CAUSE STATIC MEMORY LEAK GO TO HELL
            @NonNull SDragListeners listeners,
            @NonNull String title,
            float x, float y){
        this.listeners = listeners;
        this.title = title;
        this.x = x * ASettings.getInstance().getSpacing();
        this.y = y * ASettings.getInstance().getSpacing();
        this.container = container;
        this.context = container.getContext();
        
        sTableView = new STableView(this);
        sTableView.setVisibility(View.GONE);
    }
    
    /**
     * adds all the items and sets the table for those items
     * @param items list of items
     * @return the builder
     */
    public STableBuilder addItems(ArrayList<SItemData> items) {
        for (SItemData item : items){
            item.setTable(sTableView.getData());
        }
        sTableView.setItems(items);
        this.items = items;
        return this;
    }
    
    public STableView build(){
        Log.d("Execute", "build with builder " + this.toString());
        
        sTableView.updateData();
       
        sTableView.setOnClickListener(new STableListeners(sTableView.getData()));
        sTableView.setOnLongClickListener(listeners);
        sTableView.setVisibility(View.VISIBLE);
        container.addView(sTableView);
    
        return sTableView;
    }
}
