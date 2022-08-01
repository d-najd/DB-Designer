package com.umldesigner.activities.uml_activity.views.arrow;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.Message;
import com.umldesigner.activities.uml_activity.views.arrow.Connection.SFKConnectionView;
import com.umldesigner.infrastructure.uml.data.STable.STableData;

import lombok.Getter;

public class SFKBuilder {
    private SFKView sfkView;
   
    @Getter
    private final ViewGroup container;
    private final STableData fTableData;
    private final STableData sTableData;
    private final int sPos;
    private final int fPos;
    
    @Getter
    private SFKConnectionView fConnectionView;
    @Getter
    private SFKConnectionView sConnectionView;
    
    /**
     * creates a SFK builder
     * @param container the viewGroup where the views will be added
     * @param fTableData data of the first table, this is used for getting position of the table n stuff
     * @param fPos position of the field in the field table, the "primary key"
     * @param sTableData data of the second table, this is used for getting position of the table n stuff
     * @param sPos position of the first in the second table, the "secondary key"
     * @apiNote it is possible to swap this is {@link com.umldesigner.infrastructure.uml.data.BaseDataInterface}
     * if need arise, we may be able to get the position of the recyclerview when instantiating a
     * table and the position of the item when instantiation the items and this will lead to fewer
     * dependencies and possibility to extend to more than just a simple table, but I have no plans for
     * that
     */
    public SFKBuilder(ViewGroup container, STableData fTableData, int fPos, STableData sTableData, int sPos){
        this.container = container;
        this.fTableData = fTableData;
        this.sTableData = sTableData;
        this.fPos = fPos;
        this.sPos = sPos;
    }
    
    public SFKView build(){
        fConnectionView = buildSFKConnection(fTableData, fPos);
        sConnectionView = buildSFKConnection(sTableData, sPos);
        
        if (fConnectionView == null || sConnectionView == null) {
            rollBack();
            return null;
        } else {
            sfkView = new SFKView(this);
            return sfkView;
        }
    }
    
    public void rollBack(){
        if (fConnectionView != null){
            fConnectionView.destroy();
        }
        if (sConnectionView != null){
            sConnectionView.destroy();
        }
        sfkView.destroy();
    }
    
    /**
     * creates the specific connection
     * @param tableData used for getting position
     * @param pos position of the field in the table
     * @return instance of SFKConnectionView
     */
    private SFKConnectionView buildSFKConnection(STableData tableData, int pos){
        try {
            RecyclerView recyclerView = tableData.getRecyclerView();
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
            View item = viewHolder.itemView;
            float itemX = tableData.getX() + recyclerView.getX() + item.getX();
            float itemY = tableData.getY() + recyclerView.getY() + item.getY();
            return new SFKConnectionView(container, itemX, itemY, 0);
        } catch (NullPointerException e){
            e.printStackTrace();
            Log.d("ERROR", "Can't create SFK connection with pos " + pos);
            Message.defErrMessage(container.getContext());
        }
        return null;
    }
}