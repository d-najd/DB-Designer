package com.umldesigner.activities.uml_activity.views.arrow.Connection;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.umldesigner.activities.uml_activity.views.arrow.SFKView;
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
    
    public SFKConnectionView(ViewGroup container, float xPos, float yPos, float rotation) {
        super(container.getContext());
        createPaint();
        
        this.setMinimumWidth((int) (SSettingsSingleton.getInstance().getSpacing() + offset + 20));
        this.setMinimumHeight((int) (SSettingsSingleton.getInstance().getSpacing() + offset + 20));
        if (rotation == 180){
            xPos -= 20;
        }
        this.setX(xPos);
        this.setY(yPos);
        this.setRotationY(rotation);
        //container.addView(this);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        drawCurve(canvas);
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
        
        float firstX = settingsInstance.getSpacing() + offset;
        float firstY = settingsInstance.getSpacing() + offset;
    
        float secondX = 0 + offset;
        float secondY = 0 + offset;
    
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
        //testing
        paint.setColor(Color.GREEN);
    
        canvas.drawLine(firstBezierX, firstBezierY, firstBezierX, firstBezierY, paint);
        canvas.drawLine(secondBezierX, secondBezierY, secondBezierX, secondBezierY, paint);
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
