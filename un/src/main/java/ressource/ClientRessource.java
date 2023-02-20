package ressource;

import dao.ClientDAO;
import model.Client;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("client")
public class ClientRessource {

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Client getClient(@PathParam("id") long id){
        Client client = ClientDAO.instance.find(id);
        if(client == null)
            throw new RuntimeException("Get: Client with " + id +  " not found");
        return client;
    }
}
