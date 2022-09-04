package com.umldesigner;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.umldesigner.activities.uml_activity.STable.STableBuilder;
import com.umldesigner.activities.uml_activity.STable.STableView;
import com.umldesigner.activities.uml_activity.STable.STableControllerImpl;
import com.umldesigner.activities.uml_activity.SItem.SItemData;
import com.umldesigner.activities.uml_activity.STable.STableData;
import com.umldesigner.activities.uml_activity.grid.SDragListeners;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.utils.SUtils;

import java.util.*;

import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import lombok.Getter;

public class MainActivity extends AppCompatActivity {
    public static float dp;
    public static float spacing;
    @Getter
    private ViewGroup container;
    @Getter
    private STableView sTable1;
    @Getter
    private STableView sTable2;

    @Getter
    public static STableData sTableDataTest;

    @Getter
    private static SDragListeners dragListeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        displayData();
        //testing();
    }
    
    //no singletons or utils should be used before this method
    private void setup(){
        //initializing stuff for the singleton
        dp = getResources().getDisplayMetrics().density;
        //the spacing gets converted to int which can cause problems to say the least so I have to do this to get precision
        spacing = (float) (getResources().getDimensionPixelSize(R.dimen.spacing10000x)) / 10000;
    
        container = findViewById(R.id.container);
        dragListeners = new MainActivityListeners(this).getSDragListeners();
    }
    
    private void displayData(){
            SUtils.getInstance().clearViews();
            ApiController<STablePojo> tController = new STableControllerImpl(container);
            tController.getAll();

            /*
            for(STableData curData : SUtils.getInstance().getAllTables()){
                new STableBuilder(container, curData.getTitle(), curData.getX(), curData.getY())
                        .addItems((ArrayList<SItemData>) curData.getItems())
                        .build();
            }
             */
    }
    
    private void testing(){
        //creating stuff
    
        //Set<STableData> re = SUtils.getInstance().getTables();
        
        ArrayList<SItemData> sAdapterFields1 = new ArrayList<>(Arrays.asList(
                SItemData.newNoTableInstance("ProductId", "int"),
                SItemData.newNoTableInstance("ProductId", "int"),
                SItemData.newNoTableInstance("ProductId", "int"),
                SItemData.newNoTableInstance("ProductName", "varchar(100)")));
    
        ArrayList<SItemData> sAdapterFields2 = new ArrayList<>(Arrays.asList(
                SItemData.newNoTableInstance("StudentId", "int"),
                SItemData.newNoTableInstance("Grades", "int"),
                SItemData.newNoTableInstance("Something", "int"),
                SItemData.newNoTableInstance("StudentName", "varchar(100)")));
        
        sTable1 = new STableBuilder("testee1", container,"Product", 1, 1)
                .addItems(sAdapterFields1)
                .build();

        sTable2 = new STableBuilder("testee2", container, "Student", 13, 13)
                .addItems(sAdapterFields2)
                .build();

        sTableDataTest = sTable1.getData();

       // Pattern valuePattern = Pattern.compile("\\(.*");
       // String matcher = valuePattern.matcher("varchar(50)").group();
       // Log.d("Execute", matcher);
    
    }
}

