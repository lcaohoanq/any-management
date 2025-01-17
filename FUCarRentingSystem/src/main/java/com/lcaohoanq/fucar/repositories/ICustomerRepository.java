package com.lcaohoanq.fucar.repositories;

import com.lcaohoanq.fucar.models.Customer;
import java.util.List;

public interface ICustomerRepository {
 void save(Customer customer);
    Customer findById(Integer id);
    List<Customer> findAll();
    void delete(Integer id);
    void update(Customer customer);
    List<Customer> findAllWithAccounts();
   Customer findByIdWithAccount(Integer id);
Customer findByAccountName(String accountName);
}
