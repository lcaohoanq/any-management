package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Account;
import java.util.List;

public interface IAccountService {
    void save(Account account);
    List<Account> findAll();
    void delete(Integer id);
    Account findById(Integer id);
    void update(Account account);
    Account login(String email, String password);
    Account findByUserName(String username);
    void signup(Account newAccount);
}
