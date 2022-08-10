package com.umldesigner.activities.uml_activity.dialogs.table.edit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.adapters.EditSTableAdapter;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.data.STable.STableData;

import java.util.List;

/**
 * the dialog used that pops up to when you create new table
 */
public class EditSTableDialog extends Dialog {
    STableData sTableData;
    
    public EditSTableDialog(Context context, STableData sTableData) {
        super(context);
        this.sTableData = sTableData;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_s_table_edit);
        
        Button okBtn = findViewById(R.id.okBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        
        DialogListeners listeners = new DialogListeners(this);
        okBtn.setOnClickListener(listeners);
        cancelBtn.setOnClickListener(listeners);
    
        List<SItemData> itemDataArrayList = (List<SItemData>) sTableData.getItems();
        
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        EditSTableAdapter adapter = new EditSTableAdapter(itemDataArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    
    
    static class DialogListeners implements View.OnClickListener{
        Dialog dialog;
        
        public DialogListeners(Dialog dialog){
            this.dialog = dialog;
        }
        
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.okBtn:
                    Message.message(v.getContext(), "pressed ok");
                    dialog.dismiss();
                    break;
                case R.id.cancelBtn:
                    Message.message(v.getContext(), "pressed cancel");
                    dialog.dismiss();
                    break;
                default:
                    throw new IllegalStateException("invalid view id " + v.getId() + " in " + this.getClass().getSimpleName());
            }
        }
    }
}
