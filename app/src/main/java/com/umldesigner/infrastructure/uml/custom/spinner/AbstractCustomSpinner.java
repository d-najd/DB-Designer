package com.umldesigner.infrastructure.uml.custom.spinner;


import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;

/**
 * template for creating spinners
 * @param <T> the type data that the fields inside the spinner will use
 */
public abstract class AbstractCustomSpinner<T> {
    @Getter
    private final Context context;
    @Getter
    private final List<T> listData;
    @Getter
    private final View parentView;
    private final PopupWindow popupWindow;

    public AbstractCustomSpinner(List<T> listData, View view){
        this.context = view.getContext();
        this.listData = listData;
        this.parentView = view;

        popupWindow = createPopupWindow();
        
        if(popupWindowPosition() == null) {
            popupWindow.showAsDropDown(view);
        } else {
            popupWindow.showAsDropDown(view, popupWindowPosition().first, popupWindowPosition().second);
        }
        
        popupWindow.setWidth(100);
    }

    /**
     * gets called from the {@link CustomSpinnerListeners} when item gets pressed
     * @param item the item object that pressed the view
     * @param itemPosition the position of said item
     */
    abstract public void pressed(TextView item, int itemPosition);
    
    /**
     * method for creating the popup window, links to hooks for modifying the window are provided
     * below
     * @return a popup window
     * @see #setPopupWindowDimensions(PopupWindow)
     * @see #popupWindowPosition()
     */
    private PopupWindow createPopupWindow() {
        
        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(context);
        
        // the drop down list is a list view
        ListView listView = new ListView(context);
        
        // set our createAdapter and pass our pop up window contents
        listView.setAdapter(createAdapter(listData));
        
        // set the item click listener
        listView.setOnItemClickListener(new CustomSpinnerListeners(this, popupWindow));
        
        setPopupWindowDimensions(popupWindow);
        
        // some other visual settings
        popupWindow.setFocusable(true);
        
        // set the list view as pop up window content
        popupWindow.setContentView(listView);
        
        return popupWindow;
    }
    
    /**
     * default implementation for creating an adapter for the fields in the
     * adapter, can be overridden
     * @apiNote this implementation is designed for strings, if you plan to use integers or something
     * else you will need to override it
     * @implNote if there is a change in the style of the items consider making changes to the
     * template first
     * @param data array of items used as an input for the fields
     * @return a generic adapter
     */
    protected ArrayAdapter<T> createAdapter(List<T> data){
        ArrayAdapter<T> createAdapter = new ArrayAdapter<T>(context,
                android.R.layout.simple_spinner_dropdown_item, (List<T>) data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String data = getTitle(getItem(position));
                TextView listItem = new TextView(context);
                
                listItem.setText(data);
               
                setPopupFieldStyle(listItem, position, getCount());
                
                return listItem;
            }
        };
        
        return (ArrayAdapter<T>) createAdapter;
    }

    /**
     * hook for allowing the user to have custom title implementation, used for when <T> is not string
     * @return string title
     */
    protected String getTitle(T o){
        return (String) o;
    }
    
    /**
     * sets the dimensions on a given popup window, dimensions of the popupWindow can be changed
     * using the hooks below
     * @param popupWindow the given popupWindow
     * @see #getWidth(), hook for setting custom width
     * @see #getHeight(), hook for setting custom height
     * @see #createPopupWindow()
     */
    private void setPopupWindowDimensions(PopupWindow popupWindow){
        if (getWidth() != 0) {
            popupWindow.setWidth(getWidth());
        } else {
            popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        }
        if (getHeight() != 0) {
            popupWindow.setHeight(getHeight());
        } else {
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }
    
    //region hooks


    /**
     * hook for setting the popup window to a custom location on screen
     * @return Pair<XPos, YPos> of where the window will be located
     */
    protected Pair<Integer, Integer> popupWindowPosition(){
        return null;
    }

    /**
     * a hook for setting width of the popup window, if 0 will be ignored
     * @return width for the popup window
     * @see #setPopupWindowDimensions(PopupWindow)
     */
    protected Integer getWidth(){
        return 0;
    }
    
    /**
     * a hook for setting height of the popup window, if 0 will be ignored
     * @return height for the popup window
     * @see #setPopupWindowDimensions(PopupWindow)
     */
    protected Integer getHeight(){
        return 0;
    }
    
    /**
     * a hook for changing the style of the fields inside the popupWindow
     * @param listItem the current field
     * @param position position of the field
     * @param count count of all items?
     * @see #createAdapter(List)
     */
    protected void setPopupFieldStyle(TextView listItem, int position, int count){
        listItem.setTextSize(16);
        if (position != count - 1)
            listItem.setPadding(32, 16, 16, 13);
        else
            listItem.setPadding(32, 13, 16, 25);
        listItem.setTextColor(Color.WHITE);
    }
    
    //endregion
}

