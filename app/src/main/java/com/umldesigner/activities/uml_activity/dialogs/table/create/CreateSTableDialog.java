package com.umldesigner.activities.uml_activity.dialogs.table.create;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.umldesigner.MainActivity;
import com.umldesigner.Message;
import com.umldesigner.R;

/**
 * the dialog used that pops up to when you create new table
 */
public class CreateSTableDialog extends Dialog {
    MainActivity mainActivity;
    
    public CreateSTableDialog(MainActivity mainActivity) {
        super(mainActivity);
        this.mainActivity = mainActivity;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_s_table_create);
    
        Button okBtn = findViewById(R.id.okBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        
        DialogListeners listeners = new DialogListeners(this);
        okBtn.setOnClickListener(listeners);
        cancelBtn.setOnClickListener(listeners);
    }
    
    
    static class DialogListeners implements View.OnClickListener{
        Dialog dialog;
        
        public DialogListeners(Dialog dialog){
            this.dialog = dialog;
        }
        
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.okBtn:
                    Message.message(v.getContext(), "pressed ok");
                    
                    EditText editText = dialog.findViewById(R.id.titleEdt);
                    String newTitle = editText.getText().toString();
                    
                    if(newTitle.isEmpty())
                        Message.message(v.getContext(), "Please define title");
                    else {
                        dialog.dismiss();
                    }
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