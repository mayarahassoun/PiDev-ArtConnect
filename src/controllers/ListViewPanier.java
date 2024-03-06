/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;



import javafx.scene.control.ListCell;
import models.Oeuvre;

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
