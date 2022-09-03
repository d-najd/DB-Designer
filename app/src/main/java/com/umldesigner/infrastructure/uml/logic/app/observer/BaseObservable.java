package com.umldesigner.infrastructure.uml.logic.app.observer;

public interface BaseObservable {
    void registerObserver(BaseObserver o);
    void removeObserver(BaseObserver o);
    void notifyObservers();
}
