package ressource;
import dao.ProduitDAO;
import model.Produit;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

@Path("produits")
public class ProduitsRessource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Produit> getProduits(@QueryParam("categorie") String categorie, @QueryParam("sousCategorie") String sousCategorie){
        if(sousCategorie != null)
            return ProduitDAO.instance.produitOfSousCategorie(categorie,sousCategorie);
        else if (categorie != null)
            ProduitDAO.instance.produitOfCategorie(categorie);
        return ProduitDAO.instance.allProduit();
    }
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        return String.valueOf(ProduitDAO.instance.allProduit().size());
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void nouveauProduit(
            @FormParam("nom") String nom,
            @FormParam("description") String description,
            @FormParam("prix") double prix,
            @FormParam("categorie") String categorie,
            @FormParam("sousCategorie") String sousCategorie,
            @Context HttpServletResponse servletResponse) throws IOException {
        Produit nouveau = new Produit();
        if (nom != null && description != null && categorie != null && sousCategorie != null) {
            nouveau.setNom(nom);
            nouveau.setDescription(description);
            nouveau.setPrix(prix);
            nouveau.setCategorie(Produit.Categorie.valueOf(categorie));
            nouveau.setSousCategorie(Produit.SousCategorie.valueOf(sousCategorie));
        } else {
            throw new IOException("Les champs ne sont pas correct.");
        }
        // TODO : Verifier que tout les champs sont correct (Catégorie ect...)
        ProduitDAO.instance.create(nouveau);
        // redirect
        servletResponse.sendRedirect("");
    }
    @Path("{id}")
    public ProduitRessource getProduit(@PathParam("id") long id) {
        return new ProduitRessource(uriInfo, request, id);
    }
}
