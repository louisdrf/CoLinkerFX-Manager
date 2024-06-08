package com.colinker;

import com.colinker.config.RemoteDatabaseConnection;
import com.colinker.controllers.UserController;
import com.colinker.helpers.SceneRouter;
import com.colinker.models.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@SpringBootApplication
public class App extends Application {
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
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
            //LocalDatabase.close();
            System.out.println("Database local connexion well closed.");
        });
        stage.show();

        new Thread(() -> {
           /*if (!RemoteDatabaseConnection.tryConnection()) {
                System.out.println("Aucune connexion internet, connexion en local...");
                User.isOnline = false;
                User.setUsernameLocal();
                MongoDBImporter.importInLocalDatabase();
            }*/

            Platform.runLater(() -> {
                try {
                    addUser();
                    if (User.isOnline) SceneRouter.showLoginPage();
                    else SceneRouter.showTasksListPage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
        /*SceneRouter.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/colinker/calendar/calendar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("CoLinker");
        stage.setScene(scene);
        stage.show();
*/
    }

    private void addUser() {
        // Récupérer le contrôleur Spring de l'utilisateur et ajouter un utilisateur
        UserController userController = springContext.getBean(UserController.class);
        User newUser = new User();
        newUser.setUsername("ben");
        newUser.setPassword("benben");

        userController.createUser(newUser);
        System.out.println("User added successfully");
    }

    public static void main(String[] args) throws Exception {
        //setup database
        try {
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
        }



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
        if (springContext != null) {
            springContext.close();
        }
    }
}
