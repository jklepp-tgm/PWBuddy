package com.pwbuddy;

/**
 * @author Jakob Klepp
 * @since 2013-06-01
 */
public interface IPBObservable <S>{
    /**
     * Einen Observer hinzufügen
     * @param observer welcher hinzugefügt werden soll
     */
    public void addPBObserver(IPBObserver observer);

    /**
     * Einen Observer entfernen
     * @param observer welcher entfernt werden soll
     */
    public void removePBObserver(IPBObserver observer);

    /**
     * Alle Observer über ein Ereignis benachrichtigen
     * @param eventType
     */
    public void notifyPBObservers(IPBObserver.PBEventType eventType);
}
