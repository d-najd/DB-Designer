package com.umldesigner;

import android.content.res.Resources;
import android.view.View;

import com.umldesigner.infrastructure.uml.utils.DialogType;
import com.umldesigner.activities.uml_activity.STable.dialog.STableDialog;
import com.umldesigner.activities.uml_activity.grid.SDragListeners;
import com.umldesigner.activities.uml_activity.grid.SGridCreate;

public class MainActivityListeners implements View.OnClickListener {
    private final MainActivity mainActivity;
    public static SDragListeners sDragListeners;
    public MainActivityListeners(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        setListeners();
    }
    private void setListeners(){
        View fab = mainActivity.findViewById(R.id.createTableFab);
        fab.setOnClickListener(this);
    
        sDragListeners = new SGridCreate(mainActivity.getContainer()).getListeners();
    }
    @Override
    public void onClick(View view) {
        Resources resources = view.getResources();
        view.getResources().getString(R.string.createTableFab);
        if (resources.getString(R.string.createTableFab).equals(view.getTag())) {
            //TODO this is hard coded for testing and needs to be removed in future
            
            //STableData tableData = (STableData) SUtils.getInstance().getViewById(1).getData();
            //STableData tableData2 = (STableData) SUtils.getInstance().getViewById(2).getData();
            
            //new SFKBuilder(mainActivity.getContainer(), tableData, 0, tableData2, 3).build();
   
            STableDialog dialog = new STableDialog(mainActivity.getContainer(), null, DialogType.Create);
            dialog.show();
        } else {
            throw new IllegalStateException("invalid MainActivityListener tag " + view.getTag());
        }
    }

}