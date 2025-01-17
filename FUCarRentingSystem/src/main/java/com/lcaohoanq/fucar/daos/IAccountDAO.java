package com.lcaohoanq.fucar.daos;

import com.lcaohoanq.fucar.models.Account;
import java.util.List;

public interface IAccountDAO {

    void save(Account account);
    List<Account> findAll();
    void delete(Integer id);
    Account findById(Integer id);
    List<Account> findByPassword(String password);
    Account findByUserName(String username);
    void update(Account account);
    Account login(String email, String password);
    void signup(Account newAccount);
}