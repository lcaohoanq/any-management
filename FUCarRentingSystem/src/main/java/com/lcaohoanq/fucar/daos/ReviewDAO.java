package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.models.Review;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ReviewDAO implements IReviewDAO{

    private final SessionFactory sessionFactory;

    public ReviewDAO(String persistenceName) {
        Configuration cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }

    @Override
    public void save(Review review) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.beginTransaction();
            session.persist(review);
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
    public List<Review> findAll() {
        List<Review> reviews = null;
        Session session = sessionFactory.openSession();
        try {
            reviews = session.createQuery("from Review", Review.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return reviews;
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
    public void update(Review review) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.beginTransaction();
            session.merge(review);
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
    public Review findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Review.class, id);
        }
    }
}
