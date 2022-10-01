package com.umldesigner.activities.uml_activity.table_item.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.table.STableData;
import com.umldesigner.activities.uml_activity.table_item.SItemControllerImpl;
import com.umldesigner.activities.uml_activity.table_item.SItemData;
import com.umldesigner.infrastructure.uml.error.ErrorTags;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.utils.DialogType;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * the dialog used that pops up to when you create new table
 * @implNote mediator might be suitable here
 */
public class SItemDialog extends Dialog {
    private SItemData data;
    @Getter
    private final STableData tableData;
    private final ViewGroup container;
    private final DialogType type;

    /**
     * dialog constructor
     * @param container the container, used for working with data sent back from the backend
     * @param data the item daya
     * @param tableData the table where the item is located at
     * @param type type of the dialog
     */
    public SItemDialog(ViewGroup container, SItemData data, STableData tableData, DialogType type) {
        super(container.getContext());
        this.data = data;
        this.tableData = tableData;
        this.container = container;
        this.type = type;
    }
    
    /**
     * @return returns list of available actions for the field (actions on the onDelete and OnUpdate
     * fields)
     * @implNote this may not be the best position to store this, but I can't think of another place
     */
    public static List<String> getAllActions(Context context){
        List<String> actions = new ArrayList<>();
        
        actions.add(context.getString(R.string.noAction));
        actions.add(context.getString(R.string.restrict));
        actions.add(context.getString(R.string.cascade));
        actions.add(context.getString(R.string.setNull));
        actions.add(context.getString(R.string.setDefault));
        
        return actions;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_s_item);

        switch (type){
            case Create:
                TextView title = findViewById(R.id.title);
                title.setText(R.string.createItem);
                break;
            case Edit:
                title = findViewById(R.id.title);
                title.setText(R.string.editItem);
                break;
            default:
                throw new IllegalArgumentException("invalid dialog type");
        }

        setupDialogListeners();
        setupFields();
    }
    
    /**
     * method for setting up the ok/cancel buttons
     */
    private void setupDialogListeners(){
        //getting the fields
        TextView okBtn = findViewById(R.id.okBtn);
        TextView cancelBtn = findViewById(R.id.cancelBtn);
        
        //prep
        DialogListeners listeners = new DialogListeners(this);
        
        //setting listeners
        okBtn.setOnClickListener(listeners);
        cancelBtn.setOnClickListener(listeners);
    }
    
    /**
     * method for setting up stuff for the fields inside the dialog, stuff like listeners.
     */
    private void setupFields(){
        SItemDialogFieldListener itemListener = new SItemDialogFieldListener(this);
        
        setupBasicFields();
        setupRadioFields();
        setupFKFields(itemListener);
    }
    
    private void setupBasicFields(){
        // getting the fields
        EditText typeEdt = findViewById(R.id.typeEdit);
        EditText valueEdt = findViewById(R.id.valueEdt);
        /*
        EditText sizeEdt = findViewById(R.id.sizeEdt);
        EditText defaultEdt = findViewById(R.id.defaultEdt);
        EditText descriptionEdt = findViewById(R.id.descriptionEdt);
         */
        // preparing the data
        
        //y = re.findall("\(.*\)", txt1)
        //y = re.split("\(.*", txt1)

        if(data != null) {
            typeEdt.setText(data.getType());
            valueEdt.setText(data.getValue());
        }
    }

    /**
     * sets up the fields which have radio buttons, the fields inside Info tab
     */
    private void setupRadioFields(){
        // getting fields
        View pk = findViewById(R.id.PK);
        View an = findViewById(R.id.AN);
        View uq = findViewById(R.id.UQ);
        View ai = findViewById(R.id.AI);
        View fk = findViewById(R.id.FK);

        // getting listener
        SItemDialogRadioListener radioListener = new SItemDialogRadioListener(this);

        // setting the listeners
        pk.setOnClickListener(radioListener);
        an.setOnClickListener(radioListener);
        uq.setOnClickListener(radioListener);
        ai.setOnClickListener(radioListener);
        fk.setOnClickListener(radioListener);
    }
    
    
    /**
     * sets up fields which have stuff related to the foreign key
     * @param itemListener the listener for the buttons
     */
    private void setupFKFields(SItemDialogFieldListener itemListener){
        // getting the fields
        TextView refTable = findViewById(R.id.refTable);
        TextView refField = findViewById(R.id.refField);
        TextView onUpdate = findViewById(R.id.onUpdate);
        TextView onDelete = findViewById(R.id.onDelete);

        // setting the listeners
        refTable.setOnClickListener(itemListener);
        refField.setOnClickListener(itemListener);
        onUpdate.setOnClickListener(itemListener);
        onDelete.setOnClickListener(itemListener);

        // setting the data
        if(data != null) {
            refField.setText(data.getValue());
        } else {
            if (!tableData.getItems().isEmpty()) {
                refField.setText(tableData.getItems().get(0).getValue());
            } else {
                refField.setText("");
            }
        }

        String tableTitle = "";
        try {
            tableTitle = tableData.getTitle();
        } catch (NullPointerException e){
            Log.w(ErrorTags.APP_ERROR, "Unable to get table title, setting it to empty string");
        }
        refTable.setText(tableTitle);
    }

    /**
     * notifies the refField that the table got changed and for it to update the text to a field of
     * that table
     */
    public void notifyTableChanged(){
        TextView refField = findViewById(R.id.refField);

        if (!tableData.getItems().isEmpty()) {
            refField.setText(tableData.getItems().get(0).getValue());
        } else {
            refField.setText(null);
        }
    }
    
    /**
     * listeners for the start and end buttons
     */
    private class DialogListeners implements View.OnClickListener{
        Dialog dialog;

        public DialogListeners(Dialog dialog){
            this.dialog = dialog;
        }
        
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.okBtn){
                pressedOk();
            } else if(id == R.id.cancelBtn) {
                dialog.dismiss();
            } else {
                throw new IllegalStateException("invalid view id " + v.getId() + " in " + this.getClass().getSimpleName());
            }
        }

        private void pressedOk() {
            //getting fields
            EditText typeEdt = dialog.findViewById(R.id.typeEdit);
            EditText valueEdt = dialog.findViewById(R.id.valueEdt);
            EditText sizeEdt = dialog.findViewById(R.id.sizeEdt);
            //EditText defaultEdt = dialog.findViewById(R.id.defaultEdt);
            //EditText descriptionEdt = dialog.findViewById(R.id.descriptionEdt);

            /*
            TextView refTable = dialog.findViewById(R.id.refTable);
            TextView refField = dialog.findViewById(R.id.refField);
            TextView onUpdate = dialog.findViewById(R.id.onUpdate);
            TextView onDelete = dialog.findViewById(R.id.onDelete);
             */

            //data field prep
            int size = 0;
            try {
                size = Integer.parseInt(sizeEdt.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if(size < 0){
                Message.message(getContext(), "the size field cannot be less than 0");
                return;
            }

            if(valueEdt.getText().toString().isEmpty() ||
                    typeEdt.getText().toString().isEmpty()){
                Message.message(getContext(), "the type and value field can not be empty");
                return;
            }

            ApiController<SItemPojo> itemController = new SItemControllerImpl(container);

            //sending request
            switch (type) {
                case Create:
                    //data prep
                    data = SItemData.newInstance(
                            null,
                            valueEdt.getText().toString(),
                            typeEdt.getText().toString(),
                            size,
                            tableData);

                    /*
                        this cast does not fail because tableData.getItems() is bundled wildcard type which extends
                        SItemPojo which means it has to be instanced of the pojo or the pojo itself
                     */
                    tableData.addItem(data);
                    itemController.post(data);
                    break;
                case Edit:
                    //data prep
                    data = SItemData.newInstance(
                            data.getUuid(),
                            valueEdt.getText().toString(),
                            typeEdt.getText().toString(),
                            size,
                            tableData);
                    itemController.put(data);
                    break;
                default:
                    throw new IllegalStateException();
            }

            dialog.dismiss();
        }
    }
}