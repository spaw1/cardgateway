/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Card;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author psubedi
 */
@Stateless
@Path("verify")
public class CardFacadeREST extends AbstractFacade<Card> {

    @PersistenceContext(unitName = "PaymentGatewayPU")
    private EntityManager em;

    public CardFacadeREST() {
        super(Card.class);
    }
    
    @POST
    @Path("/ccDetails")
    @Produces({"text/plain"})
    public int hasBalance(@FormParam("no") String ccno,
                          @FormParam("requested") double requested
//                          ,@FormParam("cvc") String cvc,
//                          @FormParam("firstName") String fname,
//                          @FormParam("lastName") String lname,
//                          @FormParam("address1") String street,
//                          @FormParam("city") String city,
//                          @FormParam("state") String state,
//                          @FormParam("zip") String zip
//                          
                          ){
        String q = "SELECT e FROM Card e WHERE e.ccNo = :ccNo AND e.balance >= :requested ";
//                + "AND e.firstName = :fname "
//                + "AND e.lastName = :lname "
//                + "AND e.street = :street "
//                + "AND e.city = :city "
//                + "AND e.state = :state "
//                + "AND e.zip = :zip";
        TypedQuery<Card> query = em.createQuery(q, Card.class);
        query.setParameter("ccNo", ccno);
        query.setParameter("requested", requested);
//        query.setParameter("fname", fname);
//        query.setParameter("lname", lname);
//        query.setParameter("street", street);
//        query.setParameter("city", city);
//        query.setParameter("state", state);
//        query.setParameter("zip", zip);
        try {
            Card c = query.getSingleResult();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Card entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Card entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Card find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Produces({"text/plain"})
    public String splash(){
        return "Credit Card Validator";
    }

//    @GET
//    @Override
//    @Produces({"application/xml", "application/json"})
//    public List<Card> findAll() {
//        return super.findAll();
//    }
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Card> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
