module com.colinker.colinkerjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    opens com.colinker to javafx.fxml;
    exports com.colinker;
    exports com.colinker.plugins.calendar;
    opens com.colinker.plugins.calendar to javafx.fxml;
    exports com.colinker.plugins.tasks;
    opens com.colinker.plugins.tasks to javafx.fxml;
    exports com.colinker.plugins.notes;
    opens com.colinker.plugins.notes to javafx.fxml;
    exports com.colinker.user;
    opens com.colinker.user to javafx.fxml;
}