package com.rosklyar.cards.service;

import com.rosklyar.cards.domain.Event;
import com.rosklyar.cards.util.EventListener;

public interface EventService {
    void subscribe(EventListener listener, Event.Type... eventType);

    void notify(Event event);
}
