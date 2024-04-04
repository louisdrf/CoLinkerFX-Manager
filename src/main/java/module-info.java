module com.colinker.colinkerjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.colinker to javafx.fxml;
    exports com.colinker;
    exports com.colinker.calendar;
    opens com.colinker.calendar to javafx.fxml;
}