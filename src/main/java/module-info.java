module com.example.espaceclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;
    requires org.slf4j;
    requires rest.api.sdk;
    requires java.desktop;
    requires jdk.httpserver;

    opens com.example.espaceclient.controller to javafx.fxml;
    opens com.example.espaceclient to javafx.fxml;
    opens com.example.espaceclient.model to javafx.base;
    exports com.example.espaceclient;
}