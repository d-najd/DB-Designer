package com.umldesigner.activities.uml_activity.STable;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.SItem.SItemData;
import com.umldesigner.activities.uml_activity.SItem.SItemGridAdapter;
import com.umldesigner.activities.uml_activity.SItem.dialog.SItemDialog;
import com.umldesigner.infrastructure.uml.logic.api.controller.AbstractApiController;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.utils.DialogType;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import lombok.Getter;

import java.util.List;

/**
 * the dialog used that pops up to when you create new table
 * <p>
 * TODO make the new tables generate in the center of the screen
 */
public class STableDialog extends Dialog {
    Context context;
    @Getter
    STableData data;
    DialogType type;
    ViewGroup container;

    public STableDialog(ViewGroup container, STableData data, DialogType type) {
        super(container.getContext());
        this.context = container.getContext();
        this.container = container;
        this.data = data != null ? data : STableData.newEmptyInstance();
        this.type = type;

        if(data == null) {
            SUtils.getInstance().addTable(this.data.getId(), this.data);
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_s_table);

        //setting table title
        TextView title = findViewById(R.id.title);
        switch (type) {
            case Create:
                title.setText(R.string.createTable);
                break;
            case Edit:
                title.setText(R.string.editTable);
                break;
            default:
                throw new IllegalArgumentException("define valid type, " + type + " is not valid");
        }

        //getting fields
        TextView okBtn = findViewById(R.id.okBtn);
        TextView cancelBtn = findViewById(R.id.cancelBtn);
        TextView textView = findViewById(R.id.addColumnTxt);

        //setting listeners
        DialogListeners listeners = new DialogListeners(this);
        okBtn.setOnClickListener(listeners);
        cancelBtn.setOnClickListener(listeners);
        textView.setOnClickListener(listeners);

        itemsRecycler();
    }

    /**
     * creates recyclerview for the items of the table, located below the table title
     */
    private void itemsRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        /*

         */
        @SuppressWarnings("unchecked")
        SItemGridAdapter adapter = new SItemGridAdapter((List<SItemData>) data.getItems(), container);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void dismiss() {
        if(type == DialogType.Create) {
            SUtils.getInstance().removeById(data.getId());
        }

        super.dismiss();
    }

    private class DialogListeners implements View.OnClickListener {
        private final STableDialog dialog;
        public DialogListeners(STableDialog dialog) {
            this.dialog = dialog;

            setTitleListener();
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.okBtn) {
                pressedOk();
            } else if (id == R.id.cancelBtn) {
                dialog.dismiss();
            } else if (id == R.id.addColumnTxt){
                pressedColumnTxt();
            } else {
                throw new IllegalStateException("invalid view id " + v.getId() + " in " + this.getClass().getSimpleName());
            }
        }

        /**
         * TODO finish this
         */
        private void setTitleListener(){
            TextView editText = dialog.findViewById(R.id.titleEdt);
            editText.setOnKeyListener((view, i, keyEvent) -> {
                data.setTitle(editText.getText().toString());
                return false;
            });
        }

        private void pressedColumnTxt() {
            SItemDialog itemDialog = new SItemDialog(container, null, data, DialogType.Create);

            // recreate the adapter because item may have been added, not the most optimal way of doing things but oh
            // well
            itemDialog.setOnDismissListener(dialogInterface -> dialog.itemsRecycler());

            itemDialog.show();
        }

        private void pressedOk() {
            switch (type) {
                case Create:
                    EditText editText = dialog.findViewById(R.id.titleEdt);
                    String newTitle = editText.getText().toString();

                    if (newTitle.isEmpty()) {
                        Message.message(dialog.getContext(), "Please define title");
                    } else {
                        //recreating the table because "empty table" was used

                        @SuppressWarnings("unchecked")
                        STablePojo tableData = STableData.newInstance(null, data.getUuid(),
                                8f, 12f, newTitle, (List<SItemData>) data.getItems());
                        AbstractApiController<STablePojo> tableController = new STableControllerImpl(container);
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
                        //recreating the table because "empty table" was used
                        @SuppressWarnings("unchecked")
                        STablePojo tableData = STableData.newInstance(null, data.getUuid(),
                                data.getX(), data.getY(), newTitle, (List<SItemData>) data.getItems());
                        ApiController<STablePojo> tableController = new STableControllerImpl(container);
                        tableController.put(tableData);
                        dialog.dismiss();
                    }
            }
        }
    }
}