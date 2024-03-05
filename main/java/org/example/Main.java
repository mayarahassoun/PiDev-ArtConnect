package org.example;

import esprit_pidev.entity.Event;
import esprit_pidev.entity.Reservations;
import esprit_pidev.services.EventServices;
import esprit_pidev.services.ResServices;
import esprit_pidev.util.MyDB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        MyDB mc= MyDB.getInstance();
        System.out.println(mc.hashCode());




        LocalDate datePublication = LocalDate.of(1999,10,4);
        LocalDateTime dateTimePublication = datePublication.atStartOfDay();
        Date date = Date.from(dateTimePublication.atZone(ZoneId.systemDefault()).toInstant());






      Event e = new Event(7,"zika","showTab", date,"white_outfit" ,"white_outfit","white_outfit");
      // Event e1 = new Event("amine","kbayer", date,"black_outfit" );
        EventServices es = new EventServices() ;
        //e.addEvent(e1) ;

        es.updateEvent(e);
      // Reservations r = new Reservations(7,7,"black_outfit" );
        es.getEvent();
        ResServices rs = new ResServices();
       // rs.addReservation(r);
    }
}