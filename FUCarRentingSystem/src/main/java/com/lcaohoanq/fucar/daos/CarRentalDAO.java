package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.models.Car;
import com.lcaohoanq.fucar.models.CarRental;
import com.lcaohoanq.fucar.models.Customer;
import jakarta.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CarRentalDAO implements ICarRentalDAO{

    private final SessionFactory sessionFactory;

    public CarRentalDAO(String persistenceName) {
        Configuration cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }

    @Override
    public void save(CarRental carRental) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.beginTransaction();
            session.persist(carRental);
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
    public List<CarRental> findAll() {
        List<CarRental> carRentals = null;
        Session session = sessionFactory.openSession();
        try {
            carRentals = session.createQuery("from CarRental", CarRental.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return carRentals;
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
    public void update(CarRental carRental) {
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try (session) {
            t = session.beginTransaction();
            session.merge(carRental);
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
    public CarRental findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CarRental.class, id);
        }
    }

    @Override
    public List<CarRental> findRentalsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<CarRental> rentals;
        try (Session session = sessionFactory.openSession()) {
            // Convert LocalDate to java.sql.Date
            Date startSqlDate = Date.valueOf(startDate);
            Date endSqlDate = Date.valueOf(endDate);

            // Create HQL query to find rentals within the date range
            String hql = "FROM CarRental cr WHERE cr.pickupDate >= :startDate AND cr.returnDate <= :endDate";
            TypedQuery<CarRental> query = session.createQuery(hql, CarRental.class);
            query.setParameter("startDate", startSqlDate);
            query.setParameter("endDate", endSqlDate);

            // Execute the query and return the results
            rentals = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return rentals;
    }

    @Override
    public boolean isCarExist(Car car) {
        try (Session session = sessionFactory.openSession()) {
            // Create HQL query to check if the car exists in any CarRental
            String hql = "SELECT COUNT(cr) FROM CarRental cr WHERE cr.car = :car";
            TypedQuery<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("car", car);

            // Get the count of rentals with the specified car
            Long count = query.getSingleResult();

            // If count is greater than 0, the car exists in at least one rental
            return count > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public CarRental findByCarId(Integer carId) {
        try (Session session = sessionFactory.openSession()) {
            // Create HQL query to find CarRental by carId
            String hql = "FROM CarRental cr WHERE cr.car.id = :carId";
            TypedQuery<CarRental> query = session.createQuery(hql, CarRental.class);
            query.setParameter("carId", carId);

            // Return a single CarRental, or null if none found
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Customer> findCustomersByCarId(Integer carId) {
        List<Customer> customers;
        try (Session session = sessionFactory.openSession()) {
            // Create HQL query to find customers by carId
            String hql = "SELECT cr.customer FROM CarRental cr WHERE cr.car.id = :carId";
            TypedQuery<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("carId", carId);

            // Return the list of customers
            customers = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return customers;
    }

    @Override
    public List<CarRental> findAllByCustomerId(Integer customerId) {
        List<CarRental> rentals;
        try (Session session = sessionFactory.openSession()) {
            // Create HQL query to find rentals by customerId
            String hql = "FROM CarRental cr WHERE cr.customer.id = :customerId";
            TypedQuery<CarRental> query = session.createQuery(hql, CarRental.class);
            query.setParameter("customerId", customerId);

            // Return the list of rentals
            rentals = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return rentals;
    }

    public static void main(String[] args) {
	
    	CarRentalDAO carRentalDao = new CarRentalDAO(ResourcePaths.HIBERNATE_CONFIG);
    	CarDAO carDAO = new CarDAO(ResourcePaths.HIBERNATE_CONFIG);
    	
    	Car car = carDAO.findById(1);
    	System.out.println(car);
    	
    	CarRental carRental = carRentalDao.findByCarId(1);

    	if(carRentalDao.isCarExist(car)) {
    		System.out.println("Already exist car in rental transaction");
    		
    		System.out.println(carRental);
    		
    	}else {
    		System.out.println("No exist");
    	}

        for(CarRental cr : carRentalDao.findAll()) {
        	System.out.println(cr);
        }
    	
    	
	}
    
    
}
