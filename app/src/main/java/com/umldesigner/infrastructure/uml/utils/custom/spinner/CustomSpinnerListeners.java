package com.umldesigner.infrastructure.uml.utils.custom.spinner;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

public class CustomSpinnerListeners implements OnItemClickListener {
    
    private final CustomSpinnerCreator customSpinnerCreator;
    private final PopupWindow popupWindow;
    
    public CustomSpinnerListeners(CustomSpinnerCreator customSpinnerCreator, PopupWindow popupWindow) {
        this.customSpinnerCreator = customSpinnerCreator;
        this.popupWindow = popupWindow;
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // get the context and main activity to access variables
        Context mcontext = view.getContext();
        
        //add some animation when a list item was clicked
        Animation fadeInAnimation = AnimationUtils.loadAnimation(mcontext, android.R.anim.fade_in);
        fadeInAnimation.setDuration(10);
        view.startAnimation(fadeInAnimation);
        
        String selectedItemText = ((TextView)view).getText().toString();
        
        customSpinnerCreator.pressed(selectedItemText, position);
        popupWindow.dismiss();
    }
}