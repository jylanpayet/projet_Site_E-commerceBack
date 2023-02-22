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
    public List<Produit> getProduits(){
        return ProduitDAO.instance.allProduit();
    }
    @Path("{categorie}")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Produit> getProduitsOfCategorie(@PathParam("categorie") String categorie){
        return ProduitDAO.instance.produitOfCategorie(categorie);
    }
    @Path("{categorie}/{sousCategorie}")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Produit> getProduitsOfCategorie(@PathParam("categorie") String categorie, @PathParam("sousCategorie") String sousCategorie){
        return ProduitDAO.instance.produitOfSousCategorie(categorie,sousCategorie);
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
        // TODO : Verifier que tout les caractères sont correct.
        ProduitDAO.instance.create(nouveau);
        // redirect
        servletResponse.sendRedirect("");
    }

    @Path("{id}")
    public ProduitRessource getProduit(@PathParam("id") Long id) {
        return new ProduitRessource(uriInfo, request, id);
    }
}
