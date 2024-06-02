package com.colinker.plugins;

import java.util.List;

class PluginClass {
    String className;
    String packageName;
}

public class Plugin {
    private String name;
    private boolean installed;
    private String url;
    private List<PluginClass> classes;

    public String getName() {
        return this.name;
    }

    public List<PluginClass> getClasses() {
        return this.classes;
    }

    public boolean isInstalled() {
        return this.installed;
    }

    public String getUrl() {
        return this.url;
    }
}
