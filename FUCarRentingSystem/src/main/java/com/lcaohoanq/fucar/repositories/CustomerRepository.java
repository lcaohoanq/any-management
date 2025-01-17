package com.lcaohoanq.fucar.repositories;

import com.lcaohoanq.fucar.daos.CustomerDAO;
import com.lcaohoanq.fucar.daos.ICustomerDAO;
import com.lcaohoanq.fucar.models.Customer;
import java.util.List;

public class CustomerRepository implements ICustomerRepository {

    private ICustomerDAO customerDAO;

    public CustomerRepository(String jpaName) {
        customerDAO = new CustomerDAO(jpaName);
    }

    @Override
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public Customer findById(Integer id) {
        return customerDAO.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public void delete(Integer id) {
        customerDAO.delete(id);
    }

    @Override
    public void update(Customer customer) {
        customerDAO.update(customer);
    }

    @Override
    public List<Customer> findAllWithAccounts() {
        return customerDAO.findAllWithAccounts();
    }

    @Override
    public Customer findByIdWithAccount(Integer id) {
        return customerDAO.findByIdWithAccount(id);
    }

    @Override
    public Customer findByAccountName(String accountName) {
        return customerDAO.findByAccountName(accountName);
    }
}
