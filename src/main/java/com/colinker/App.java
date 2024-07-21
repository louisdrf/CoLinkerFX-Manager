package com.colinker;

import com.colinker.helpers.SceneRouter;
import com.colinker.models.User;
import com.colinker.routing.localrouter.controllers.LocalUserRouter;
import com.colinker.services.MongoDataTransferService;
import com.colinker.services.StatusConnectionService;
import com.colinker.services.UserPropertiesService;
import com.colinker.views.ApiResponseModal;
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

import static com.colinker.services.StatusConnectionService.isOnline;

@SpringBootApplication(scanBasePackages = "com.colinker")
public class App extends Application {
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
            killDockerContainer();
            UserPropertiesService.cleanProperties();
        });
        stage.show();

        new Thread(() -> {
            //PluginLoader.getInstance().loadPlugins();
            Platform.runLater(() -> {
                try {
                    if(UserPropertiesService.isUserOnline()) {
                        MongoDataTransferService.synchroniseDataInLocal();
                    }
                    SceneRouter.showLoginPage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
    }

    public static void main(String[] args) throws Exception {
        launchDockerContainer();
        launch(args);
    }

    private void scheduleOnlineCheck() {
        System.out.println("Initializing scheduler...");
        StatusConnectionService service = new StatusConnectionService();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Executing saveOnlineStatus");
            try {
                service.saveOnlineStatus();
            } catch (Exception e) {
                System.out.println("Exception in saveOnlineStatus: " + e.getMessage());
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS); // Intervalle de 10 secondes
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
    }

    private void killDockerContainer() {
        try {
            String pathToDockerCompose = Paths.get(System.getProperty("user.dir"), "docker-compose.yml").toString();
            String dockerComposeDownCommand = "docker compose -f " + pathToDockerCompose + " down";
            executeCommand(dockerComposeDownCommand);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'arrêt du conteneur Docker : " + e.getMessage());
        }
    }

    private static void launchDockerContainer() throws Exception {
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
    }
}
