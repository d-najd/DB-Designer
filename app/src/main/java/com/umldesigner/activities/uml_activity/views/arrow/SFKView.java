package com.umldesigner.activities.uml_activity.views.arrow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.umldesigner.activities.uml_activity.views.arrow.Connection.SFKConnectionView;
import com.umldesigner.infrastructure.uml.entities.Movable;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObserver;

import java.util.HashSet;

import javax.security.auth.Destroyable;

//https://blogs.sitepointstatic.com/examples/tech/svg-curves/cubic-curve.html

public class SFKView extends View implements Movable, Destroyable, BaseObserver {
    private final SFKBuilder sfkBuilder;
    private final SSettingsSingleton settingsInstance;
    
    private SFKConnectionView firstKey;
    private SFKConnectionView secondKey;
    
    private final int color = Color.argb(255, 150, 150, 150);
    public Paint paint;
    
    private final float lineX;
    
    /**
     * getting of values will be done through a interface which will list all of the connections and
     * when a user pressed one of the connections it will give us the baseObserver and that should
     * be enough
     */
    private HashSet<BaseObserver> arrowConnectors = new HashSet<>();
   
    public SFKView(SFKBuilder sfkBuilder){
        super(sfkBuilder.getContainer().getContext());
        
        createPaint();
    
        settingsInstance = SSettingsSingleton.getInstance();
        ViewGroup container = sfkBuilder.getContainer();
    
        this.setX(1 * settingsInstance.getSpacing());
        this.setY(1 * settingsInstance.getSpacing());
        this.lineX = sfkBuilder.getLineX();
        
        this.sfkBuilder = sfkBuilder;
        
        this.firstKey = (sfkBuilder.getFConnectionView());
        this.secondKey = (sfkBuilder.getSConnectionView());
        
        this.setMinimumWidth((int) 10000);
        this.setMinimumHeight((int) 10000);
    
        container.addView(this);
    }
    
    
    public void setFirstKey(SFKConnectionView firstKey) {
        this.firstKey = firstKey;
    }
    
    public void setSecondKey(SFKConnectionView secondKey) {
        this.secondKey = secondKey;
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
     * draws the line which sits between the 2 given tables
     * <pre>
     *  ______
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