package fr.uge.but.schtroumpf.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Conserve l'historique de tous les événements survenus pendant la partie.
 */
public class EventHistory {

    private final List<Event> history;

    public EventHistory() {
        this.history = new ArrayList<>();
    }

    /** Ajoute un événement à l'historique. */
    public void addEvent(Event event) {
        this.history.add(event);
    }

    /** Retourne une copie de l'historique complet (lecture seule). */
    public List<Event> getHistory() {
        return new ArrayList<>(this.history);
    }

    /** Retourne le dernier événement enregistré, ou null si vide. */
    public Event getLastEvent() {
        if (this.history.isEmpty()) {
            return null;
        }
        return this.history.get(this.history.size() - 1);
    }
}
