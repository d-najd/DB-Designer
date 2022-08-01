package com.umldesigner.infrastructure.uml.entities;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.pojos.BasePojo;

/**
 * Uml Object that has only the most basic of functionality, this is android specific and should
 * not be added to the shared directory
 */
public interface BaseSObject extends BaseDestroyable {
    float getX();
    float getY();
    int getWidth();
    int getHeight();
    
    /**
     * method used for setting up the data that is specific to the uml object, the method makes sure
     * that all the correct interfaces and classes are implemented
     *
     * @param data the data being passed
     *
     * also this makes my brain hurt
     */
    <T extends BasePojo & BaseDataInterface> void setData(T data);
    
    /**
     * method used for updating the data object which every uml object should have
     */
     void updateData();
     
     BaseDataInterface getData();
}
