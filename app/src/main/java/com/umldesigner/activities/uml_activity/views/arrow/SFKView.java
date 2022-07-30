package com.umldesigner.activities.uml_activity.views.arrow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.umldesigner.activities.uml_activity.views.arrow.Connection.SFKConnectionView;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObserver;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.pojos.BasePojo;

import java.util.HashSet;

import lombok.Getter;

//https://blogs.sitepointstatic.com/examples/tech/svg-curves/cubic-curve.html

public class SFKView extends View implements SObject, BaseObservable {
    private final int color = Color.argb(255, 150, 150, 150);
    @Getter
    private ViewGroup container;
    
    private float firstX;
    private float firstY;
    
    private float secondX;
    private float secondY;
    
    private SSettingsSingleton settingsInstance;
    
    public Paint paint;
    public float center;
    
    
    /**
     * getting of values will be done through a interface which will list all of the connections and
     * when a user pressed one of the connections it will give us the baseObserver and that should
     * be enough
     */
    private HashSet<BaseObserver> arrowConnectors = new HashSet<>();
    
    public SFKView(ViewGroup container) {
        super(container.getContext());
        createPaint();
        
        settingsInstance = SSettingsSingleton.getInstance();
        this.container = container;
        
        this.setX(1 * settingsInstance.getSpacing());
        this.setY(1 * settingsInstance.getSpacing());
        
        firstX = 0;
        firstY = 0;
        secondX = 20 * settingsInstance.getSpacing();
        secondY = 20 * settingsInstance.getSpacing();
    
        this.setMinimumWidth((int) secondX);
        this.setMinimumHeight((int) secondY);
        
        container.addView(this);
    }
    
    @Override
    public void destroy() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public <T extends BasePojo & BaseDataInterface> void setData(T data) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void updateData() {
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
        drawBezier(canvas);
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
        Log.d("Execute:", "drawLine");
       
        center = secondX/2;
        
        canvas.drawLine(center, this.getY() + settingsInstance.getSpacing(),
               center,  getHeight() - settingsInstance.getSpacing(), paint);
        
    }
    
    /**
     * draws a bezier line that connects the fields (visualizes the foreign key)
     *
     * <pre>
     *
     * this is the line we are drawing, including the curve
     *           |
     *  ______  \/
     * |      |---|
     * |      |   |
     *  ------    |    ______
     *            |___|      |
     *             /\ |      |
     *             |   ------
     *               </pre>
     * @param canvas canvas that we draw on
     */
    public void drawBezier(Canvas canvas){
        Log.d("Execute:", "drawBezier");
        
        SFKConnectionView arrowConnector = new SFKConnectionView(this, 10 * settingsInstance.getSpacing(), 10 * settingsInstance.getSpacing(), 0);
        
        registerObserver(arrowConnector);
    }
    
    private Paint createPaint(){
        Log.d("Execute:", "createPaint");
        
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
    public void registerObserver(BaseObserver o) {
        Log.d("Execute:", "registerObserver with parameter " + o.toString());
        
        arrowConnectors.add(o);
    }
    
    //TODO destroy the view as well
    @Override
    public void removeObserver(BaseObserver o) {
        Log.d("Execute:", "removeObserver with parameter" + o.toString());
        //SArrowConnector arrowConnector = (SArrowConnector)o;
        
        arrowConnectors.remove(o);
    }
    
    @Override
    public void notifyObservers() {
        Log.d("Execute:", "notifyObservers");
        
       for (BaseObserver observer : arrowConnectors){
           observer.updateObserver(this, null);
       }
    }
}