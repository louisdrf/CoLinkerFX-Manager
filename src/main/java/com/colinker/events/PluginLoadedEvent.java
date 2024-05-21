package com.colinker.events;

import com.colinker.plugins.Plugin;
import javafx.event.Event;
import javafx.event.EventType;

import java.util.List;

public class PluginLoadedEvent extends Event {
    public static final EventType<PluginLoadedEvent> PLUGIN_LOADED = new EventType<>(Event.ANY, "PLUGIN_LOADED");

    private final List<Plugin> plugins;

    public PluginLoadedEvent(List<Plugin> plugins) {
        super(PLUGIN_LOADED);
        this.plugins = plugins;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }
}
