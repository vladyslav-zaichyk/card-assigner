package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.Event;
import com.rosklyar.cards.util.EventListener;

public interface EventService {
    void subscribe(Event.Type eventType, EventListener listener);

    void notify(Event event);
}
