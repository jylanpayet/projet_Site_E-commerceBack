package dao;

import model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO extends DAO<Client>{
    @Override
    public Client find(long id) {
        return null;
    }

    @Override
    public Client create(Client obj) {
        try {

                PreparedStatement prepare = this    .connect
                        .prepareStatement(
                                "INSERT INTO client (client_id, nom, mail, adresse , telephone) VALUES(?,?,?,?,?)"
                        );
                prepare.setLong(1, 1);
                prepare.setString(2, obj.getNom());
                prepare.setString(3, obj.getNom());
                prepare.setString(4, obj.getNom());
                prepare.setString(5, obj.getNom());
                prepare.executeUpdate();
                obj = this.find(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Client update(Client obj) {
        return null;
    }

    @Override
    public void delete(Client obj) {

    }
}
