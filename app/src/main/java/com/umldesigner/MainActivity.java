package com.umldesigner;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.umldesigner.activities.uml_activity.STable.builder.STableBuilder;
import com.umldesigner.activities.uml_activity.STable.view.STableView;
import com.umldesigner.activities.uml_activity.STable.controller.STableController;
import com.umldesigner.activities.uml_activity.SItem.data.SItemData;
import com.umldesigner.activities.uml_activity.STable.data.STableData;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.ApiController;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.utils.SUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import lombok.Getter;

public class MainActivity extends AppCompatActivity implements RequestHandler {
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
        new MainActivityListeners(this);
    }
    
    private void displayData(){
            SUtils.getInstance().clearViews();
            ApiController sTableController = new STableController(container);
            sTableController.getAll();

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
    
        HashSet<STableData> re = SUtils.getInstance().getAllTables();
        
        ArrayList<SItemData> sAdapterFields1 = new ArrayList<>(Arrays.asList(
                SItemData.newTestingInstance("ProductId", "int"),
                SItemData.newTestingInstance("ProductId", "int"),
                SItemData.newTestingInstance("ProductId", "int"),
                SItemData.newTestingInstance("ProductName", "varchar(100)")));
    
        ArrayList<SItemData> sAdapterFields2 = new ArrayList<>(Arrays.asList(
                SItemData.newTestingInstance("StudentId", "int"),
                SItemData.newTestingInstance("Grades", "int"),
                SItemData.newTestingInstance("Something", "int"),
                SItemData.newTestingInstance("StudentName", "varchar(100)")));
        
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
    
    @Override
    public void receiveData(List<?> requestedData, ApiController controller, ApiRequest code) {

    }
}

