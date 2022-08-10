package com.umldesigner.infrastructure.uml.utils.custom.spinner;


import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public abstract class CustomSpinnerCreator<T> {
    private final Context context;
    private final List<Object> data;
    private View v;
    private final Object[] popUpContents;
    private PopupWindow popupWindow;
    
    public CustomSpinnerCreator(List<Object> data, View v){
        this.context = v.getContext();
        this.data = data;
        
        //TODO possibly clean this up?
        popUpContents = new Object[data.size()];
        data.toArray(popUpContents);
        
        popupWindow = createPopupWindow();
        popupWindow.showAsDropDown(v);
    }
    
    /**
     * sets the adapter to spinner to a given position
     * TODO make sure this doesn't cause visual glitches because we are moving the popup window
     * @param xOff x offset for the popup window
     * @param yOff y offset for the popup window
     */
    public void setCustomPosition(int xOff, int yOff){
        popupWindow.showAsDropDown(v, xOff, yOff);
    }
    
    private PopupWindow createPopupWindow() {
        
        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(context);
        
        // the drop down list is a list view
        ListView listView = new ListView(context);
        
        // set our createAdapter and pass our pop up window contents
        listView.setAdapter(createAdapter(popUpContents));
        
        // set the item click listener
        listView.setOnItemClickListener(new CustomSpinnerListeners(this, popupWindow));
        
        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        
        // set the list view as pop up window content
        popupWindow.setContentView(listView);
        
        return popupWindow;
    }
    
    /**
     * default implementation for creating an adapter for the fields in the
     * adapter, can be overridden
     * TODO make sure that this works because we are using generics
     * @param data array of items used as an input for the fields
     * @return a generic adapter
     */
    protected ArrayAdapter<T> createAdapter(Object[] data){
        ArrayAdapter<String> createAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, (String[]) data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //setting stuff for every item in the list
                String text = getItem(position);
                TextView listItem = new TextView(context);
            
                listItem.setText(text);
                listItem.setTextSize(16);
                if (position != getCount() - 1)
                    listItem.setPadding(32, 16, 16, 13);
                else
                    listItem.setPadding(32, 13, 16, 25);
                listItem.setTextColor(Color.LTGRAY);
            
                return listItem;
            }
        };
    
        return (ArrayAdapter<T>) createAdapter;
    }
    
    /**
     * gets called from the {@link CustomSpinnerListeners} when item gets pressed
     * @param item the item object that pressed the view
     * @param itemPosition the position of said item
     */
    abstract public void pressed(Object item, int itemPosition);
    
    /**
     * TODO check what the **** this is supposed to be
     */
    private int pixelToDp(int pixels){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = 20f;
        float fpixels = metrics.density * dp;
        return (int) (fpixels + 0.5f);
    }
}

