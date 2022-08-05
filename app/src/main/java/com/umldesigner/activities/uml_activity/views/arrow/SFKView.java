package com.umldesigner.activities.uml_activity.views.arrow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.Message;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.Movable;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObserver;

import javax.security.auth.Destroyable;

//https://blogs.sitepointstatic.com/examples/tech/svg-curves/cubic-curve.html

public class SFKView extends View implements Movable, Destroyable, BaseObserver {
    private final SFKBuilder sfkBuilder;
    private final SSettingsSingleton settingsInstance;
    
    private STableData fTableData;
    private STableData sTableData;
    private int fTablePos;
    private int sTablePos;
    private ViewGroup container;
   
    private final int color = Color.argb(255, 150, 150, 150);
    public Paint paint;
    
    private boolean overLapping;
    
    public SFKView(SFKBuilder sfkBuilder){
        super(sfkBuilder.getContainer().getContext());
        
        createPaint();
    
        settingsInstance = SSettingsSingleton.getInstance();
        ViewGroup container = sfkBuilder.getContainer();
    
        this.setX(1 * settingsInstance.getSpacing());
        this.setY(1 * settingsInstance.getSpacing());
        
        this.sfkBuilder = sfkBuilder;
        this.fTableData = sfkBuilder.getFTableData();
        this.sTableData = sfkBuilder.getSTableData();
        this.fTablePos = sfkBuilder.getFPos();
        this.sTablePos = sfkBuilder.getSPos();
        this.container = sfkBuilder.getContainer();
        
        this.setMinimumWidth((int) 100000);
        this.setMinimumHeight((int) 100000);
    
        
        container.addView(this);
    }
    
    @Override
    public void destroy() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void move(float x, float y) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       
        drawLines(canvas);
    }
    
    
    @Override
    public void updateObserver(BaseObservable observable, Object args) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * draws the line which sits between the 2 given tables along with the connectors n stuff
     * <pre>
     *     and this line
     *           |
     *  ______  \/
     * |      |---|
     * |      |   |   <------ this line
     *  ------    |    ______
     *            |___|      |
     *                |      |
     *                 ------
     *  </pre>
     * @param canvas canvas that we draw on
     */
    public void drawLines(Canvas canvas){
        Log.d("Execute", "drawLines");
        
        float lineX = calLineX();
        
        Pair<Float, Float> fTableItemPositions = calItemPositions(fTableData, fTablePos);
        Pair<Float, Float> sTableItemPositions = calItemPositions(sTableData, sTablePos);
        
        if (fTableItemPositions == null || sTableItemPositions == null){
            destroy();
            throw new IllegalStateException("item positions are invalid");
        }
        
        float fTableX = fTableItemPositions.first - SSettingsSingleton.getInstance().getSpacing();
        float sTableX = sTableItemPositions.first - SSettingsSingleton.getInstance().getSpacing();
        
        float fTableY = fTableItemPositions.second;
        float sTableY = sTableItemPositions.second;
        
        //draw the main line
        canvas.drawLine(lineX, fTableY,
                lineX, sTableY, paint);
        
        //draw the connectors
        if (overLapping){
            fTableX += SSettingsSingleton.TABLE_WIDTH;
            sTableX += SSettingsSingleton.TABLE_WIDTH;
        } else if (fTableX < sTableX) {
            fTableX += SSettingsSingleton.TABLE_WIDTH;
        } else {
            sTableX += SSettingsSingleton.TABLE_WIDTH;
        }
        
        canvas.drawLine(fTableX, fTableY,
                lineX, fTableY, paint);
        
        canvas.drawLine(sTableX, sTableY,
                lineX, sTableY, paint);
    }
    
    /**
     * calculates the x and y position of a given item inside the table
     * @return Pair(x, y) positions of the requested item
     * @param tableData the data where the item is located at
     * @param pos position in the list of the item
     */
    private Pair<Float, Float> calItemPositions(STableData tableData, int pos){
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
            Message.defErrMessage(getContext());
        }
        return null;
    }
    
    /**
     * calculates the x position of the line and if the tables are overlapping
     * @return x pos of the main line
     */
    private float calLineX(){
        float fTableStart = fTableData.getX();
        float sTableStart = sTableData.getX();
        
        float fTableEnd = SSettingsSingleton.TABLE_WIDTH + fTableStart;
        float sTableEnd = SSettingsSingleton.TABLE_WIDTH + sTableStart;
        
        //if overlapping
        if (sTableStart < fTableEnd && fTableStart < sTableEnd){
            overLapping = true;
            return Math.max(fTableEnd, sTableEnd);
        } else { //if not overlapping
            float smallerEnd =  Math.min(fTableEnd, sTableEnd);
            float biggerStart = Math.max(fTableStart, sTableStart);
            float dif = biggerStart - smallerEnd;
            
            return biggerStart - dif/2 - SSettingsSingleton.getInstance().getSpacing();
        }
    }
    
    private Paint createPaint(){
        Log.d("Execute", "createPaint");
        
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        
        return paint;
    }
}