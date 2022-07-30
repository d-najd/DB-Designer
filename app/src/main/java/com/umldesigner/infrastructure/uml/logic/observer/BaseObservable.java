package com.umldesigner.infrastructure.uml.logic.observer;

public interface BaseObservable {
    public void registerObserver(BaseObserver o);
    public void removeObserver(BaseObserver o);
    public void notifyObservers();
}
