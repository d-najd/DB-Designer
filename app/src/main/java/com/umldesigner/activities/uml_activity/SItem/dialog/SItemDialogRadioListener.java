package com.umldesigner.activities.uml_activity.SItem.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.infrastructure.uml.error.ErrorTags;

import java.lang.reflect.Array;
import java.util.HashMap;

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

    @SuppressLint("NonConstantResourceId")
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

        switch (view.getId()){
            case R.id.PK:
                CompoundButton an = dialog.findViewById(R.id.AN);

                if (curBtn.isChecked()){
                   an.setChecked(false);
                   an.setEnabled(false);
                } else {
                    an.setEnabled(true);
                }
                break;
            case R.id.AN:
            case R.id.UQ:
            case R.id.AI:
            case R.id.FK:
                break;
            default:
                Message.defErrMessage(c);
                break;
        }
    }

    /**
     * turns the radiobutton on if it was off and off if it was on
     * @param curBtn
     */
    private void turnOnOffBtn(RadioButton curBtn){
        curBtn.setChecked(!curBtn.isEnabled());
    }

}
