package com.colinker.calendar;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CalendarCell {

    Rectangle container = new Rectangle();
    private int strokeWidth = 1;
    double width;
    double height;


    public CalendarCell(FlowPane calendar) {

        this.container.setFill(Color.TRANSPARENT);
        this.container.setStroke(Color.BLACK);
        this.container.setStrokeWidth(strokeWidth);

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        this.width = (calendarWidth / 7) - strokeWidth - spacingH;
        this.container.setWidth(this.width);
        this.height = (calendarHeight/6) - strokeWidth - spacingV;
        this.container.setHeight( this.height);
    }
}
