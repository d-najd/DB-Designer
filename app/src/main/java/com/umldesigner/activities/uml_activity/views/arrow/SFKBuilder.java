package com.umldesigner.activities.uml_activity.views.arrow;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.Message;
import com.umldesigner.activities.uml_activity.views.arrow.Connection.SFKConnectionView;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class SFKBuilder {
    @Getter(AccessLevel.NONE)
    private SFKView sfkView;
   
    private final ViewGroup container;
    private final STableData fTableData;
    private final STableData sTableData;
    private final int sPos;
    private final int fPos;
    
    /**
     * stores the x and y positions like Pair(x, y), of the item gotten with STableData and position
     */
    private final Pair<Float, Float> fTableItemPositions;
    /**
     * stores the x and y positions like Pair(x, y), of the item gotten with STableData and position
     */
    private final Pair<Float, Float> sTableItemPositions;
    
    private SFKConnectionView fConnectionView;
    private SFKConnectionView sConnectionView;
    
    private final float lineX;
    private float lineCenterY;
    
    /**
     * creates a SFK builder
     * @param container the viewGroup where the views will be added
     * @param fTableData data of the first table, this is used for getting position of the table n stuff
     * @param fPos position of the field in the field table, the "primary key"
     * @param sTableData data of the second table, this is used for getting position of the table n stuff
     * @param sPos position of the first in the second table, the "secondary key"
     * @implNote it is possible to swap this is {@link com.umldesigner.infrastructure.uml.data.BaseDataInterface}
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
        this.lineX = calLineX();
        this.lineCenterY = calLineCenterY();
    }
    
    public SFKView build(){
        sfkView = new SFKView(this);
        
        fConnectionView = buildSFKConnection(fTableItemPositions, true);
        sConnectionView = buildSFKConnection(sTableItemPositions, false);
        
        if (fConnectionView == null || sConnectionView == null) {
            rollBack();
            return null;
        } else {
            sfkView.setFirstKey(fConnectionView);
            sfkView.setSecondKey(sConnectionView);
       
            return sfkView;
        }
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
    private SFKConnectionView buildSFKConnection(Pair<Float, Float> positions, boolean firstKey){
        try {
            return new SFKConnectionView(this, positions, firstKey);
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
    
    private float calLineX(){
        float fTableStart = getFTableData().getX();
        float sTableStart = getSTableData().getX();
        
        float fTableEnd = SSettingsSingleton.TABLE_WIDTH + fTableStart;
        float sTableEnd = SSettingsSingleton.TABLE_WIDTH + sTableStart;
        
        //if overlapping
        if (sTableStart < fTableEnd && fTableStart < sTableEnd){
            return Math.max(fTableEnd, sTableEnd);
        } else { //if not overlapping
            float smallerEnd =  Math.min(fTableEnd, sTableEnd);
            float biggerStart = Math.max(fTableStart, sTableStart);
            float dif = biggerStart - smallerEnd;
    
            return biggerStart - dif/2 - SSettingsSingleton.getInstance().getSpacing();
        }
    }
    
    /**
     * method for calculating the center of the SFKView line
     * @return the center y position
     */
    private float calLineCenterY(){
        float fTableY = getFTableItemPositions().second;
        float sTableY = getSTableItemPositions().second;
    
        float lineDifY = Math.max(fTableY, sTableY) - Math.min(fTableY, sTableY);
        return Math.max(fTableY, sTableY) - lineDifY;
    }
}