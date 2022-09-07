package com.umldesigner.activities.uml_activity.SFK;

import android.view.ViewGroup;

import com.umldesigner.activities.uml_activity.STable.STableData;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class SFKBuilder {
    @Getter(AccessLevel.NONE)
    private SFKView sfkView;
    private final ViewGroup container;
    private final STableData fTableData;
    private final STableData sTableData;
    private final int sPos;
    private final int fPos;
    private boolean shouldRegister = true;
    /**
     * creates a SFK builder
     * @param container the viewGroup where the views will be added
     * @param fTableData data of the first table, this is used for getting position of the table n stuff
     * @param fPos position of the field in the field table, the "primary key"
     * @param sTableData data of the second table, this is used for getting position of the table n stuff
     * @param sPos position of the first in the second table, the "secondary key"
     * @implNote it is possible to swap this is {@link com.umldesigner.infrastructure.uml.data.BaseDataInterface}
     * if need arise, we may be able to get the position of the recyclerview when instantiating a
     * table and the position of the item when instantiation the items and this will lead to fewer
     * dependencies and possibility to extend to more than just a simple table, but I have no plans for
     * that
     */
    public SFKBuilder(ViewGroup container, STableData fTableData, int fPos, STableData sTableData, int sPos){
        this.container = container;
        this.fTableData = fTableData;
        this.sTableData = sTableData;
        this.fPos = fPos;
        this.sPos = sPos;
    }
    
    /**
     * notifies that the builder should not register the current view as an observer,
     * default is true
     */
    public SFKBuilder shouldNotRegister(){
        shouldRegister = false;
        return this;
    }
    
    public SFKView build(){
        sfkView = new SFKView(this);
        if (shouldRegister) {
            fTableData.registerObserver(sfkView);
            sTableData.registerObserver(sfkView);
        }
        return sfkView;
    }

}