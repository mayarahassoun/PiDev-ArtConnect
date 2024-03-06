
package services;

import models.panier;
import utils.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Service {
    

        private final Connection connection;

    private static Service instance;
    
        public Service() {
            connection = (Connection) ConnectionUtil.conDB();
    }
    
    public static Service getInstance()
    {
        if (instance == null) {
            instance = new Service();
        }
        return instance; 
    }
    
    
    
    

    
    
        public panier get_PanierByProduit(int id) {
        panier e = new panier();
        String requete = "select * from panier where id_oeuvre="+id;
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                e.setId_panier(rs.getInt("id_panier"));
                e.setId_produit(rs.getInt("id_oeuvre"));
                e.setQuantité(rs.getInt("quantite"));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
            System.out.println("service.Service.get_PanierByProduit()"+e.toString());
        return e;

    }
      
      
          
   public void addPanier(panier p)throws SQLDataException, SQLException{
        
         
      String query ="INSERT INTO `panier`( `id_oeuvre`, `quantite`) VALUES (?,?)";
 
         PreparedStatement st;
        
        try {
            st = connection.prepareStatement(query);
                st.setInt(1,p.getId_produit());
                st.setInt(2,p.getQuantité());
                st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

    }




   
   
       public List<panier> getAllPanier() throws SQLDataException {

        
        List<panier> list =new ArrayList<panier>();
        int count =0;
        
        String requete="select * from panier";
         try{
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(requete);
            
            while (rs.next()){
                
                panier e = new panier();
                e.setId_panier(rs.getInt("id_panier"));
                e.setId_produit(rs.getInt("id_oeuvre"));
                e.setQuantité(rs.getInt("quantite"));
                
                list.add(e);
                count++;
            }
            if(count == 0){
                return list;
           }else{

               return list;
            
           
        }
         }
        catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   
           
       }
      
      
      
     public boolean deleteFromPanier(int idp) throws SQLDataException {

        
        
        try {
            
            Statement st=connection.createStatement();
            String req= "DELETE FROM `panier` WHERE `id_oeuvre`="+idp;
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
     
     
    public boolean deletePanier() throws SQLDataException {

        
        
        try {
            
            Statement st=connection.createStatement();
            String req= "TRUNCATE TABLE `panier`";
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
          
          
       public boolean ModifierQuantite(int id,int q) throws SQLDataException {
              String query = "UPDATE `panier` SET `quantite`=? WHERE `id_oeuvre` = ?";
		PreparedStatement st;
               try {
                st = connection.prepareStatement(query);
            
                st.setInt(1,q);
                st.setInt(2,id);
                st.executeUpdate();
                System.out.println("trueeeeeeeee");
                return true;
                
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
   

        

    
}
