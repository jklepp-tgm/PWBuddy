package com.pwbuddy;

/**
 * @author Jakob Klepp
 * @since 2013-06-01
 */
public interface IPBObserver <S extends IPBObservable<S>>{
    /**
     * Wird vom observierten Subjekt aufgerufen
     *
     * @param subject observierten Subjekt welchen etwas zugestoßen ist
     * @param eventType was dem observierten Subjekt zugestßen ist
     */
    public void eventOcurred(S subject, PBEventType eventType);

    /**
     * Alles mögliche was einen DataSet so zustoßen kann
     */
    public enum PBEventType {
        /** DataSet wurde neu erstellt */
        CREATED,
        /** DataSet wurde erstellt */
        CHANGED,
        /** DataSet wurde gelöscht */
        DELETED
    }
}
