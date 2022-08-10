package com.umldesigner;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.umldesigner.activities.uml_activity.views.table.STableBuilder;
import com.umldesigner.activities.uml_activity.views.table.STableView;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import lombok.Getter;

public class MainActivity extends AppCompatActivity {
    @Getter
    private ViewGroup container;
    public static float dp;
    public static float spacing;
    
    @Getter
    private STableView sTable1;
    @Getter
    private STableView sTable2;
   
    private SSettingsSingleton settingsInstance;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setup();
        
        //creating stuff
        ArrayList<SItemData> umlAdapterFieldArrayList = new ArrayList<>(Arrays.asList(
                new SItemData("ProductId", "int"),
                new SItemData("ProductId", "int"),
                new SItemData("ProductId", "int"),
                new SItemData("ProductName", "varchar(100)")));
        
        sTable1 = new STableBuilder(container, settingsInstance.getSDragListeners(), "title", 1, 1)
                .addItems(umlAdapterFieldArrayList)
                .build();
        
        sTable2 = new STableBuilder(container, settingsInstance.getSDragListeners(), "title1", 13, 13)
                .addItems(umlAdapterFieldArrayList)
                .build();
    
        HashMap<Integer, SObject> a = settingsInstance.getAllViews();
    }

    private void setup(){
        //initializing stuff for the singleton
        dp = getResources().getDisplayMetrics().density;
        //the spacing gets converted to int which can cause problems to say the least so I have to do this to get precision
        spacing = (float) (getResources().getDimensionPixelSize(R.dimen.spacing10000x)) / 10000;
    
        container = findViewById(R.id.container);
        new MainActivityListeners(this);
    
        /*
          creating the singleton, NOTE all fields before the singleton are needed for its creation
         */
        settingsInstance = SSettingsSingleton.getInstance();
    }
}

