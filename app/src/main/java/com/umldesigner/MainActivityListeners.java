package com.umldesigner;

import android.content.res.Resources;
import android.view.View;

import com.umldesigner.activities.uml_activity.CreateTableDialog;
import com.umldesigner.activities.uml_activity.views.sfk.SFKBuilder;
import com.umldesigner.activities.uml_activity.views.sfk.SFKView;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

public class MainActivityListeners implements View.OnClickListener {
    MainActivity mainActivity;
    public MainActivityListeners(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    
    @Override
    public void onClick(View view) {
        Resources resources = view.getResources();
        view.getResources().getString(R.string.createTableFab);
        if (resources.getString(R.string.createTableFab).equals(view.getTag())) {
            //NOTE this is hard coded for testing
            STableData tableData = (STableData) SSettingsSingleton.getInstance().getViewById(1).getData();
            STableData tableData2 = (STableData) SSettingsSingleton.getInstance().getViewById(2).getData();
            
            SFKView builder = new SFKBuilder(mainActivity.getContainer(), tableData, 0, tableData2, 3, true).build();
   
            
            
            //AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            
            //https://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
            
            //builder.setTitle("hello china");
            
    
            //AlertDialog alertDialog = builder.create();
            //alertDialog.show();
            
            CreateTableDialog dialog = new CreateTableDialog(mainActivity);
            dialog.show();
        } else {
            throw new IllegalStateException("invalid MainActivityListener tag " + view.getTag());
        }
    }
}
