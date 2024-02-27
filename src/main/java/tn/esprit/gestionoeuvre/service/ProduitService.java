package tn.esprit.gestionoeuvre.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.gestionoeuvre.entity.Categorie;
import tn.esprit.gestionoeuvre.entity.Oeuvre;
import tn.esprit.gestionoeuvre.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProduitService {


    private final Connection cnx;

    private static ProduitService instance;

    public ProduitService() {
        cnx = DataSource.getInstance().getCnx();
    }

    public static ProduitService getInstance()
    {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }

    public void addOeuvre(Oeuvre q)throws SQLDataException, SQLException {



        String query ="INSERT INTO `oeuvreart`(`nom`,`description`,`prix`,`quantite`,`image`,`id_cat`,`certif`) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement st;

        try {
            st = cnx.prepareStatement(query);
            st.setString(1,q.getNom());
            st.setString(2,q.getDescription());
            st.setDouble(3,q.getPrix());
            st.setInt(4,q.getQuantite());
            st.setString(5,q.getImage());
            st.setInt(6,q.getId_cat());
            st.setString(7,q.getCertif());
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void ModifierOeuvre(Oeuvre q) throws SQLDataException {


        String query = "UPDATE `oeuvreart` SET `nom`=?,`description`=?,`prix`=?,`quantite`=?,`image`=?,`id_cat`=? ,`certif`=? WHERE `idOeuvre` = ?";
        PreparedStatement st;
        try {
            st = cnx.prepareStatement(query);
            st.setString(1,q.getNom());
            st.setString(2,q.getDescription());
            st.setFloat(3,q.getPrix());
            st.setInt(4,q.getQuantite());
            st.setString(5,q.getImage());
            st.setInt(6,q.getId_cat());
            st.setString(7,q.getCertif());
            st.setInt(8,q.getIdOeuvre());
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public ObservableList<Oeuvre> getAllOeuvres() throws SQLDataException {


        List<Oeuvre> list =new ArrayList<Oeuvre>();
        int count =1;

        String requete="select * from oeuvreart";
        try{
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()){

                Oeuvre e = new Oeuvre();
                e.setNom(rs.getString("nom"));
                e.setDescription(rs.getString("description"));
                e.setPrix(rs.getFloat("prix"));
                e.setQuantite(rs.getInt("quantite"));
                e.setImage(rs.getString("image"));
                e.setId_cat(rs.getInt("id_cat"));
                e.setIdOeuvre(rs.getInt("idOeuvre"));
                e.setCertif(rs.getString("certif"));
                list.add(e);

                count++;
                System.out.printf("Error"+count);
            }
            if(count == 0){
                return null;
            }else{

                return FXCollections.observableArrayList(list);


            }
        }
        catch (SQLException ex) {

            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }

    public Oeuvre getOeuvreById(int i) {
        Oeuvre e = new Oeuvre();
        int nombre = 0;
        String requete = "select * from oeuvreart where idOeuvre="+i;
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                e.setNom(rs.getString("nom"));
                e.setDescription(rs.getString("description"));
                e.setPrix(rs.getFloat("prix"));
                e.setQuantite(rs.getInt("quantite"));
                e.setImage(rs.getString("image"));
                e.setId_cat(rs.getInt("id_cat"));
                e.setIdOeuvre(rs.getInt("idOeuvre"));
                e.setCertif(rs.getString("certif"));
                nombre++;

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return e;

    }





    public void deleteOeuvre(int idCat) throws SQLDataException {

        try {

            Statement st=cnx.createStatement();
            String req= "DELETE FROM oeuvreart WHERE idOeuvre="+idCat;
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
