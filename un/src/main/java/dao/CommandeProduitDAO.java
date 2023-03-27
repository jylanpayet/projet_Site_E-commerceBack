package dao;

import model.Panier;
import model.Produit;
import model.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeProduitDAO extends DAO{
    public static CommandeProduitDAO instance = new CommandeProduitDAO();

    public void create(long commande_id, Panier panier){
        try{
            for(Pair<Integer, Produit> pair : panier.getPanier()){
                PreparedStatement prepare = initialisationRequetePreparee(this.connect,"INSERT INTO list_commande_produit (commande_id , produit_id, nombre) VALUES(?, ?, ?)",
                        false,commande_id,pair.getScd().getId(),pair.getFst());
                prepare.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Panier allLink(long commande_id){
        List<Pair<Integer, Produit>> resultat = new ArrayList<>();
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"SELECT * FROM list_commande_produit WHERE commande_id = ?",false, commande_id);
            ResultSet result = prepare.executeQuery();
            if(result.next()) {
                do {
                    resultat.add(new Pair<>(result.getInt("nombre"), ProduitDAO.instance.find(result.getLong("produit_id"))));
                } while (result.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Panier(resultat);
    }
}
