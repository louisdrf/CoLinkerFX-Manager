package com.colinker.services;

import javafx.beans.property.SimpleBooleanProperty;

public class UserOnlineStatusObservableService {

    private static SimpleBooleanProperty isUserOnlineProperty = new SimpleBooleanProperty(false);

    public static boolean isUserOnline() {
        return isUserOnlineProperty.get();
    }

    public static SimpleBooleanProperty isUserOnlineProperty() {
        return isUserOnlineProperty;
    }

    public static void setUserOnline(boolean isUserOnline) {
        isUserOnlineProperty.set(isUserOnline);
    }
}