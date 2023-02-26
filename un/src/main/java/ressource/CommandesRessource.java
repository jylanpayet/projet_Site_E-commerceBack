package ressource;
import dao.CommandeDAO;
import dao.CommandeProduitDAO;
import model.Commande;
import model.Panier;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("commandes/{client_id}")
public class CommandesRessource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    //TODO : lister les commandes d'un utilisateur
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Commande> getAllCommandeOfClient(@PathParam("client_id") long id){
        return CommandeDAO.instance.allCommandeOfClient(id);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void nouvelleCommande(@PathParam("client_id") long id, Panier panier){
        CommandeDAO.instance.create(id,panier);
    }

    @Path("{commande_id}")
    public CommandeRessource getCommande(@PathParam("commande_id") Long id) {
        return new CommandeRessource(uriInfo, request, id);
    }

}
