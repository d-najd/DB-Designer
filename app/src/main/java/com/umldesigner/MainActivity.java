package com.umldesigner;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.umldesigner.activities.uml_activity.views.table.STableBuilder;
import com.umldesigner.activities.uml_activity.views.table.STableView;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.api.controller.schema.table.STableController;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;

import java.util.ArrayList;
import java.util.Arrays;

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
   
    private SSettings settingsInstance;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setup();
        testing();
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
        settingsInstance = SSettings.getInstance();
    }
    
    private void testing(){
        //creating stuff
        ArrayList<SItemData> sAdapterFields1 = new ArrayList<>(Arrays.asList(
                new SItemData("ProductId", "int"),
                new SItemData("ProductId", "int"),
                new SItemData("ProductId", "int"),
                new SItemData("ProductName", "varchar(100)")));
    
        ArrayList<SItemData> sAdapterFields2 = new ArrayList<>(Arrays.asList(
                new SItemData("StudentId", "int"),
                new SItemData("StudentName", "varchar(100)")));
        
        sTable1 = new STableBuilder(container, settingsInstance.getSDragListeners(), "Product", 1, 1)
                .addItems(sAdapterFields1)
                .build();
    
        sTable2 = new STableBuilder(container, settingsInstance.getSDragListeners(), "Student", 13, 13)
                .addItems(sAdapterFields2)
                .build();
    
        STableController sTableController = new STableController(this);
        sTableController.getAllTables();
    }
}

