package ressource;

import dao.ProduitDAO;
import lombok.AllArgsConstructor;
import model.Produit;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putProduit(Produit produit) {
        return putAndGetResponse(produit);
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
        Produit test = ProduitDAO.instance.find(produit.getId());
        if(test != null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
        }
        // TODO : Verifier que tout les caractères sont correct.
        ProduitDAO.instance.update(produit);
        return res;
    }
}
