package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {
    /**
     * URL de connexion à la base de donnée
     */
    private static String url = "jdbc:mysql://localhost:3306/asii";
    /**
     * Nom de l'user
     */
    private static String user = "root";
    /**
     * Mot de passe de l'user
     */
    private static String passwd = "";
    /**
     * Objet Connexion
     */
    private static Connection connect;

    /**
     * Méthode qui va nous retourner notre instance
     * et la créer si elle n'existe pas...
     * @return
     */
    public static Connection getInstance(){
        if(connect == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }
}
