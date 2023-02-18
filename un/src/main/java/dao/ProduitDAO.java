package dao;

import model.Produit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProduitDAO extends DAO<Produit>{
    @Override
    public Produit find(long id) {
        Produit produit = new Produit();
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"SELECT * FROM produit WHERE produit_id = ?",false,id);
            ResultSet result = prepare.executeQuery();
            if(result.first())
                produit = new Produit(
                        result.getLong("produit_id"),
                        result.getString("nom"),
                        result.getString("description"),
                        result.getDouble("prix"),
                        Produit.Categorie.valueOf(result.getString("categorie")),
                        Produit.SousCategorie.valueOf(result.getString("sous_categorie"))
                );
            //TODO : gérer le cas où rien ne serait trouver
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produit;
    }

    @Override
    public Produit create(Produit produit) {
        try {
        //TODO : vérifier que les paramètres sont corrects ,gérer le cas où rien ne serait créer
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"INSERT INTO produit (nom, description, prix, categorie, sous_categorie) VALUES(?,?,?,?,?)",
                    true,produit.getNom(),produit.getDescription(),produit.getPrix(),produit.getCategorie(),produit.getSousCategorie());
            prepare.executeUpdate();
            ResultSet result = prepare.getGeneratedKeys();
            if (result.next()) {
                produit = this.find(result.getLong( 1 ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produit;
    }

    @Override
    public Produit update(Produit produit) {
        try {
            //TODO : vérifier que tous les paramètres sont correctement remplis, et si l'update a été effectuer
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"UPDATE produit SET nom = ?, description = ?, prix = ?, categorie = ?, sous_categorie = ? WHERE produit_id = ?",
                    false,produit.getNom(),produit.getDescription(),produit.getPrix(),produit.getCategorie(),produit.getSousCategorie());
            prepare.executeUpdate();
            produit = this.find(produit.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produit;
    }

    @Override
    public void delete(Produit produit) {
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"DELETE FROM produit WHERE produit_id = ?",
                    false,produit.getId());
            prepare.executeUpdate();
            // TODO : verifier la suppression
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
