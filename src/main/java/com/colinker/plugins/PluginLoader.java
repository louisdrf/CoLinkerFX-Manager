package com.colinker.plugins;

import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

class ByteClassLoader extends ClassLoader {

    public ByteClassLoader() {
        super(ByteClassLoader.class.getClassLoader());
    }
    public Class<?> defineByteClass(byte[] bytes, int off, int length) {
        return super.defineClass(bytes, off, length);
    }
}

public class PluginLoader {
    private static PluginLoader instance;
    List<Plugin> plugins;
    ByteClassLoader loader;

    private PluginLoader() {
        this.loader = new ByteClassLoader();
    }

    public static synchronized PluginLoader getInstance() {
        if (instance == null) {
            instance = new PluginLoader();
        }
        return instance;
    }

    public List<Plugin> loadPlugins() {
        Config config = loadConfig();

        if (config == null) return List.of();

        this.plugins = config.getPlugins();
        List<Plugin> loadedPlugins = this.plugins.stream()
                                            .filter(Plugin::isInstalled)
                                            .collect(Collectors.toList());

        for (Plugin plugin : loadedPlugins) {
            if (plugin.isInstalled()) {
                for (String file : plugin.getFiles()) {
                    File pluginFile = new File("com/colinker/plugins/" + plugin.getName() + "/" + file);
                    if (file.contains("Controller")) break;
                    Class<?> clazz = this.loadClass(pluginFile);
                    if (clazz == null) return List.of();
                }
            }
        }

        return loadedPlugins;
    }

    private Config loadConfig() {
        Gson gson = new Gson();
        InputStreamReader isr = new InputStreamReader(PluginLoader.class.getClassLoader().getResourceAsStream("plugins/config.json"));
        return gson.fromJson(isr, Config.class);
    }

    public Class<?> loadClass(File pluginFile) {
        try {
            FileInputStream fis = new FileInputStream(pluginFile);
            byte[] classBytes = fis.readAllBytes();
            return loader.defineByteClass(classBytes, 0, classBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Plugin> getPlugins() {
        return this.plugins;
    }
}