package com.umldesigner.activities.uml_activity.views.sfk;

import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.Message;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;

public class SFKFacade {
    private SFKView sfkView;
    
    public SFKFacade(SFKView view){
        this.sfkView = view;
    }
    
    /**
     * calculates the x and y position of a given item inside the table
     * @return Pair(x, y) positions of the requested item
     * @param tableData the data where the item is located at
     * @param pos position in the list of the item
     */
    public Pair<Float, Float> calItemPositions(STableData tableData, int pos){
        Log.d("Execute", "calItemPositions with parameters: tableData (toString doesn't work), pos " + pos);
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
            Message.defErrMessage(sfkView.getContext());
        }
        return null;
    }
    
    /**
     * calculates the x position of the line and if the tables are overlapping
     * @return x pos of the main line
     */
    public float calLineX(){
        Log.d("Execute", "calLineX");
        
        float fTableStart = sfkView.getFTableData().getX();
        float sTableStart = sfkView.getSTableData().getX();
        
        float fTableEnd = SSettings.TABLE_WIDTH + fTableStart;
        float sTableEnd = SSettings.TABLE_WIDTH + sTableStart;
        
        //if overlapping
        if (sTableStart < fTableEnd && fTableStart < sTableEnd){
            return Math.max(fTableEnd, sTableEnd);
        } else { //if not overlapping
            float smallerEnd =  Math.min(fTableEnd, sTableEnd);
            float biggerStart = Math.max(fTableStart, sTableStart);
            float dif = biggerStart - smallerEnd;
            
            return biggerStart - dif/2 - SSettings.getInstance().getSpacing();
        }
    }
    
    /**
     * checks if the tables are overlapping
     * @return true if the tables are overlapping false if the are not
     */
    public boolean isOverLapping() {
        Log.d("Execute", "isOverlapping");
        float fTableStart = sfkView.getFTableData().getX();
        float sTableStart = sfkView.getSTableData().getX();
        
        float fTableEnd = SSettings.TABLE_WIDTH + fTableStart;
        float sTableEnd = SSettings.TABLE_WIDTH + sTableStart;
        
        return sTableStart < fTableEnd && fTableStart < sTableEnd;
    }

}
