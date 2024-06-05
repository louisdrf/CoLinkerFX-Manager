package com.colinker;

import com.colinker.helpers.SceneRouter;
import com.colinker.models.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@SpringBootApplication(scanBasePackages = "com.colinker")
public class App extends Application {
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        springContext = new SpringApplicationBuilder(App.class).run(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        springContext.getAutowireCapableBeanFactory().autowireBean(this);

        stage.setTitle("CoLinker");
        SceneRouter.stage = stage;
        SceneRouter.showLoadingScreen();
        stage.setOnCloseRequest(event -> {
            // kill le conteneur
            System.out.println("Database local connexion well closed.");
        });
        stage.show();

        new Thread(() -> {
            User.isOnline = false;

            Platform.runLater(() -> {
                try {
                    if (User.isOnline) SceneRouter.showLoginPage();
                    else SceneRouter.showTasksListPage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
    }

    public static void main(String[] args) throws Exception {
        try {
            String loadCommand = "docker load -i /mongo.tar";
            executeCommand(loadCommand);
            String initCommand = "docker exec mongo-container mongo admin /docker-entrypoint-initdb.d/entrypoint.js";
            executeCommand(initCommand);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String loadCommand = "docker load -i /mongo.tar";
        executeCommand(loadCommand);

        String pathToDockerCompose = Paths.get(System.getProperty("user.dir"), "docker-compose.yml").toString();
        String dockerComposeUpCommand = "docker-compose -f " + pathToDockerCompose + " up -d";
        executeCommand(dockerComposeUpCommand);

        launch(args);
    }


    private static void executeCommand(String command) throws Exception {
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }
    }
}
