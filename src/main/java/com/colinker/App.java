package com.colinker;

import com.colinker.controllers.UserController;
import com.colinker.helpers.SceneRouter;
import com.colinker.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.builder.SpringApplicationBuilder;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

@SpringBootApplication
public class App extends Application {
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
        Dotenv dotenv = Dotenv.load();
        String[] args = getParameters().getRaw().toArray(new String[0]);
        springContext = new SpringApplicationBuilder(App.class).run(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Chargement du contexte Spring dans JavaFX
        springContext.getAutowireCapableBeanFactory().autowireBean(this);

        SceneRouter.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("calendar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("CoLinker");
        stage.setScene(scene);
        stage.show();

        // Ajout d'un utilisateur dans la base de données
        addUser();
    }

    private void addUser() {
        // Récupérer le contrôleur Spring de l'utilisateur et ajouter un utilisateur
        UserController userController = springContext.getBean(UserController.class);
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("password123");

        userController.createUser(newUser);
        System.out.println("User added successfully");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        if (springContext != null) {
            springContext.close();
        }
    }
}
