package com.rosklyar.cards.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.rosklyar.cards.domain.Event;
import com.rosklyar.cards.util.EventListener;

public class ConcurrentEventservice implements EventService {
    private final Multimap<Event.Type, EventListener> listeners = ArrayListMultimap.create();

    @Override
    public void subscribe(Event.Type eventType, EventListener listener) {
        synchronized (listeners) {
            listeners.put(eventType, listener);
        }
    }

    @Override
    public void notify(Event event) {
        synchronized (listeners) {
            listeners.get(event.type).forEach(listener -> listener.update(event));
        }
    }
}
