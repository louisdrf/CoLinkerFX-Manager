package com.colinker.controllers;

import com.colinker.routes.SceneRouter;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class NavbarController {


    public void showCalendarPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showCalendarPage();
    }

    public void showTasksListPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showTasksListPage();
    }
}
