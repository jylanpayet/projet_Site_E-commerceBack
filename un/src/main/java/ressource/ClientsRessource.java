package ressource;

import dao.ClientDAO;
import model.Client;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("clients")
public class ClientsRessource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Client> getClients(){
        return ClientDAO.instance.allClient();
    }
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        return String.valueOf(ClientDAO.instance.allClient().size());
    }

    @GET
    @Path("/by-email/{email}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Client getClientByEmail(@PathParam("email") String email) {
        System.out.println("Email received: " + email);
        // Utilisez la méthode appropriée de votre DAO pour trouver un client par email
        Client client = ClientDAO.instance.findClientByEmail(email);
        System.out.println("Client found: " + client);
        return client;
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void nouveauCLient(
                        @FormParam("nom") String nom,
                        @FormParam("mail") String mail,
                        @FormParam("adresse") String adresse,
                        @FormParam("telephone") String telephone,
                        @FormParam("motdepasse") String motdepasse,
                        @Context HttpServletResponse servletResponse) throws IOException {
        Client nouveau = new Client();
        if (nom != null && mail != null && adresse != null && telephone != null && motdepasse != null) {
            nouveau.setNom(nom);
            nouveau.setMail(mail);
            nouveau.setAdresse(adresse);
            nouveau.setTelephone(telephone);
            nouveau.setMotdepasse(motdepasse);
        } else {
            throw new IOException("Les champs ne sont pas correct.");
        }
        // TODO : Verifier que tout les champs sont correct (Bon mail ect ...)
        ClientDAO.instance.create(nouveau);
        // redirect
        servletResponse.sendRedirect("");
    }

    @Path("{id}")
    public ClientRessource getClient(@PathParam("id") long id) {
        return new ClientRessource(uriInfo, request, id);
    }
}
