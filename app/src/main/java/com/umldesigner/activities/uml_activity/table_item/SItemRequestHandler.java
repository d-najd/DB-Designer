package com.umldesigner.activities.uml_activity.table_item;


import com.umldesigner.infrastructure.uml.logic.api.AbstractRequestHandler;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

class SItemRequestHandler extends AbstractRequestHandler<SItemPojo> {
    private static SItemRequestHandler instance;
    public static SItemRequestHandler getInstance(){
        if (instance == null){
            instance = new SItemRequestHandler();
        }
        return instance;
    }
}
