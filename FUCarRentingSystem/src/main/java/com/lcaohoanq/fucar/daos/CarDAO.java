package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.Customer;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CarDAO implements ICarDAO {

    private final SessionFactory sessionFactory;

    public CarDAO(String persistenceName) {
        Configuration cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }
    
    @Override
    public void save(Car car) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.beginTransaction();
            session.persist(car);
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
    public List<Car> findAll() {
        List<Car> cars = null;
        Session session = sessionFactory.openSession();
        try {
            cars = session.createQuery("from Car", Car.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return cars;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            Car car = session.get(Car.class, id);
            if (car != null) {
                session.remove(car);
                System.out.println("Successfully Deleted");
            } else {
                System.out.println("Car not found for ID: " + id);
            }
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            System.out.println("Error during deletion: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Car car) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.beginTransaction();
            session.merge(car);
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
    public Car findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Car.class, id);
        }
    }


    @Override
    public List<Car> findAllWithCarProducers() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT DISTINCT c FROM Car c LEFT JOIN FETCH c.producer";
            return session.createQuery(hql, Car.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllCarNames() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT c.carName FROM Car c";
            return session.createQuery(hql, String.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Car> findByCarName(String name) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT c FROM Car c WHERE c.carName = :name";
            return session.createQuery(hql, Car.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        } finally {
            session.close();
        }
    }
}
