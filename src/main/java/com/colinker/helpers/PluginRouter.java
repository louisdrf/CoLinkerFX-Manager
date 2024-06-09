package com.colinker.helpers;

import io.github.cdimascio.dotenv.Dotenv;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PluginRouter {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String baseUrl = dotenv.get("ExterneApi_URL");
    String defaultRoute =  baseUrl + "/plugins";

    public void downloadPlugin(String pluginName, String fileName) {
        try {
            HttpResponse<byte[]> response = Unirest.get(this.defaultRoute + "/download/" + pluginName + "/" + fileName).asBytes();

            if (response.isSuccess()) {
                byte[] fileBytes = response.getBody();
                File outputDir = new File("com/colinker/plugins/" + pluginName);
                if (!outputDir.exists()) outputDir.mkdirs();
                File outputFile = new File(outputDir, fileName);
                FileOutputStream outputStream = new FileOutputStream(outputFile);

                outputStream.write(fileBytes);
                outputStream.close();

                System.out.println("File downloaded successfully: " + fileName);
            } else {
                System.out.println("Failed to download file: " + response.getStatusText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
