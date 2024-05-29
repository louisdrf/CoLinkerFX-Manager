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
    requires com.google.gson;
    requires spring.data.commons;
    requires spring.data.mongodb;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires java.dotenv;
    requires org.slf4j;
    requires annotations;

    // Ouvrir tous les packages nécessaires à Spring
    opens com.colinker.config to spring.core, spring.beans, spring.context;
    opens com.colinker.controllers to spring.core, javafx.fxml;
    opens com.colinker.services to spring.core;
    opens com.colinker.models to spring.core, javafx.base;
    opens com.colinker to spring.core;



    exports com.colinker;
    exports com.colinker.models;
    exports com.colinker.controllers;
    exports com.colinker.services;
}
