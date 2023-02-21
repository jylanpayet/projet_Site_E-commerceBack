package ressource;

import dao.ClientDAO;
import lombok.AllArgsConstructor;
import model.Client;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@AllArgsConstructor
public class ClientRessource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    Long id;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Client getClient(){
        Client client = ClientDAO.instance.find(this.id);
        if(client == null)
            throw new RuntimeException("Get: Client with " + id +  " not found");
        return client;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putClient(Client client) {
        return putAndGetResponse(client);
    }

    @DELETE
    public void deleteTodo() {
        ClientDAO.instance.delete(id);
        Client test = ClientDAO.instance.find(id);
        if(test != null)
            throw new RuntimeException("Delete: Todo with " + id +  " not found");
    }

    private Response putAndGetResponse(Client client) {
        Response res;
        Client test = ClientDAO.instance.find(client.getId());
        if(test != null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
        }
        System.out.println(client);
        // TODO : Verifier que tout les caractères sont correct.
        ClientDAO.instance.update(client);
        return res;
    }
}
