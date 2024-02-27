module tn.esprit.gestionoeuvre {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    opens tn.esprit.gestionoeuvre.controller to javafx.fxml;
    exports tn.esprit.gestionoeuvre.entity;
    opens tn.esprit.gestionoeuvre to javafx.fxml;
    exports tn.esprit.gestionoeuvre;
}