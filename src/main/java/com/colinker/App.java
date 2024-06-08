package com.colinker;

import com.colinker.helpers.SceneRouter;
import com.colinker.models.User;
import com.colinker.services.StatusConnectionService;
import io.github.cdimascio.dotenv.Dotenv;
import com.colinker.plugins.PluginLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "com.colinker")
public class App extends Application {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String baseUrl = dotenv.get("ExterneApi_URL");
    private ConfigurableApplicationContext springContext;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void init() throws Exception {
        scheduleOnlineCheck();
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
            PluginLoader pluginLoader = new PluginLoader();
            Platform.runLater(() -> {
                try {
                    if (User.isOnline) SceneRouter.showLoginPage();
                    else SceneRouter.showTasksListPage();
                    pluginLoader.firePlugins();
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
        String dockerComposeUpCommand = "docker compose -f " + pathToDockerCompose + " up -d";
        executeCommand(dockerComposeUpCommand);

        launch(args);
    }

    private void scheduleOnlineCheck() {
        StatusConnectionService service = new StatusConnectionService();
        scheduler.scheduleAtFixedRate(service::saveOnlineStatus, 0, 1, TimeUnit.MINUTES);
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
        if (springContext != null) springContext.close();

        try {
            String pathToDockerCompose = Paths.get(System.getProperty("user.dir"), "docker-compose.yml").toString();
            String dockerComposeDownCommand = "docker compose -f " + pathToDockerCompose + " down";
            executeCommand(dockerComposeDownCommand);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'arrêt du conteneur Docker : " + e.getMessage());
        }
    }
}
