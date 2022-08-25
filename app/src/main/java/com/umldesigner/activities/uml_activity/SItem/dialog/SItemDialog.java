package com.umldesigner.activities.uml_activity.SItem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.SItem.controller.SItemController;
import com.umldesigner.activities.uml_activity.SItem.data.SItemData;
import com.umldesigner.activities.uml_activity.STable.data.STableData;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;
import com.umldesigner.infrastructure.uml.utils.DialogType;
import com.umldesigner.infrastructure.uml.utils.SUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * the dialog used that pops up to when you create new table
 * @implNote mediator might be suitable here
 */
public class SItemDialog extends Dialog {
    private SItemData data;
    private final ViewGroup container;
    private final DialogType type;

    public SItemDialog(ViewGroup container, SItemData data, DialogType type) {
        super(container.getContext());
        this.data = data;
        this.container = container;
        this.type = type;
    }
    
    /**
     * @return returns list of available actions for the field (actions on the onDelete and OnUpdate
     * fields)
     * @implNote this may not be the best position to store this but I can't think of another place
     */
    public static List<String> getAllActions(Context context){
        List<String> actions = new ArrayList<>();
        
        actions.add(context.getString(R.string.no_action));
        actions.add(context.getString(R.string.restrict));
        actions.add(context.getString(R.string.cascade));
        actions.add(context.getString(R.string.set_null));
        actions.add(context.getString(R.string.set_default));
        
        return actions;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_s_item_edit);
    
        setupDialogListeners();
        setupFields();
    }
    
    /**
     * method for setting up the ok/cancel buttons
     */
    private void setupDialogListeners(){
        //getting the fields
        Button okBtn = findViewById(R.id.okBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        
        //prep
        DialogListeners listeners = new DialogListeners(this, data);
        
        //setting listeners
        okBtn.setOnClickListener(listeners);
        cancelBtn.setOnClickListener(listeners);
    }
    
    /**
     * method for setting up stuff for the fields inside the dialog, stuff like listeners.
     */
    private void setupFields(){
        EditSItemDialogListeners itemListener = new EditSItemDialogListeners(this);
        
        setupBasicFields(itemListener);
        setupFKFields(itemListener);
    }
    
    private void setupBasicFields(EditSItemDialogListeners itemListener){
        // getting the fields
        EditText typeEdt = findViewById(R.id.typeEdit);
        EditText valueEdt = findViewById(R.id.valueEdt);
        EditText sizeEdt = findViewById(R.id.sizeEdt);
        EditText defaultEdt = findViewById(R.id.defaultEdt);
        EditText descriptionEdt = findViewById(R.id.descriptionEdt);
      
        // preparing the data
        
        //y = re.findall("\(.*\)", txt1)
        //y = re.split("\(.*", txt1)

        if(data != null) {
            typeEdt.setText(data.getType());
            valueEdt.setText(data.getValue());
        }
    }
    
    
    /**
     * sets up fields which have stuff related to the foreign key
     * @param itemListener the listener for the buttons
     */
    private void setupFKFields(EditSItemDialogListeners itemListener){
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
            refTable.setText(getSelectedTableData().getTitle());
        }
    }
    
    /**
     * @return the data for the selected table in the refTable field, if unable to get
     * the field a table which {{@link #data}} belongs to will be returned instead, if that
     * fails as well null will be returned
     */
    public STableData getSelectedTableData(){
            TextView refTable = findViewById(R.id.refTable);
            Optional<STableData> optionalTable = SUtils.getInstance().getAllTables().parallelStream()
               .filter(o -> o.getTitle().equals(refTable.getText().toString())).findAny();
            
            if(!optionalTable.isPresent()){
                optionalTable = SUtils.getInstance().getAllTables().parallelStream()
                        .filter(o -> o.getItems().contains(data)).findAny();
    
                return optionalTable.orElse(null);
            }
            return optionalTable.get();
    }
    
    /**
     * notifies the refField that the table got changed and for it to update the text to a field of
     * that table
     */
    public void notifyTableChanged(){
        TextView refField = findViewById(R.id.refField);
        
        refField.setText(getSelectedTableData().getItems().get(0).getValue());
    }
    
    /**
     * listeners for the start and end buttons
     */
    private class DialogListeners implements View.OnClickListener{
        Dialog dialog;

        public DialogListeners(Dialog dialog, SItemData sItemData){
            this.dialog = dialog;
        }
        
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.okBtn:
                    pressedOk();
                    break;
                case R.id.cancelBtn:
                    Message.message(v.getContext(), "pressed cancel");
                    dialog.dismiss();
                    break;
                default:
                    throw new IllegalStateException("invalid view id " + v.getId() + " in " + this.getClass().getSimpleName());
            }
        }

        private void pressedOk() {
            switch (type) {
                case Create:
                    //getting fields
                    EditText typeEdt = dialog.findViewById(R.id.typeEdit);
                    EditText valueEdt = dialog.findViewById(R.id.valueEdt);
                    EditText sizeEdt = dialog.findViewById(R.id.sizeEdt);
                    EditText defaultEdt = dialog.findViewById(R.id.defaultEdt);
                    EditText descriptionEdt = dialog.findViewById(R.id.descriptionEdt);

                    TextView refTable = dialog.findViewById(R.id.refTable);
                    TextView refField = dialog.findViewById(R.id.refField);
                    TextView onUpdate = dialog.findViewById(R.id.onUpdate);
                    TextView onDelete = dialog.findViewById(R.id.onDelete);

                    //prep
                    int size = 0;
                    try {
                        size = Integer.parseInt(sizeEdt.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    //setting sItemData

                    data = new SItemData(valueEdt.getText().toString(), typeEdt.getText().toString(), size);
                    SItemController controller = new SItemController(container);
                    controller.post(data);
            }
        }
    }
}
