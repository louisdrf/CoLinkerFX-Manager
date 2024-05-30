package com.colinker.controllers;

import com.colinker.models.Task;
import com.colinker.routing.remoterouter.RemoteTaskRouter;
import com.colinker.views.TaskInfosModal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

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

    int nbDaysInWeek = 7;
    int startHourInCalendar = 6;
    int maxHourInCalendar = 22;
    int calendarNumberOfRows = 18;
    int calendarNumberOfColumns = 8;
    double calendarWidth = 950;
    double calendarHeight = 420;
    int calendarRowHeight = (int) (calendarHeight / calendarNumberOfRows);
    int calendarColumnWidth = (int) (calendarWidth / calendarNumberOfColumns);

    LocalDate startOfWeek;
    LocalDate endOfWeek;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        today = LocalDate.now();
        dateFocus = today;
        forwardOneWeekButton.setOnAction(event -> goForwardOneWeek());
        backOneWeekButton.setOnAction(event -> goBackOneWeek());
        updateCalendar();
    }

    private void updateCalendarWeekLimitDates() {
        startOfWeek = dateFocus.minusDays(dateFocus.getDayOfWeek().getValue() - 1);
        endOfWeek = startOfWeek.plusDays(6);
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

    private void updateCalendar() {
        calendarVBox.getChildren().clear();

        GridPane calendarGrid = new GridPane();
        calendarGrid.setGridLinesVisible(false);
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);

        createColumns(calendarGrid);
        createRows(calendarGrid);

        updateCalendarWeekLimitDates();
        writeWeekRangeDates();

        writeDatesLabelsOnTop(calendarGrid);
        drawLinesFromDatesLabel(calendarGrid);

        writeHoursLabelsOnLeft(calendarGrid);
        drawLinesFromHoursLabel(calendarGrid);

        calendarVBox.getChildren().add(calendarGrid);

        List<Task> weeklyTaskList = getTodoTasksByPeriod(startOfWeek, endOfWeek);
        for(Task task : weeklyTaskList) {
            addTaskToCalendar(calendarGrid, task);
        }
    }

    private void createColumns(GridPane calendarGrid) {
        for (int i = 0; i < calendarNumberOfColumns; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(calendarColumnWidth);
            colConst.setPercentWidth(100.0 / calendarNumberOfColumns); // Set percent width for better alignment
            calendarGrid.getColumnConstraints().add(colConst);
        }
    }

    private void createRows(GridPane calendarGrid) {
        for (int i = 0; i < calendarNumberOfRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(calendarRowHeight);
            calendarGrid.getRowConstraints().add(rowConst);
        }
    }

    private void writeDatesLabelsOnTop(GridPane calendarGrid) {
        for (int day = 0; day < nbDaysInWeek; day++) {
            LocalDate date = startOfWeek.plusDays(day);
            Label dayLabel = new Label(date.format(DateTimeFormatter.ofPattern("EEEE d")));
            dayLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            centerElement(dayLabel);
            calendarGrid.add(dayLabel, day + 1, 0);
        }
    }

    private void writeHoursLabelsOnLeft(GridPane calendarGrid) {
        for (int hour = startHourInCalendar; hour <= maxHourInCalendar; hour++) {
            Label hourLabel = new Label(String.format("%02d:00", hour));
            hourLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            centerElement(hourLabel);
            calendarGrid.add(hourLabel, 0, hour - 5);
        }
    }

    private void drawLinesFromDatesLabel(GridPane calendarGrid) {
        for (int colIndex = 1; colIndex <= nbDaysInWeek + 1; colIndex++) {
            Line verticalLine = new Line(0, 0, 0, 500);
            verticalLine.setStyle("-fx-stroke: gray; -fx-stroke-width: 0.5;");
            calendarGrid.add(verticalLine, colIndex, 1, 1, calendarNumberOfRows - 1);
        }
    }

    private void drawLinesFromHoursLabel(GridPane calendarGrid) {
        double lineWidth = calendarWidth - 52;
        for (int rowIndex = startHourInCalendar; rowIndex <= maxHourInCalendar; rowIndex++) {
            Line line = new Line(0, 0, lineWidth, 0);
            line.setStyle("-fx-stroke: gray; -fx-stroke-width: 0.5;");
            calendarGrid.add(line, 1, rowIndex - 5, nbDaysInWeek, 1);
        }
    }


    private void writeWeekRangeDates() {
        String weekRange = startOfWeek.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + " - " +
                endOfWeek.format(DateTimeFormatter.ofPattern("d MMM yyyy"));
        currentWeekLabel.setText(weekRange);
    }

    private void centerElement(Node node) {
        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);
    }

    private void addTaskToCalendar(GridPane grid, Task task) {
        LocalDateTime startDateTime = task.dateDebut.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDateTime = task.dateFin.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();

        int dayOfWeek = startDateTime.getDayOfWeek().getValue();
        int startHour = startDateTime.getHour();
        int endHour = endDateTime.getHour();

        int rowSpan = endHour - startHour;

        VBox taskBox = new VBox();
        taskBox.setStyle("-fx-background-color: " + "#5356FF;" + "-fx-padding: 5px;");
        taskBox.setAlignment(Pos.CENTER);
        taskBox.setSpacing(5);
        taskBox.setPrefWidth(150);
        taskBox.setMaxHeight(Double.MAX_VALUE);

        Label taskTitleLabel = new Label(task.title);
        taskTitleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;" + "-fx-wrap-text: true;" + "-fx-text-alignment: center;");
        taskTitleLabel.setMaxWidth(Double.MAX_VALUE);
        taskTitleLabel.setMaxHeight(Double.MAX_VALUE);

        Button seeMoreButton = new Button("Voir plus");
        seeMoreButton.setStyle("-fx-font-size: 13px; -fx-background-color: #fff; -fx-text-fill: #5356FF;");
        seeMoreButton.setMaxWidth(Double.MAX_VALUE);
        seeMoreButton.setOnAction(event -> TaskInfosModal.showTaskDetails(task));

        String checkIconUnicode = "✓";
        Label doneIcon = new Label(checkIconUnicode);
        doneIcon.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        doneIcon.setOnMouseClicked(event -> {
            RemoteTaskRouter.updateTaskAsDone(task.id);
            updateCalendar();
        });

        VBox.setVgrow(taskTitleLabel, Priority.ALWAYS);
        VBox.setVgrow(seeMoreButton, Priority.NEVER);
        VBox.setVgrow(doneIcon, Priority.NEVER);

        taskBox.getChildren().addAll(taskTitleLabel, seeMoreButton, doneIcon);

        centerElement(taskBox);

        grid.add(taskBox, dayOfWeek, startHour - 5, 1, rowSpan);
    }
}
