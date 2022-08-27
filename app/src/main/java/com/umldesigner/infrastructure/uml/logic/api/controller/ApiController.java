package com.umldesigner.infrastructure.uml.logic.api.controller;

public interface ApiController<T> {
    public void getAll();

    public void getByUuid(Object id);

    public void post(T o);

    public void put(T o);

}
