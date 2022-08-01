package com.umldesigner.activities.uml_activity.views;

import android.util.Log;
import android.view.View;

import com.umldesigner.Message;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.infrastructure.uml.logic.SArrowParts;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.pojos.BasePojo;

/**
 * @deprecated
 */
public class SArrowPart extends View implements SObject {
    private final Integer id;
    private final SArrowParts type;
    private final SArrowViewOld parent;
    private final SSettingsSingleton umlSettingsInstance;
    
    public SArrowPart(SArrowViewOld parent, SArrowParts type) {
        super(parent.getContext());
        umlSettingsInstance = SSettingsSingleton.getInstance();
        this.id = umlSettingsInstance.getNextId();
        this.type = type;
        this.parent = parent;
        this.setId(id);
        umlSettingsInstance.allViewTagsPut(id, this);
        this.setMinimumWidth((int) parent.colliderSize);
        this.setMinimumHeight((int) parent.colliderSize);
        setOnTouchListener(parent.sListeners);

        parent.viewGroup.addView(this);

        double angle = SArrowViewOld.calculateAngle(parent.xStart, parent.yStart, parent.xEnd, parent.yEnd);

        switch (type){
            case ArrowHead:
                this.setElevation(SSettingsSingleton.ARROW_HEAD_ELEVATION);
                this.setX(parent.xEnd - parent.colliderSize/2);
                this.setY(parent.yEnd - parent.colliderSize/2);
                this.setRotation((float) (angle + 180));
                break;
            case ArrowBody:
                this.setElevation(SSettingsSingleton.ARROW_BODY_ELEVATION);
                this.setX(parent.xStart - parent.colliderSize/2);
                this.setY(parent.yStart - parent.colliderSize/2);
                this.setRotation((float) angle + 90);
                this.setPivotY(parent.colliderSize/2);
                this.setPivotX(parent.colliderSize/2);
                this.setMinimumHeight((int) ((Math.sqrt(Math.pow(parent.xStart - parent.xEnd, 2) +
                        Math.pow(parent.yStart - parent.yEnd, 2))) +  parent.colliderSize));
                break;
            case ArrowBack:
                this.setElevation(SSettingsSingleton.ARROW_BACK_ELEVATION);
                this.setX(parent.xStart - parent.colliderSize/2);
                this.setY(parent.yStart - parent.colliderSize/2);
                this.setRotation((float) (angle + 180));
                break;
            default:
                Message.defErrMessage(parent.getContext());
                Log.wtf("ERROR", "invalid tag entered in " + getClass().getSimpleName() + ": " + type);
        }
    }

    @Override
    public void move(float x, float y) {
        try {
            switch (type) {
                case ArrowHead:
                    parent.movePart(x, y, true);
                    break;
                case ArrowBody:
                    parent.move(x, y);
                    break;
                case ArrowBack:
                    parent.movePart(x, y, false);
                    break;
                default:
                    throw new IllegalAccessException("invalid arrow type entered " + type);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("need to implement the method..");
    }
    
    @Override
    public <T extends BasePojo & BaseDataInterface> void setData(T data) {
       throw new UnsupportedOperationException("need to implement the method...");
    }
    
    @Override
    public void updateData() {
        throw new UnsupportedOperationException("need to implement the method...");
    }
}