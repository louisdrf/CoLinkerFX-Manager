package com.colinker;

import com.colinker.controllers.UserController;
import com.colinker.helpers.SceneRouter;
import com.colinker.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class App extends Application {
    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
        Dotenv dotenv = Dotenv.load();
        // Configure et lance le contexte Spring directement ici
        String[] args = getParameters().getRaw().toArray(new String[0]);
        springContext = new SpringApplicationBuilder(App.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        SceneRouter.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("calendar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("CoLinker");
        stage.setScene(scene);

        SceneRouter.currentScene = scene;

        stage.show();

        //addUser
        UserController userController = springContext.getBean(UserController.class);
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("password123");

        userController.createUser(newUser);

    }

    public static void main(String[] args) throws Exception {
        String loadCommand = "docker load -i /mongo.tar";
        executeCommand(loadCommand);

        // Setup database using Docker Compose
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
    public void stop() throws Exception {
        springContext.close();
    }


}