package dao;

import model.Commande;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandeDAO extends DAO<Commande>{
    public static CommandeDAO instance = new CommandeDAO();
    @Override
    public Commande find(long id) {
        Commande commande = new Commande();
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"SELECT * FROM commande WHERE commande_id = ?",false,id);
            ResultSet result = prepare.executeQuery();
            if(result.first())
                commande = new Commande(
                        result.getLong("commande_id"),
                        result.getLong("client_id")
                );
            //TODO : gérer le cas où rien ne serait trouver
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }

    @Override
    public Commande create(Commande commande) {
        try {
            //TODO : vérifier que les paramètres sont corrects ,gérer le cas où rien ne serait créer
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"INSERT INTO commande (client_id) VALUES(?)",
                    true,commande.getId());
            prepare.executeUpdate();
            ResultSet result = prepare.getGeneratedKeys();
            if (result.next()) {
                commande = this.find(result.getLong( 1 ));
            }
            // TODO : une fois la commande créer, lier les produits du panier à la commande
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }

    @Override
    public Commande update(Commande commande) {
        try {
            //TODO : vérifier que tous les paramètres sont correctement remplis, et si l'update a été effectuer
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"UPDATE commande SET client_id WHERE commande_id = ?",
                    false,commande.getClient_id());
            prepare.executeUpdate();
            commande = this.find(commande.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }

    @Override
    public void delete(long id) {
        try {
            // TODO : Supprimer tous les liens de produit en rapport avec la commande et verifier la suppression
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"DELETE FROM commande WHERE commande_id = ?",
                    false,id);
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Todo : faire une fonction pour récupérer tous les produits d'une commande
}
