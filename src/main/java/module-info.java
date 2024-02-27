module com.example.pi_salma.controllers {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires java.activation;
    requires javax.mail;
requires jakarta.activation;
    opens com.example.pi_salma.models to javafx.base;
    opens com.example.pi_salma.controllers  to javafx.fxml;
    requires itextpdf;
    requires twilio;
    requires java.desktop;

    exports com.example.pi_salma.controllers;
    exports com.example.pi_salma.test;
    opens com.example.pi_salma.test to javafx.fxml;
}