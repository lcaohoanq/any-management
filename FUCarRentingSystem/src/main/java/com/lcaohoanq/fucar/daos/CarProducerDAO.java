package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.models.CarProducer;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CarProducerDAO implements ICarProducerDAO{

    private final SessionFactory sessionFactory;

    public CarProducerDAO(String persistenceName) {
        Configuration cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }

    @Override
    public void save(CarProducer carProducer) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.beginTransaction();
            session.persist(carProducer);
            t.commit();
            System.out.println("Successfully saved");
        } catch (Exception e) {
            assert t != null;
            t.rollback();
            System.out.println("Error " + e.getMessage());
            // TODO: handle exception
        }
//			sessionFactory.close();
    }

    @Override
    public List<CarProducer> findAll() {
        List<CarProducer> carProducers = null;
        Session session = sessionFactory.openSession();
        try {
            carProducers = session.createQuery("from CarProducer", CarProducer.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return carProducers;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.getTransaction();
            t.begin();
            Account account = session.get(Account.class, id);
            session.remove(account);
            t.commit();
            System.out.println("Successfully Delete");
        } catch (Exception e) {
            assert t != null;
            t.rollback();
            System.out.println("Error " + e.getMessage());
        }
//			sessionFactory.close();
    }

    @Override
    public void update(CarProducer carProducer) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.beginTransaction();
            session.merge(carProducer);
            t.commit();
            System.out.println("Update Success");
        } catch (Exception e) {
            assert t != null;
            t.rollback();
            System.out.println("Error " + e.getMessage());
        }
//			sessionFactory.close();
    }

    @Override
    public CarProducer findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CarProducer.class, id);
        }
    }
}
