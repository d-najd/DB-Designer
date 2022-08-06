package com.umldesigner.infrastructure.uml.data.STable;

import android.util.Pair;

import com.umldesigner.activities.uml_activity.views.sfk.SFKView;

import java.util.HashMap;

import lombok.Getter;

public class STableDataBuffer {
    @Getter
    int count = 0;
    
    /**
     * the point of this buffer is because we aren't able to remove stuff from the table Iterator
     * while it is iterating we create a buffer which holds the old object (the first value) and the
     * new object with the updated data from the table (second value) and after the iterator is done
     * we start another iterator and since we are iterating the buffer not the specific object,
     * destroying that object wont affect the iterator in any way, destroying is done because removal
     * of specific lines on the canvas is impossible so I had to resort to recreating the view.
     *
     * this is written like this to make sure that nothing gets broken when the first view gets removed
     */
    @Getter
    private HashMap<Integer, Pair<SFKView, SFKView>> buffer = new HashMap<>();
    /**
     * adds value to the buffer
     * @param currentView the current view, one that needs to be removed
     * @param newView the new view, the view that needs to be added to the connections list
     */
    public void addValue(SFKView currentView, SFKView newView){
        buffer.put(count, new Pair<>(currentView, newView));
        count += 1;
    }
}
