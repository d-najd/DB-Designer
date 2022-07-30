package com.umldesigner.activities.uml_activity.views.arrow;

import android.content.Context;
import android.view.View;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.pojos.BasePojo;

public class SFKBuilder extends View implements SObject {
    
    public SFKBuilder(Context context) {
        super(context);
    }
    
    @Override
    public void destroy() {
       throw new UnsupportedOperationException();
    }
    
    @Override
    public <T extends BasePojo & BaseDataInterface> void setData(T data) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void updateData() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void move(float x, float y) {
        throw new UnsupportedOperationException();
    }
}
