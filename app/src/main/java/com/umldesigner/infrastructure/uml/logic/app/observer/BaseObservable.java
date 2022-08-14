package com.umldesigner.infrastructure.uml.logic.app.observer;

public interface BaseObservable {
    public void registerObserver(BaseObserver o);
    public void removeObserver(BaseObserver o);
    public void notifyObservers();
}
