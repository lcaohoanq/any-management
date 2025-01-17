package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Customer;
import com.lcaohoanq.fucar.repositories.CustomerRepository;
import com.lcaohoanq.fucar.repositories.ICustomerRepository;
import java.util.List;

public class CustomerService implements ICustomerService{

    private ICustomerRepository customerRepository;

    public CustomerService(String jpaName){
        customerRepository = new CustomerRepository(jpaName);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        customerRepository.delete(id);
    }

    @Override
    public void update(Customer customer) {
        customerRepository.update(customer);
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id);
    }

    public static void main(String[] args) {
        CustomerService customerService = new CustomerService("hibernate.cfg.xml");
        List<Customer> customers = customerService.findAll();
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    @Override
    public List<Customer> findAllWithAccounts() {
        return customerRepository.findAllWithAccounts();
    }

    @Override
    public Customer findByIdWithAccount(Integer id) {
        return customerRepository.findByIdWithAccount(id);
    }

    @Override
    public Customer findByAccountName(String accountName) {
        return customerRepository.findByAccountName(accountName);
    }

}
