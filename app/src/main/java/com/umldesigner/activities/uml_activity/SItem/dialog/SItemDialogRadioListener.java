package com.umldesigner.activities.uml_activity.SItem.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.infrastructure.uml.error.ErrorTags;

/**
 * listeners for the "radioGroup" in the type field inside the SItemDialog.
 *
 * @implNote new instance has to be created for each dialog or enabledButtons has to be reset because it holds which
 * button has been enabled and which disabled since android provides to way to turn on single radio button once it has
 * been pressed
 */
public class SItemDialogRadioListener implements View.OnClickListener {
    private final SItemDialog dialog;

    public SItemDialogRadioListener(SItemDialog dialog){
        this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
        Log.d("Debug", "onClick: " + view.toString());

        CompoundButton curBtn;
        Context c = view.getContext();

        try {
            curBtn = (CompoundButton) view;
        } catch (Exception e){
            Log.e(ErrorTags.APP_ERROR, this.getClass().getSimpleName() + "only accepts compoundButton or extension " +
                    "of it for listeners");
            Message.defErrMessage(c);
            e.printStackTrace();
            return;
        }

        //turnOnOffBtn(curBtn);

        int id = view.getId();

        if(id == R.id.PK){
            CompoundButton an = dialog.findViewById(R.id.AN);

            if (curBtn.isChecked()){
                an.setChecked(false);
                an.setEnabled(false);
            } else {
                an.setEnabled(true);
            }
        } else if(
                id == R.id.AN ||
                id == R.id.UQ ||
                id == R.id.AI ||
                id == R.id.FK){ } else {
            Message.defErrMessage(c);
            Log.e(ErrorTags.APP_ERROR, "Invalid pressed view id");
        }
    }
}
