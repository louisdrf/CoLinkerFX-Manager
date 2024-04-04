package com.colinker.calendar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    void updateCalendar() {
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void backOneMonth() {
        dateFocus = dateFocus.minusMonths(1);
        updateCalendar();
    }
    @FXML
    void forwardOneMonth() {
        dateFocus = dateFocus.plusMonths(1);
        updateCalendar();
    }

    private void drawCalendar() {
        int daysInWeek = 7;
        int calendarRows = 6;

        updateYearAndMonthLabels();

        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesForCurrentMonth();

        int maxDaysInMonth = getMaxDaysInMonth();
        int firstDayOfMonthOffset = getFirstDayOfMonthOffset();

        for (int row = 0; row < calendarRows; row++) {
            for (int column = 0; column < daysInWeek; column++) {

                StackPane stackPane = new StackPane();
                CalendarCell cell = new CalendarCell(calendar);

                stackPane.getChildren().add(cell.container);

                int currentCellIndexInCalendar = (column + 1) + (daysInWeek * row);

                if (currentCellIndexInCalendar > firstDayOfMonthOffset) {
                    int currentDate = currentCellIndexInCalendar - firstDayOfMonthOffset;
                    if (currentDate <= maxDaysInMonth) {
                        addDateTextToStackPane(currentDate, cell.height, stackPane);
                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, cell.height, cell.width, stackPane);
                        }
                    }
                    highlightToday(cell, currentDate);
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }


    private void updateYearAndMonthLabels() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesForCurrentMonth() {
        return getCalendarActivitiesMonth(dateFocus);
    }

    private int getMaxDaysInMonth() {
        int monthMaxDate = dateFocus.getMonth().maxLength();
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        return monthMaxDate;
    }

    private int getFirstDayOfMonthOffset() {
        return ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone())
                .getDayOfWeek().getValue();
    }

    private void addDateTextToStackPane(int currentDate, double cellHeight, StackPane stackPane) {
        Text date = new Text(String.valueOf(currentDate));
        double textTranslationY = - (cellHeight / 2) * 0.75;
        date.setTranslateY(textTranslationY);
        stackPane.getChildren().add(date);
    }

    private void highlightToday(CalendarCell cell, int currentDate) {
        if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
            cell.container.setStroke(Color.BLUE);
        }
    }



    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int i = 0; i < calendarActivities.size(); i++) {
            if(i >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(i).clientName + ", " + calendarActivities.get(i).date.toLocalTime());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity: calendarActivities) {

            int activityDate = activity.date.getDayOfMonth();

            if(!calendarActivityMap.containsKey(activityDate)) {
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);
                List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }

        return  calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            ZonedDateTime time = ZonedDateTime.of(year, month, random.nextInt(27)+1, random.nextInt(23)+1,0,0,0,dateFocus.getZone());
            calendarActivities.add(new CalendarActivity(time, "Hans", 111111));
        }

        return createCalendarMap(calendarActivities);
    }
}