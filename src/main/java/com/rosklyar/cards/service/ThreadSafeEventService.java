package com.rosklyar.cards.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.rosklyar.cards.domain.Event;
import com.rosklyar.cards.util.EventListener;

import java.util.Arrays;

public class ThreadSafeEventService implements EventService {
    private final Multimap<Event.Type, EventListener> listeners = ArrayListMultimap.create();

    @Override
    public void subscribe(EventListener listener, Event.Type... eventTypes) {
        synchronized (listeners) {
            Arrays.stream(eventTypes).forEach(eventType -> listeners.put(eventType, listener));
        }
    }

    @Override
    public void notify(Event event) {
        synchronized (listeners) {
            listeners.get(event.type).forEach(listener -> listener.update(event));
        }
    }
}
