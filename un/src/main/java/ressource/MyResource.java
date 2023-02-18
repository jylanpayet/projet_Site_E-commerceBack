package ressource;

import dao.ClientDAO;
import model.Client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * ICI, c'est comme une classe hello world !
 *
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        // Test pour verifier que la bdd fonctionne bien,
        // à retravailler par la suite.

        // * * * {
        Client c = new Client(0,"coucou","17 rue de la presse","11@gmail.com","088082830");
        ClientDAO coco =new ClientDAO();
        Client d = coco.create(c);
        System.out.println(d);
        coco.delete(d);
        // } * * *

        return "Got it!";
    }
}
