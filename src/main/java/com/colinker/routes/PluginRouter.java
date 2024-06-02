package com.colinker.routes;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PluginRouter {
    String defaultRoute = "http://localhost:8000/plugins";

    public void downloadPlugin(String fileName) {
        try {
            HttpResponse<byte[]> response = Unirest.get(this.defaultRoute + "/download").asBytes();

            if (response.isSuccess()) {
                byte[] fileBytes = response.getBody();
                File outputFile = new File("src/main/java/com.colinker/plugins/" + fileName);
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
