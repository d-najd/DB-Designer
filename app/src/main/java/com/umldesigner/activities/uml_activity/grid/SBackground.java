package com.umldesigner.activities.uml_activity.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.umldesigner.infrastructure.uml.logic.app.SSettings;


/**
 * basically the grid on the background
 */

class SBackground extends View {
    private final Paint paint = new Paint();
    
    public SBackground(Context context) {
        super(context);
        paint.setColor(Color.parseColor("#353535"));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    
        Log.d("Execute ", "onDraw Schema Background");
        
        //float yOff = topBar.getHeight();
        float yOff = 0;
        
        for (int x = 0; x < 1000; x++){
            for (int y = 0; y < 1000; y++){
                float xVal = x * SSettings.getInstance().getSpacing();
                float yVal = y * SSettings.getInstance().getSpacing() + yOff;

                canvas.drawCircle(xVal, yVal, 2.5f, paint);
            }
        }
    }
}
