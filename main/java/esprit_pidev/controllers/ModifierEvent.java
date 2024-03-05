package esprit_pidev.controllers;
import esprit_pidev.entity.Event;
import esprit_pidev.services.EventServices;

import esprit_pidev.services.EventServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ModifierEvent {
    @FXML
    private DatePicker dateEvent;

    @FXML
    private TextField descEvent;

    @FXML
    private ImageView imageTF;

    @FXML
    private TextField localisationevent;

    @FXML
    private TextField nomEvent;

    @FXML
    private TextField typeEvent;

    @FXML
    void displayev(MouseEvent event) {

    }}

 /*   @FXML
    /*void modifierevent(ActionEvent event) {
        // Récupérer les données des champs de texte

        String name = nomEvent.getText();
        String type = typeEvent.getText();
        LocalDate date = dateEvent.getValue();
        String desc = descEvent.getText();
        String local = localisationevent.getText();

        // Créer un objet Event avec les données récupérées
        Event ev = new Event(id,name,type, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),desc,imagePath,local);
    /*            ev.setId(id);
                ev.setName(name);
                ev.setType(type);
                ev.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                ev.setDesc(desc);
                ev.setImagePath(imagePath);
                ev.setLocal(local); // */

       /* EventServices evs =new EventServices();
        evs.updateEvent(ev);

    }
/**/

