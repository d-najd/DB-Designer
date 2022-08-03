package com.umldesigner.activities.uml_activity.views.arrow;

import android.util.Log;
import android.util.Pair;
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
    @Getter
    private final STableData fTableData;
    @Getter
    private final STableData sTableData;
    @Getter
    private final int sPos;
    @Getter
    private final int fPos;
    
    /**
     * stores the x and y positions like Pair(x, y), of the item gotten with STableData and position
     */
    @Getter
    private final Pair<Float, Float> fTableItemPositions;
    /**
     * stores the x and y positions like Pair(x, y), of the item gotten with STableData and position
     */
    @Getter
    private final Pair<Float, Float> sTableItemPositions;
    
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
        
        this.fTableItemPositions = calItemYPos(fTableData, fPos);
        this.sTableItemPositions = calItemYPos(sTableData, sPos);
    }
    
    public SFKView build(){
        sfkView = new SFKView(this);
        
        fConnectionView = buildSFKConnection(fTableItemPositions);
        sConnectionView = buildSFKConnection(sTableItemPositions);
        
        if (fConnectionView == null || sConnectionView == null) {
            rollBack();
            return null;
        } else {
            sfkView = new SFKView(this);
            return sfkView;
        }
    }
    
    private SFKView createLine(){
        sfkView = new SFKView(this);
        return sfkView;
    }
    
    private void rollBack(){
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
     * @param positions Pair(x, y) with the positions of the given item
     * @return instance of SFKConnectionView
     * @see #calItemYPos(STableData, int)
     */
    private SFKConnectionView buildSFKConnection(Pair<Float, Float> positions){
        try {
            return new SFKConnectionView(container, positions.first, positions.second, 0);
        } catch (NullPointerException e){
            e.printStackTrace();
            Log.d("ERROR", "Can't create SFK connection with positions " + positions);
            Message.defErrMessage(container.getContext());
        }
        return null;
    }
    
    /**
     * calculates the x and y position of a given item inside the table
     * @return Pair(x, y) positions of the requested item
     * @param tableData the data where the item is located at
     * @param pos position in the list of the item
     */
    private Pair<Float, Float> calItemYPos(STableData tableData, int pos){
        try {
            RecyclerView recyclerView = tableData.getRecyclerView();
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
            assert viewHolder != null;
            View item = viewHolder.itemView;
            float itemX = tableData.getX() + recyclerView.getX() + item.getX();
            float itemY = tableData.getY() + recyclerView.getY() + item.getY();
            return new Pair<>(itemX, itemY);
        } catch (NullPointerException e){
            e.printStackTrace();
            Log.d("ERROR", "Can't create SFK connection with pos " + pos);
            Message.defErrMessage(container.getContext());
        }
        return null;
    }
}