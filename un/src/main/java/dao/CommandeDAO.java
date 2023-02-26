package dao;

import model.Commande;
import model.Panier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeDAO extends DAO{
    public static CommandeDAO instance = new CommandeDAO();
    public Commande find(long id) {
        Commande commande = null;
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"SELECT * FROM commande WHERE commande_id = ?",false,id);
            ResultSet result = prepare.executeQuery();
            if(result.first())
                commande = new Commande(
                        result.getLong("commande_id"),
                        result.getLong("client_id"),
                        result.getString("date")
                );
            //TODO : gérer le cas où rien ne serait trouver
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }

    public Commande create(long client_id, Panier panier) {
        Commande commande = new Commande();
        try {
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            //TODO : vérifier que les paramètres sont corrects ,gérer le cas où rien ne serait créer
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"INSERT INTO commande (client_id,date) VALUES(?, ?)",
                    true,client_id,s.format(date));
            prepare.executeUpdate();
            ResultSet result = prepare.getGeneratedKeys();
            if (result.next()) {
                commande = this.find(result.getLong( 1 ));
                // Une fois la commande créer, lier les produits du panier à la commande
                CommandeProduitDAO.instance.create(commande.getId(),panier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }

    public void delete(long id) {
        try {
            // TODO : Supprimer tous les liens de produit en rapport avec la commande
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"DELETE FROM commande WHERE commande_id = ?",
                    false,id);
            prepare.executeUpdate();
            //TODO : verifier la suppression
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Commande> allCommandeOfClient(long client_id){
        List<Commande> resultat = new ArrayList<>();
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"SELECT * FROM commande WHERE client_id = ?",false,client_id);
            ResultSet result = prepare.executeQuery();
            if(result.first()) {
                do {
                    resultat.add(new Commande(
                            result.getLong("commande_id"),
                            result.getLong("client_id"),
                            result.getString("date")
                    ));
                } while (result.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultat;
    }
}
