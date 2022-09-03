package com.umldesigner.infrastructure.uml.logic.api.controller;

public interface ApiController<T> {
    void getAll();

    void getByUuid(Object id);

    void post(T o);

    void put(T o);

}
