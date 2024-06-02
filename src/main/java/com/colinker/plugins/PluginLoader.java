package com.colinker.plugins;

import com.colinker.events.EventBus;
import com.colinker.events.PluginLoadedEvent;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.stream.Collectors;

public class PluginLoader {
    String pluginsPath = "src/main/java/com/colinker/plugins/";

    List<Plugin> plugins;
    List<Plugin> loadedPlugins;

    public PluginLoader() {
        this.loadedPlugins = this.loadPlugins();
    }

    private List<Plugin> loadPlugins() {
        Config config = loadConfig();

        if (config == null) return List.of();

        this.plugins = config.getPlugins();
        List<Plugin> loadedPlugins = this.plugins.stream()
                                            .filter(Plugin::isInstalled)
                                            .collect(Collectors.toList());

        for (Plugin plugin : loadedPlugins) {
            if (plugin.isInstalled()) {
                for (PluginClass pluginClass : plugin.getClasses()) {
                    File pluginFile = new File(pluginClass.packageName + "/" + pluginClass.className + ".class");
                    URLClassLoader classLoader = this.createClassLoader(pluginFile);

                    if (classLoader == null) return List.of();

                    try {
                        Class<?> clazz = classLoader.loadClass(pluginClass.className);
                    } catch(ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return loadedPlugins;
    }

    private Config loadConfig() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(this.pluginsPath + "config.json"), Config.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private URLClassLoader createClassLoader(File jarFile) {
        try {
            URL[] urls = new URL[]{jarFile.toURI().toURL()};
            return new URLClassLoader(urls, getClass().getClassLoader());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void firePlugins() {
        PluginLoadedEvent pluginLoadedEvent = new PluginLoadedEvent(this.loadedPlugins);
        EventBus.fireEvent(pluginLoadedEvent);
    }

    public List<Plugin> getLoadedPlugins() {
        return this.loadedPlugins;
    }

    public List<Plugin> getPlugins() {
        return this.plugins;
    }
}