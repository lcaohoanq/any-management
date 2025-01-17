package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.Customer;
import java.util.List;

public interface ICustomerDAO {
    void save(Customer customer);
    List<Customer> findAll();
    void delete(Integer id);
    void update(Customer customer);
    Customer findById(Integer id);
    List<Customer> findAllWithAccounts();
    Customer findByIdWithAccount(Integer id);
    Customer findByAccountName(String accountName);
}