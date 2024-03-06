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
public class ListViewProduit extends ListCell<Oeuvre> {
    
    
     @Override
     public void updateItem(Oeuvre e, boolean empty)
    {
        super.updateItem(e,empty);
        if(e != null)
        {
            
            ProduitItemController data = new ProduitItemController();
            data.setInfo(e);
            setGraphic(data.getBox());
        }
    }
    
}
