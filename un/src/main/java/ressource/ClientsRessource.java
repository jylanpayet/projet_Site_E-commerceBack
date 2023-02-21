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
        // TODO : Verifier que tout les caractères sont correct.
        ClientDAO.instance.create(nouveau);
        // redirect
        servletResponse.sendRedirect("");
    }

    @Path("{id}")
    public ClientRessource getClient(@PathParam("id") Long id) {
        return new ClientRessource(uriInfo, request, id);
    }
}