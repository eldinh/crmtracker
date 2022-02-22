package com.crmtracker.dao;

import com.crmtracker.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO{
    private final Logger log = (Logger) LogManager.getLogger(CustomerDAOImpl.class.getName());

    // need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        log.info("Starting CustomerDAOImpl getCustomers[0]");

        log.debug("getCustomers[1]: getting the current hibernate session");
        Session session = sessionFactory.getCurrentSession();

        log.debug("getCustomers[2]: creating a query");
        Query<Customer> query = session.createQuery("from Customer order by lastName",
                                                    Customer.class);

        log.debug("getCustomers[3]: executing query and get result list");
        List<Customer> customers = query.getResultList();

        // return the results
        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        log.info("Starting CustomerDAOImpl saveCustomer[0]");
        log.info("saveCustomer[1]: customer - {}", customer);

        log.debug("saveCustomer[2]: Getting current hibernate session");
        Session session = sessionFactory.getCurrentSession();

        log.debug("saveCustomer[3]: Saving/updating the customer");
        session.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int id) {
        log.info("Starting CustomerDAOImpl getCustomer[0]");
        log.info("getCustomer[1]: id - {}", id);

        log.debug("getCustomer[2]: Getting the current hibernate session");
        Session session = sessionFactory.getCurrentSession();

        log.debug("getCustomer[3]: Retrieving customer from db using primary key {}", id);
        return session.get(Customer.class, id);
    }

    @Override
    public void deleteCustomer(int id) {
        log.info("Starting CustomerDAOImpl deleteCustomer[0]");
        log.info("deleteCustomer[1]: id - {}", id);

        log.debug("deleteCustomer[2]: Getting current session");
        Session session = sessionFactory.getCurrentSession();

        log.debug("deleteCustomer[3]: Deleting customer with primary key {}", id);
        Query query = session.createQuery("delete from Customer where id=:customerId");
        query.setParameter("customerId", id);

        log.debug("deleteCustomer[4]: Executing update");
        query.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String searchName) {
        log.info("Starting CustomerDAOImpl searchCustomers[0]");

        log.info("searchCustomers[1]: searchName - {}", searchName);
        log.debug("searchCustomers[2]: Getting current hibernate session");
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Customer> theQuery;

        //
        // only search by name if theSearchName is not empty
        //
        if (searchName != null && searchName.trim().length() > 0) {
            log.debug("searchCustomers[3]: search for firstName or lastName ... case-insensitive");
            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + searchName.toLowerCase() + "%");
        }
        else {
            log.debug("searchCustomers[4]: searchName is empty ... so just get all customers");
            theQuery =currentSession.createQuery("from Customer", Customer.class);
        }

        log.debug("searchCustomers[5]: execute query and get result list");
        return (List<Customer>) theQuery.getResultList();

    }
}
