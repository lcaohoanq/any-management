package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Customer;
import java.util.List;

public interface ICustomerService {
    void save(Customer customer);
    List<Customer> findAll();
    void delete(Integer id);
    void update(Customer customer);
    Customer findById(Integer id);
    List<Customer> findAllWithAccounts();
    Customer findByIdWithAccount(Integer id);
    Customer findByAccountName(String accountName);
}
