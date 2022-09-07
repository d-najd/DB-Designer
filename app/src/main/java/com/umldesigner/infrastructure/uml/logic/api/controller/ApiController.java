package com.umldesigner.infrastructure.uml.logic.api.controller;

import android.view.ViewGroup;

public interface ApiController<T> {

    /**
     * the base endpoint, for ex /s/table
     * @return the base endpoint
     */
    String getEndpoint();

    /**
     * the container where the views need to be added
     * @return the container itself
     */
    ViewGroup getContainer();

    void getAll();

    void getByUuid(Object id);

    void post(T o);

    void put(T o);

}
