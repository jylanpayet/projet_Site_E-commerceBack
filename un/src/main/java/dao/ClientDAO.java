package dao;

import model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends DAO<Client>{
    public static ClientDAO instance = new ClientDAO();
    @Override
    public Client find(long id) {
        Client client = new Client();
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"SELECT * FROM client WHERE client_id = ?",false,id);
            ResultSet result = prepare.executeQuery();
            if(result.first())
                client = new Client(
                        result.getLong("client_id"),
                        result.getString("nom"),
                        result.getString("adresse"),
                        result.getString("mail"),
                        result.getString("telephone"),
                        result.getString("motdepasse"),
                        result.getLong("admin")
                );
            //TODO : gérer le cas où rien ne serait trouver
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client create(Client client) {
        try {
            //TODO : vérifier que les paramètres sont correctement remplis, gérer le cas où rien ne serait créer
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"INSERT INTO client (nom, mail, adresse , telephone, motdepasse, admin) VALUES(?,?,?,?,?,?)",
                    true,client.getNom(),client.getAdresse(),client.getMail(),client.getTelephone(),client.getMotdepasse(),0);
            prepare.executeUpdate();
            ResultSet result = prepare.getGeneratedKeys();
            if (result.next()) {
                client = this.find(result.getLong( 1 ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client update(Client client) {
        try {
            //TODO : vérifier que tous les paramètres sont correctement remplis, et si l'update a été effectuer
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"UPDATE client SET nom = ?, adresse = ?, mail = ?, telephone = ?, motdepasse = ?, admin = ? WHERE client_id = ?",
                    false,client.getNom(),client.getAdresse(),client.getMail(),client.getTelephone(),client.getMotdepasse(), 0,client.getId());
            prepare.executeUpdate();
            client = this.find(client.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"DELETE FROM client WHERE client_id = ?",
                    false,id);
            prepare.executeUpdate();
            // TODO : verifier la suppression
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> allClient(){
        List<Client> resultat = new ArrayList<>();
        try {
            PreparedStatement prepare = initialisationRequetePreparee(this.connect,"SELECT * FROM client",false);
            ResultSet result = prepare.executeQuery();
            if(result.first()){
                resultat.add(new Client(
                        result.getLong("client_id"),
                        result.getString("nom"),
                        result.getString("adresse"),
                        result.getString("mail"),
                        result.getString("telephone"),
                        result.getString("motdepasse"),
                        result.getLong("admin")
                ));
                while (result.next()){
                    resultat.add(new Client(
                            result.getLong("client_id"),
                            result.getString("nom"),
                            result.getString("adresse"),
                            result.getString("mail"),
                            result.getString("telephone"),
                            result.getString("motdepasse"),
                            result.getLong("admin")
                    ));
                }
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return resultat;
    }
    // Todo : faire une fonction pour récupérer toutes les commandes d'un utilisateur
}
