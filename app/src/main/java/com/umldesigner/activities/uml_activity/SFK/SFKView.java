package com.umldesigner.activities.uml_activity.SFK;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import com.umldesigner.activities.uml_activity.STable.STableData;
import com.umldesigner.activities.uml_activity.STable.STableDataBuffer;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;
import com.umldesigner.infrastructure.uml.logic.app.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.app.observer.BaseObserver;
import lombok.Getter;

import javax.security.auth.Destroyable;

//https://blogs.sitepointstatic.com/examples/tech/svg-curves/cubic-curve.html

@SuppressLint("ViewConstructor")
public class SFKView extends View implements Destroyable, BaseObserver {
    private final SFKFacade sfkFacade;
    
    @Getter
    private final STableData fTableData;
    @Getter
    private final STableData sTableData;
    @Getter
    private final int fTablePos;
    @Getter
    private final int sTablePos;
    private final ViewGroup container;
    
    /**
     * we don't want to hold reference to the sfkBuilder because it will become out of sync, we want
     * to use the BaseObserver interface for updating data
     */
    private final int color = Color.argb(255, 150, 150, 150);
    
    public SFKView(SFKBuilder sfkBuilder){
        super(sfkBuilder.getContainer().getContext());

        SSettings settingsInstance = SSettings.getInstance();
        sfkFacade = new SFKFacade(this);
        
        this.fTableData = sfkBuilder.getFTableData();
        this.sTableData = sfkBuilder.getSTableData();
        this.fTablePos = sfkBuilder.getFPos();
        this.sTablePos = sfkBuilder.getSPos();
        this.container = sfkBuilder.getContainer();
        
        this.setX(1 * settingsInstance.getSpacing());
        this.setY(1 * settingsInstance.getSpacing());
        this.setMinimumWidth(100000);
        this.setMinimumHeight(100000);
        
        container.addView(this);
    }
    
    /**
     * @implNote this will cause problems if the view is not removed from the sdk's inside the
     * TableData which contain this (one contains this key as a primary fTableData, the other as
     * secondary sTableData)
     */
    @Override
    public void destroy() {
        fTableData.removeObserver(this);
        sTableData.removeObserver(this);
        
        container.removeView(this);
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       
        drawLines(canvas);
    }
    
    /**
     * @see STableDataBuffer for more info
     */
    @Override
    public void updateObserver(BaseObservable observable, Object args) {
        if (args instanceof STableDataBuffer){
            SFKView newView = new SFKBuilder(container, fTableData, fTablePos, sTableData, sTablePos)
                    .shouldNotRegister().build();
            
            ((STableDataBuffer) args).addValue(this, newView);
        } else {
            throw new UnsupportedOperationException();
        }
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
        
        float lineX = sfkFacade.calLineX();
        boolean overLapping = sfkFacade.isOverLapping();
        Paint paint = createPaint();
        
        Pair<Float, Float> fTableItemPositions = sfkFacade.calItemPositions(fTableData, fTablePos);
        Pair<Float, Float> sTableItemPositions = sfkFacade.calItemPositions(sTableData, sTablePos);
        
        if (fTableItemPositions == null || sTableItemPositions == null){
            destroy();
            throw new IllegalStateException("item positions are invalid");
        }
        
        float fTableX = fTableItemPositions.first - SSettings.getInstance().getSpacing();
        float sTableX = sTableItemPositions.first - SSettings.getInstance().getSpacing();
        
        float fTableY = fTableItemPositions.second;
        float sTableY = sTableItemPositions.second;
    
        Path path = new Path();
        path.moveTo(lineX, fTableY);
        path.lineTo(lineX, sTableY);
        canvas.drawPath(path, paint);
    
        //draw the connectors
        if (overLapping){
            fTableX += SSettings.getInstance().getTableWidth();
            sTableX += SSettings.getInstance().getTableWidth();
        } else if (fTableX < sTableX) {
            fTableX += SSettings.getInstance().getTableWidth();
        } else {
            sTableX += SSettings.getInstance().getTableWidth();
        }
        
        canvas.drawLine(fTableX, fTableY,
                lineX, fTableY, paint);
        
        canvas.drawLine(sTableX, sTableY,
                lineX, sTableY, paint);
    }
    
    private Paint createPaint(){
        Log.d("Execute", "createPaint");
        
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        
        return paint;
    }
}