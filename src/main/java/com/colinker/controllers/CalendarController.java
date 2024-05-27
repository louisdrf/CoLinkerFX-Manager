package com.colinker.controllers;

import com.colinker.models.Task;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    @FXML
    private Button forwardOneWeekButton;
    @FXML
    private Button backOneWeekButton;
    @FXML
    private VBox calendarVBox;
    @FXML
    private Label currentWeekLabel;
    private LocalDate dateFocus;
    private LocalDate today;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        today = LocalDate.now();
        dateFocus = today;
        forwardOneWeekButton.setOnAction(event -> goForwardOneWeek());
        backOneWeekButton.setOnAction(event -> goBackOneWeek());
        updateCalendar();
    }

    private void updateCalendar() {
        calendarVBox.getChildren().clear();

        GridPane calendarGrid = new GridPane();
        calendarGrid.setGridLinesVisible(false);
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);

        double paneWidth = 950;
        double paneHeight = 420;

        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(paneWidth / 8);
            calendarGrid.getColumnConstraints().add(colConst);
        }

        for (int i = 0; i < 18; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(paneHeight / 18);
            calendarGrid.getRowConstraints().add(rowConst);
        }

        LocalDate startOfWeek = dateFocus.minusDays(dateFocus.getDayOfWeek().getValue() - 1);
        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            Label dayLabel = new Label(date.format(DateTimeFormatter.ofPattern("EEEE d")));
            dayLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            GridPane.setValignment(dayLabel, VPos.CENTER);
            calendarGrid.add(dayLabel, i + 1, 0);
        }

        for (int hour = 6; hour <= 22; hour++) {
            Label hourLabel = new Label(String.format("%02d:00", hour));
            hourLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            GridPane.setHalignment(hourLabel, HPos.CENTER);
            GridPane.setValignment(hourLabel, VPos.CENTER);
            calendarGrid.add(hourLabel, 0, hour - 5);
        }

        for (int hour = 6; hour <= 21; hour++) {
            Line line = new Line(0, 0, paneWidth, 0);
            line.setStyle("-fx-stroke: gray; -fx-stroke-width: 0.5;");
            calendarGrid.add(line, 1, hour - 5, 7, 1);
        }

        calendarVBox.getChildren().add(calendarGrid);

        String weekRange = startOfWeek.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + " - " +
                startOfWeek.plusDays(6).format(DateTimeFormatter.ofPattern("d MMM yyyy"));
        currentWeekLabel.setText(weekRange);
    }

    private void goForwardOneWeek() {
        dateFocus = dateFocus.plusWeeks(1);
        updateCalendar();
    }

    private void goBackOneWeek() {
        dateFocus = dateFocus.minusWeeks(1);
        updateCalendar();
    }

    private List<Task> getTodoTasksByPeriod(LocalDate start, LocalDate end) {
        return RemoteTaskRouter.getTodoTasksByPeriod(start, end);
    }
}
