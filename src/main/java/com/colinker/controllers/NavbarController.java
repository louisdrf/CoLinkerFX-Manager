package com.colinker.controllers;

import com.colinker.helpers.SceneRouter;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class NavbarController {

    public void showCalendarPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showCalendarPage();
    }

    public void showTasksListPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showTasksListPage();
    }

    public void showUpdatePage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showUpdatePage();
    }

    public void showNotesPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showNotesPage();
    }

    public void showPluginsPage(MouseEvent mouseEvent) throws IOException {
        SceneRouter.showPluginsPage();
    }
}
