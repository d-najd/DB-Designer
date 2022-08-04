package com.umldesigner.activities.uml_activity.views.arrow.Connection;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.umldesigner.activities.uml_activity.views.arrow.SFKBuilder;
import com.umldesigner.activities.uml_activity.views.arrow.SFKView;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.BaseDestroyable;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObserver;

/**
 * draws a bezier line that connects the fields (visualizes the foreign key)
 *
 * <pre>
 *
 * this is the bezier line we are drawing, including the curve
 *           |
 *  ______  \/
 * |      |---|
 * |      |   |
 *  ------    |    ______
 *            |___|      |
 *             /\ |      |
 *             |   ------
 *               </pre>
 */
@SuppressLint("ViewConstructor")
public class SFKConnectionView extends View implements BaseObserver, BaseDestroyable {
    private final Pair<Float, Float> positions;
    private final boolean firstKey;
    private SFKBuilder builder;
    
    private Paint paint;
    private final int color = Color.argb(255, 150, 150, 150);
    
    /**
     * how much we want the line to be curved, test with no ease to be able to see the effect
     */
    float effect = .0f;
    /**
     * works backwards, 0 is full ease 1 is no ease, defines how "smooth" a curve is
     */
    float ease = .75f;
    float offset = 20 * SSettingsSingleton.getInstance().getDp();
    
    public SFKConnectionView(SFKBuilder builder, Pair<Float, Float> positions, boolean firstKey){
        super(builder.getContainer().getContext());
   
        this.builder = builder;
        this.positions = positions;
        this.firstKey = firstKey;
        this.setMinimumWidth(9999999);
        this.setMinimumHeight((int) (SSettingsSingleton.getInstance().getSpacing() + offset));
        this.setX(builder.getLineX() - offset/2);
        this.setY(positions.second - offset/2 + SSettingsSingleton.getInstance().getSpacing()/2);
        
        calRotation();
        
        builder.getContainer().addView(this);
    }
    
    private float calRotation(){
        if (this.getY() > builder.getLineCenterY()){
            this.setRotationX(180);
            this.setY(this.getY() + SSettingsSingleton.getInstance().getSpacing()/2);
        }
        
        return 0;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    
        createPaint();
        drawCurve(canvas);
        drawLine(canvas);
    }
    
    private void drawLine(Canvas canvas){
        STableData tableData;
        if (firstKey){
            tableData = builder.getFTableData();
        } else {
            tableData = builder.getSTableData();
        }
        
        float lineX = builder.getLineX();
        float tableX = tableData.getX();
        
        if(lineX > tableX + SSettingsSingleton.TABLE_WIDTH){
            tableX += SSettingsSingleton.TABLE_WIDTH;
        }
       
        
        canvas.drawLine(lineX, this.getY(), tableX, this.getY(), paint);
        
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
    public void drawCurve(Canvas canvas){
        Log.d("Execute", "drawCurve");
        
        SSettingsSingleton settingsInstance = SSettingsSingleton.getInstance();
        
        float firstX = settingsInstance.getSpacing() + offset/2;
        float firstY = settingsInstance.getSpacing() + offset/2;
        
        float secondX = 0 + offset/2;
        float secondY = 0 + offset/2;
    
        //setting up the bezier lines
        //applying the "effect" to the beziers (how much the line is curved)
        float bezierX = firstX - (settingsInstance.getSpacing() * effect);
        float bezierY = secondY + (settingsInstance.getSpacing() * effect);
        
        //applying ease to the bezier (how close to the original point they are)
        float firstBezierX = firstX - Math.abs(bezierX - firstX) * ease;
        float firstBezierY = firstY - Math.abs(bezierY - firstY) * ease;
        
        float secondBezierX = secondX + Math.abs(bezierX - secondX) * ease;
        float secondBezierY = secondY + Math.abs(bezierY - secondY) * ease;
        
        //drawing the line
        Path linePath = new Path();
        linePath.moveTo(firstX, firstY); //starting point
        linePath.cubicTo(
                firstBezierX, firstBezierY, //bezier point no 1
                secondBezierX, secondBezierY, //bezier point no 2
                secondX, secondY); //ending point
        canvas.drawPath(linePath, paint);
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
        SFKView container = (SFKView) observable;
        
        //define methods for stuff like moving the connector when a table is moved n stuff
    }
    
    @Override
    public void destroy() {
    
    }
}
