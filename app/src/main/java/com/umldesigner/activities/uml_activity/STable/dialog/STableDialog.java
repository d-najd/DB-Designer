package com.umldesigner.activities.uml_activity.STable.dialog;

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
import com.umldesigner.activities.uml_activity.SItem.dialog.SItemDialog;
import com.umldesigner.infrastructure.uml.utils.DialogType;
import com.umldesigner.activities.uml_activity.STable.controller.STableController;
import com.umldesigner.activities.uml_activity.STable.data.STableData;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

/**
 * the dialog used that pops up to when you create new table
 */
public class STableDialog extends Dialog {
    Context context;
    STablePojo data;
    DialogType type; //0 for creating
    ViewGroup container;

    public STableDialog(ViewGroup container, STablePojo data, DialogType type) {
        super(container.getContext());
        this.context = container.getContext();
        this.container = container;
        this.data = data;
        this.type = type;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_s_table);

        TextView title = findViewById(R.id.title);
        switch (type){
            case Create:
                title.setText("Create Table");
                break;
            case Edit:
                title.setText("Edit Table");
                break;
            default:
                throw new IllegalArgumentException("define valid type, " + type + " is not valid");
        }

        Button okBtn = findViewById(R.id.okBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        TextView textView = findViewById(R.id.addColumnTxt);

        DialogListeners listeners = new DialogListeners(this, data, type, container);
        okBtn.setOnClickListener(listeners);
        cancelBtn.setOnClickListener(listeners);
        textView.setOnClickListener(listeners);
    }
    
    
    private static class DialogListeners implements View.OnClickListener{
        private final Dialog dialog;
        private final STablePojo data;
        private final DialogType type;
        private final ViewGroup container;

        public DialogListeners(Dialog dialog, STablePojo data, DialogType type, ViewGroup container){
            this.dialog = dialog;
            this.data = data;
            this.type = type;
            this.container = container;
        }
        
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.okBtn:
                    pressedOk();
                    break;
                case R.id.cancelBtn:
                    dialog.dismiss();
                    break;
                case R.id.addColumnTxt:
                    pressedColumnTxt();
                    break;
                default:
                    throw new IllegalStateException("invalid view id " + v.getId() + " in " + this.getClass().getSimpleName());
            }
        }

        private void pressedColumnTxt(){
            SItemDialog dialog = new SItemDialog(container, null, DialogType.Create);
            dialog.show();
        }

        private void pressedOk(){
            switch (type) {
                case Create:
                    EditText editText = dialog.findViewById(R.id.titleEdt);
                    String newTitle = editText.getText().toString();

                    if (newTitle.isEmpty()) {
                        Message.message(dialog.getContext(), "Please define title");
                    }
                    else {
                        STablePojo tableData = new STableData(null, 8f, 12f, newTitle);
                        STableController tableController = new STableController(container);
                        tableController.post(tableData);
                        dialog.dismiss();
                    }
                    break;
                case Edit:
                    editText = dialog.findViewById(R.id.titleEdt);
                    newTitle = editText.getText().toString();

                    if (newTitle.isEmpty()) {
                        Message.message(dialog.getContext(), "Please define title");
                    } else {
                        STablePojo tableData = new STableData(null, 8f, 12f, newTitle);
                        STableController tableController = new STableController(container);
                        tableController.post(tableData);
                        dialog.dismiss();
                    }
            }
        }
    }
}