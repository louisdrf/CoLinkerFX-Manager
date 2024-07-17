package com.colinker.controllers;

import com.colinker.helpers.SceneRouter;
import com.colinker.models.Association;
import com.colinker.models.Room;
import com.colinker.models.User;
import com.colinker.routing.localrouter.controllers.LocalAuthRouter;
import com.colinker.routing.remoterouter.RemoteAuthRouter;
import com.colinker.routing.remoterouter.RemoteAssociationRouter;
import com.colinker.routing.remoterouter.RemoteTaskRoomRouter;
import com.colinker.services.UserPropertiesService;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;


public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField loginEmailField;
    @FXML
    private TextField loginPasswordField;

    public void redirectColinkerLink() {
        try {
            Dotenv dotenv = Dotenv.load();
            String registerPage = dotenv.get("RegisterPageUrl");
            assert registerPage != null;
            Desktop.getDesktop().browse(new URI(registerPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login() throws IOException {
        String email = this.loginEmailField.getText();
        String password = this.loginPasswordField.getText();
        if (email.isEmpty() || password.isEmpty()) return;

        if (UserPropertiesService.isUserOnline()) {
            RemoteAuthRouter.login(email, password);
            String token = UserPropertiesService.getToken();
            if(!token.isEmpty()) {
                System.out.println("show tasks page");
                SceneRouter.showTasksListPage();
                User.associations = RemoteAssociationRouter.getUserAssociations(email);
            }
        }
        else {
            if(LocalAuthRouter.login(email, password)) SceneRouter.showTasksListPage();
        }
    }


}