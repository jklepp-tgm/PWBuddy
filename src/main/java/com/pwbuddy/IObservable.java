package com.pwbuddy;

/**
 * @author Jakob Klepp
 * @since 2013-06-01
 */
public interface IObservable<S>{
    /**
     * Einen Observer hinzufügen
     * @param observer welcher hinzugefügt werden soll
     */
    public void addPBObserver(IObserver observer);

    /**
     * Einen Observer entfernen
     * @param observer welcher entfernt werden soll
     */
    public void removePBObserver(IObserver observer);

    /**
     * Alle Observer über ein Ereignis benachrichtigen
     * @param eventType
     */
    public void notifyPBObservers(IObserver.PBEventType eventType);
}
