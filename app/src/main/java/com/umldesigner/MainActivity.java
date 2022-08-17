package com.umldesigner;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.umldesigner.activities.uml_activity.views.table.STableBuilder;
import com.umldesigner.activities.uml_activity.views.table.STableView;
import com.umldesigner.api.controller.uml_activity.item.SItemController;
import com.umldesigner.api.controller.uml_activity.table.STableController;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.infrastructure.uml.logic.api.ApiMethodCodes;
import com.umldesigner.infrastructure.uml.logic.api.BaseAPIControllerTemplate;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;

public class MainActivity extends AppCompatActivity implements ReceiverInterface {
    public static float dp;
    public static float spacing;
    @Getter
    private ViewGroup container;
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
        
        sTable1 = new STableBuilder(container,"Product", 1, 1)
                .addItems(sAdapterFields1)
                .build();
    
        sTable2 = new STableBuilder(container, "Student", 13, 13)
                .addItems(sAdapterFields2)
                .build();
    
        SItemController itemController = new SItemController(this, this);
        itemController.getAllItems();
    
        STableController sTableController = new STableController(this, this);
        sTableController.getAllTables();
    }
    
    @Override
    public void receiveData(List<?> requestedData, BaseAPIControllerTemplate controller, ApiMethodCodes code) {
        if (controller.getEndpoint().equals(Endpoints.TABLE)){
            switch (code){
                case getAll:
                    Log.d("Debug", "receiveData: getAll");
                    //SSettings.getInstance().clearViews();
                    
                    HashMap<Integer, SObject> objects = SSettings.getInstance().getAllViews();
                    
                    List<STablePojo> tables = (List<STablePojo>) requestedData;
                    for(STablePojo pojo : tables){
                        
                        ArrayList<SItemData> items = new ArrayList<>();
                        for(SItemPojo itemPojo : pojo.getItems()){
                            items.add(new SItemData(itemPojo));
                        }
                        
                        new STableBuilder(container, pojo.getTitle(), pojo.getX(), pojo.getY())
                                .addItems(items)
                                .build();
                    }
                    
                    break;
                default:
                    throw new IllegalStateException("the current receiver is unable to handle the current state");
            }
        } else {
            throw new IllegalStateException("the current receiver is unable to handle the current state");
        }
    }
}

