package com.colinker.plugins;

import java.io.File;
import java.util.List;

public class Plugin {
    private String name;
    private List<String> files;

    public String getName() {
        return this.name;
    }

    public List<String> getFiles() {
        return this.files;
    }

    public boolean isInstalled() {
        File outputDir = new File("com/colinker/plugins/" + this.name);
        return outputDir.exists();
    }
}
