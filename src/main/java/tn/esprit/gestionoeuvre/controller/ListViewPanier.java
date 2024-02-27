/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionoeuvre.controller;



import javafx.scene.control.ListCell;
import tn.esprit.gestionoeuvre.entity.Oeuvre;

/**
 *
 * @author dell
 */
public class ListViewPanier extends ListCell<Oeuvre> {
    
    
     @Override
     public void updateItem(Oeuvre e, boolean empty)
    {
        super.updateItem(e,empty);
        if(e != null)
        {
            
            PaniertemController data = new PaniertemController();
            data.setInfo(e);
            setGraphic(data.getBox());
        }
    }
    
}
