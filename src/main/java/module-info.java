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
    requires java.desktop;
    requires unirest.java;
    requires de.flapdoodle.embed.mongo;

    opens com.colinker to javafx.fxml;
    exports com.colinker;
    exports com.colinker.models;
    opens com.colinker.models to javafx.fxml;
    exports com.colinker.controllers;
    opens com.colinker.controllers to javafx.fxml;
    exports com.colinker.repositories;
    opens com.colinker.repositories to javafx.fxml;
    exports com.colinker.services;
    opens com.colinker.services to javafx.fxml;
}