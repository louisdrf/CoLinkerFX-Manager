package com.colinker.events;

import javafx.event.Event;
import javafx.event.EventType;
import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private static final List<EventHandlerWrapper> handlers = new ArrayList<>();

    public static void addEventHandler(EventType<? extends Event> eventType, EventHandlerWrapper handler) {
        handlers.add(handler);
    }

    public static void fireEvent(Event event) {
        for (EventHandlerWrapper handler : handlers) {
            handler.handleEvent(event);
        }
    }

    public interface EventHandlerWrapper {
        void handleEvent(Event event);
    }
}
