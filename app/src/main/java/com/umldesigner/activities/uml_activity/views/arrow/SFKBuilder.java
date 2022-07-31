package com.umldesigner.activities.uml_activity.views.arrow;

import android.view.ViewGroup;

import com.umldesigner.activities.uml_activity.views.arrow.Connection.SFKConnectionView;
import com.umldesigner.infrastructure.uml.data.STable.STableData;

public class SFKBuilder {
    private SFKView sfkView;
    
    private ViewGroup container;
    private STableData fTableData;
    private STableData sTableData;
    private int sPos;
    private int fPos;
    
    /**
     * creates a SFK builder
     * @param container the viewGroup where the views will be added
     * @param fTableData data of the first table, this is used for getting position of the table n stuff
     * @param fPos position of the field in the field table, the "primary key"
     * @param sTableData data of the second table, this is used for getting position of the table n stuff
     * @param sPos position of the first in the second table, the "secondary key"
     */
    public SFKBuilder(ViewGroup container, STableData fTableData, int fPos, STableData sTableData, int sPos){
        this.container = container;
        this.fTableData = fTableData;
        this.sTableData = sTableData;
        this.fPos = fPos;
        this.sPos = sPos;
        
        sfkView = new SFKView(container);
    }
    
    public SFKView build(){
        SFKConnectionView fConnectionView = buildSFKConnection(fTableData, fPos);
        SFKConnectionView sConnectionView = buildSFKConnection(sTableData, sPos);
        
        return null;
    }
    
    /**
     * creates the specific connection
     * @param tableData used for getting position
     * @param pos position of the field in the table
     * @return instance of SFKConnectionView
     */
    private SFKConnectionView buildSFKConnection(STableData tableData, int pos){
       // tableData.getX();
        
        SFKConnectionView sfkConnection = new SFKConnectionView(container, 0, 0, 0);
        return sfkConnection;
    }
}
