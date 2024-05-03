package com.colinker;

import com.colinker.controllers.UserController;
import com.colinker.helpers.SceneRouter;
import com.colinker.models.User;
import javafx.application.Application;
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

        stage.setTitle("CoLinker");
        SceneRouter.stage = stage;
        SceneRouter.showCalendarPage();

        stage.show();

        //addUser
        UserController userController = springContext.getBean(UserController.class);
        User newUser = new User(); // Assurez-vous que la classe User a des setters appropriés
        newUser.setUsername("newUser");
        newUser.setPassword("password123");

        // Appel de la méthode du contrôleur pour créer l'utilisateur
        userController.createUser(newUser);
    }

    public static void main(String[] args) throws Exception {
        //setup database
        /*try {
            // Commande Docker pour charger l'image MongoDB à partir du fichier tar.gz
            String loadCommand = "docker load -i /mongo.tar";
            executeCommand(loadCommand);

            // Commande Docker pour démarrer MongoDB
            String runCommand = "docker run --name my-mongodb -d -p 27017:27017 mongo";
            executeCommand(runCommand);

            // Attente pour laisser le temps à MongoDB de démarrer
            Thread.sleep(3500);

            // Commande pour créer la base de données et l'utilisateur
            String initCommand = "docker exec mongo-container mongo admin /docker-entrypoint-initdb.d/entrypoint.js";
            executeCommand(initCommand);


            System.out.println("MongoDB initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }*/



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