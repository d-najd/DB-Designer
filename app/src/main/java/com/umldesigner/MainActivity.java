package com.umldesigner;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.umldesigner.activities.uml_activity.SGridCreate;
import com.umldesigner.activities.uml_activity.SListeners;
import com.umldesigner.activities.uml_activity.views.table.STableBuilder;
import com.umldesigner.activities.uml_activity.views.table.STableView;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.SObjectFactory;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.Getter;

public class MainActivity extends AppCompatActivity implements ReceiverInterface{
    @Getter
    private ViewGroup container;
    private SObjectFactory sObjectFactory;
    public static float dp;
    public static SListeners listeners;
    public static float spacing;
    
    @Getter
    private STableView sTable1;
    @Getter
    private STableView sTable2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        //initializing stuff for the singleton
        dp = getResources().getDisplayMetrics().density;
        //the spacing gets converted to int which can cause problems to say the least so I have to do this to get precision
        spacing = (float) (getResources().getDimensionPixelSize(R.dimen.spacing10000x)) / 10000;
        
        
        container = findViewById(R.id.container);
        setListeners();
        
        sObjectFactory = new SObjectFactory(container, listeners);
        
        SSettingsSingleton settingsInstance = SSettingsSingleton.getInstance();
        
        //creating stuff
        ArrayList<SItemData> umlAdapterFieldArrayList = new ArrayList<>(Arrays.asList(
                new SItemData("ProductId", "int"),
                new SItemData("ProductId", "int"),
                new SItemData("ProductId", "int"),
                new SItemData("ProductName", "varchar(100)")));
        
        sTable1 = new STableBuilder(container, listeners, "title", 1, 1)
                .addItems(umlAdapterFieldArrayList)
                .build();
        
        sTable2 = new STableBuilder(container, listeners, "title1", 13, 13)
                .addItems(umlAdapterFieldArrayList)
                .build();
        
        //SFKConnectionView connection1 = new SFKConnectionView(container, 10 * settingsInstance.getSpacing(), 10 * settingsInstance.getSpacing(), 180);
        //SFKConnectionView connection2 = new SFKConnectionView(container, 10 * settingsInstance.getSpacing(), 10 * settingsInstance.getSpacing(), 0);
    
        
        //depricated, replace with builder in future
        //container.addView((View) sObjectFactory.create("arrow",
        //        new float[]{3.5f * spacing, 13f * spacing, 9.5f * spacing, 18f * spacing}));
    }
    
    
    /**
     * creates listener for the fab, this will need altering in the future
     */
    private void setListeners(){
        View fab = findViewById(R.id.createTableFab);
        fab.setOnClickListener(new MainActivityListeners(this));
    
        listeners = new SGridCreate(container).getListeners();
    }
    
    @Override
    public boolean receiveData(Object sentData) {
        return false;
    }
}

