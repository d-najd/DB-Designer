package com.umldesigner.activities.uml_activity.listeners.item.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.dialogs.item.edit.spinners.EditSItemDialogActionsSpinner;
import com.umldesigner.activities.uml_activity.dialogs.item.edit.spinners.EditSItemDialogRTableSpinner;
import com.umldesigner.activities.uml_activity.views.table.STableView;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.infrastructure.uml.logic.ASettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EditSItemDialogListeners implements View.OnClickListener {
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Context c = view.getContext();
        switch (view.getId()){
            case R.id.refTable:
                new EditSItemDialogRTableSpinner(getAllTables(), (TextView)view);
                break;
            case R.id.refField:
                break;
            case R.id.onUpdate:
            case R.id.onDelete:
                new EditSItemDialogActionsSpinner(getAllActions(view.getContext()), (TextView)view);
                break;
            default:
                Message.defErrMessage(c);
                break;
        }
    }
    
    /**
     * @return returns list of available actions for the field (actions on the onDelete and OnUpdate
     * fields)
     */
    private List<String> getAllActions(Context context){
        List<String> actions = new ArrayList<>();
        
        actions.add(context.getString(R.string.no_action));
        actions.add(context.getString(R.string.restrict));
        actions.add(context.getString(R.string.cascade));
        actions.add(context.getString(R.string.set_null));
        actions.add(context.getString(R.string.set_default));
        
        return actions;
    }
    
    /**
     * @return list of the table titles
     */
    private List<String> getAllTables(){
        List<String> allTables = new ArrayList<>();
    
        Iterator<Map.Entry<Integer, SObject>> viewsIterator = ASettings.getInstance().getViewsIterator();
        while (viewsIterator.hasNext()){
            SObject curView = viewsIterator.next().getValue();
            if (curView instanceof STableView){
                allTables.add(((STableView) curView).getData().getTitle());
            }
        }
        
        return allTables;
    }
}
