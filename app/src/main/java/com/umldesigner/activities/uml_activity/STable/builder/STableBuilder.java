package com.umldesigner.activities.uml_activity.STable.builder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.umldesigner.activities.uml_activity.STable.view.STableView;
import com.umldesigner.activities.uml_activity.STable.listener.STableListeners;
import com.umldesigner.activities.uml_activity.SItem.data.SItemData;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;
import com.umldesigner.infrastructure.uml.utils.SUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class STableBuilder {
    private final Context context;
    private final float x;
    private final float y;
    private final String title;
    private List<SItemData> items;
    @Getter(AccessLevel.NONE)
    private final STableView sTableView;
    private final ViewGroup container;

    private final String uuid;
    
    /**
     * builder used for creating schema tables
     * @param container a container in which the view is located at
     * @param title title for the table
     * @param x position in grid pieces?
     * @param y position in grid pieces?
     */
    public STableBuilder(
            String uuid,
            @NonNull ViewGroup container, // WHOEVER MADE THIS CAUSE STATIC MEMORY LEAK GO TO HELL
            @NonNull String title,
            float x, float y){
        this.title = title;

        this.uuid = uuid;
        this.x = x * SSettings.getInstance().getSpacing();
        this.y = y * SSettings.getInstance().getSpacing();
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
        
        sTableView.setOnClickListener(new STableListeners(sTableView.getData(), container));
        sTableView.setOnLongClickListener(SUtils.getInstance().getDragListeners());
        sTableView.setVisibility(View.VISIBLE);
        container.addView(sTableView);
    
        return sTableView;
    }
}
