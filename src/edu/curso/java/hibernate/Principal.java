package edu.curso.java.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import edu.curso.java.hibernate.bo.Cliente;
import edu.curso.java.hibernate.bo.Direccion;
import edu.curso.java.hibernate.bo.Pedido;

public class Principal {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Session session = null;
        FullTextSession fullTextSession = null;

        /*
		 
    	  session = HibernateUtil.getSessionFactory().openSession();

            fullTextSession = Search.getFullTextSession(session);


            Transaction transaction = fullTextSession.beginTransaction();

           Cliente cliente = new Cliente();

           cliente.setNombre("Claudio");
           cliente.setApellido("Samoray");
           cliente.setCuit("23-3365321-9");
           cliente.setFechaAlta(new Date());

           Long id = (Long) fullTextSession.save(cliente);
           System.out.println("Id generado " + id);

           cliente = new Cliente();

           cliente.setNombre("Maria");
           cliente.setApellido("Lopez");
           cliente.setCuit("30-2146781-1");
           cliente.setFechaAlta(new Date());

           id = (Long) fullTextSession.save(cliente);
           System.out.println("Id generado " + id);


           transaction.commit();

           fullTextSession.close();*/
        session = HibernateUtil.getSessionFactory().openSession();

        fullTextSession = Search.getFullTextSession(session);

        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Cliente.class).get();

        System.out.println("Simple ------------------------------------------------");

        org.apache.lucene.search.Query query = qb.keyword()
                .onFields("nombre", "apellido").matching("lopez")
                .createQuery();

        org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
                query, Cliente.class);

        List<Cliente> result = hibQuery.list();
        for (Cliente c : result) {
            System.out.println(c.getNombre() + " " + c.getApellido());
        }

        System.out.println("Fuzzy------------------------------------------------");

        query = qb.keyword().fuzzy().onField("apellido").matching("zamorai").createQuery();

        hibQuery = fullTextSession.createFullTextQuery(
                query, Cliente.class);

        result = hibQuery.list();
        for (Cliente c : result) {
            System.out.println(c.getNombre() + " " + c.getApellido());
        }

        System.out.println("Wildcard------------------------------------------------");

        query = qb.keyword().wildcard().onField("apellido").matching("sa*").createQuery();

        hibQuery = fullTextSession.createFullTextQuery(
                query, Cliente.class);

        result = hibQuery.list();
        for (Cliente c : result) {
            System.out.println(c.getNombre() + " " + c.getApellido());
        }

        fullTextSession.close();

        HibernateUtil.getSessionFactory().close();

    }

}
