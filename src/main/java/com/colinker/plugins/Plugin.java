package com.colinker.plugins;

import java.util.List;

class PluginClass {
    String className;
    String packageName;
}

public class Plugin {
    private String name;
    private List<PluginClass> classes;

    public String getName() {
        return this.name;
    }

    public List<PluginClass> getClasses() {
        return this.classes;
    }
}
