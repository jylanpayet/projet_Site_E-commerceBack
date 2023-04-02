package ressource;

import dao.ProduitDAO;
import lombok.AllArgsConstructor;
import model.Produit;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;

@AllArgsConstructor
public class ProduitRessource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    long id;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Produit getProduit(){
        Produit produit = ProduitDAO.instance.find(this.id);
        if(produit == null)
            throw new RuntimeException("Get: Client with " + id +  " not found");
        return produit;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response putProduit(@FormParam("nom") String nom,
                               @FormParam("description") String description,
                               @FormParam("prix") String prix,
                               @FormParam("photo") String photo,
                               @Context HttpServletResponse servletResponse) throws IOException {
        Produit update = new Produit();
        if (nom != null && description != null) {
            update.setNom(nom);
            update.setDescription(description);
            update.setPrix(Double.parseDouble(prix));
            update.setPhoto(photo);
        } else {
            throw new IOException("Les champs ne sont pas correct.");
        }
        return putAndGetResponse(update);
    }

    @DELETE
    public void deleteProduit() {
        ProduitDAO.instance.delete(id);
        Produit test = ProduitDAO.instance.find(id);
        if(test != null)
            throw new RuntimeException("Delete: Produit with " + id +  " not found");
    }

    private Response putAndGetResponse(Produit produit) {
        Response res;
        Produit test = ProduitDAO.instance.find(id);
        if(test != null) {
            res = Response.noContent().build();
            test.setNom(produit.getNom());
            test.setDescription(produit.getDescription());
            test.setPrix(produit.getPrix());
            test.setPhoto(produit.getPhoto());
            ProduitDAO.instance.update(test);
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
        }
        // TODO : Verifier que tout les caractères sont correct.
        return res;
    }
}
