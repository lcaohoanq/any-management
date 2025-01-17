package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.Customer;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CustomerDAO implements ICustomerDAO {

    private SessionFactory sessionFactory = null;
    private Configuration cf = null;

    public CustomerDAO(String persistenceName) {
        cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }

    @Override
    public void save(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(customer);
            t.commit();
            System.out.println("Successfully saved");
        } catch (Exception e) {
            t.rollback();
            System.out.println("Error " + e.getMessage());
            // TODO: handle exception
        } finally {
//			sessionFactory.close();
            session.close();
        }
    }

    @Override
    public Customer findById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return (Customer) session.get(Customer.class, id);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = null;
        Session session = sessionFactory.openSession();
        try {
            customers = session.createQuery("from Customer", Customer.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
//			sessionFactory.close();
        }
        return customers;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction t = session.getTransaction();
        try {
            t.begin();
            Customer account = (Customer) session.get(Customer.class, id);
            session.delete(account);
            t.commit();
            System.out.println("Successfully Delete");
        } catch (Exception e) {
            t.rollback();
            System.out.println("Error " + e.getMessage());
        } finally {
//			sessionFactory.close();
            session.close();
        }
    }

    @Override
    public void update(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(customer);
            t.commit();
            System.out.println("Update Success");
        } catch (Exception e) {
            t.rollback();
            System.out.println("Error " + e.getMessage());
        } finally {
//			sessionFactory.close();
            session.close();
        }
    }

    @Override
    public List<Customer> findAllWithAccounts() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.account";
            return session.createQuery(hql, Customer.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Customer findByIdWithAccount(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT c FROM Customer c LEFT JOIN FETCH c.account WHERE c.customerId = :id";
            return session.createQuery(hql, Customer.class)
                .setParameter("id", id)
                .uniqueResult();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Customer findByAccountName(String accountName) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT c FROM Customer c JOIN FETCH c.account a WHERE a.accountName = :accountName";
            return session.createQuery(hql, Customer.class)
                .setParameter("accountName", accountName)
                .uniqueResult();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }


}
