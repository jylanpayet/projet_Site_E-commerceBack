package ressource;

import dao.CommandeDAO;
import dao.CommandeProduitDAO;
import lombok.AllArgsConstructor;
import model.Commande;
import model.Panier;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@AllArgsConstructor
public class CommandeRessource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    long id;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Panier getCommande(){
        Panier commande = CommandeProduitDAO.instance.allLink(id);
        if(commande == null)
            throw new RuntimeException("Get: Commande with " + id +  " not found");
        return commande;
    }
    @DELETE
    public void deleteCommande() {
        CommandeDAO.instance.delete(id);
        Commande test = CommandeDAO.instance.find(id);
        if(test != null)
            throw new RuntimeException("Delete: Commande with " + id +  " not found");
    }



}
