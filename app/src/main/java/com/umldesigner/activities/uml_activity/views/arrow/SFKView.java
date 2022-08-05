package com.umldesigner.activities.uml_activity.views.arrow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.umldesigner.infrastructure.uml.entities.Movable;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObserver;

import javax.security.auth.Destroyable;

//https://blogs.sitepointstatic.com/examples/tech/svg-curves/cubic-curve.html

public class SFKView extends View implements Movable, Destroyable, BaseObserver {
    private final SFKBuilder sfkBuilder;
    private final SSettingsSingleton settingsInstance;
   
    private final int color = Color.argb(255, 150, 150, 150);
    public Paint paint;
    
    private final float lineX;
    
    public SFKView(SFKBuilder sfkBuilder){
        super(sfkBuilder.getContainer().getContext());
        
        createPaint();
    
        settingsInstance = SSettingsSingleton.getInstance();
        ViewGroup container = sfkBuilder.getContainer();
    
        this.setX(1 * settingsInstance.getSpacing());
        this.setY(1 * settingsInstance.getSpacing());
        this.lineX = sfkBuilder.getLineX();
        
        this.sfkBuilder = sfkBuilder;
        
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
       
        drawLine(canvas);
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
    public void drawLine(Canvas canvas){
        Log.d("Execute", "drawLine");
        
        float fTableY = sfkBuilder.getFTableItemPositions().second;
        float sTableY = sfkBuilder.getSTableItemPositions().second;
   
        canvas.drawLine(lineX, fTableY,
                lineX, sTableY, paint);
       
        float fTableX = sfkBuilder.getFTableItemPositions().first - SSettingsSingleton.getInstance().getSpacing();
        float sTableX = sfkBuilder.getSTableItemPositions().first - SSettingsSingleton.getInstance().getSpacing();
        
        if (sfkBuilder.isOverLapping()){
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
    
    @Override
    public void updateObserver(BaseObservable observable, Object args) {
        throw new UnsupportedOperationException();
    }
}