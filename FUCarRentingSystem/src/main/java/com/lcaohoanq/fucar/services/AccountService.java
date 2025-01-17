package com.lcaohoanq.fucar.services;

import com.lcaohoanq.fucar.models.Account;
import com.lcaohoanq.fucar.repositories.AccountRepository;
import com.lcaohoanq.fucar.repositories.IAccountRepository;
import java.util.List;

public class AccountService implements IAccountService{

    private final IAccountRepository accountRepo;

    public AccountService(String name) {
        accountRepo = new AccountRepository(name);
    }

    @Override
    public void save(Account account) {
        accountRepo.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    @Override
    public void delete(Integer id) {
        accountRepo.delete(id);
    }

    @Override
    public Account findById(Integer id) {
        return accountRepo.findById(id);
    }

    @Override
    public void update(Account account) {
        accountRepo.update(account);
    }

    @Override
    public Account login(String email, String password) {
        return accountRepo.login(email, password);
    }

    @Override
    public Account findByUserName(String username) {
        return accountRepo.findByUserName(username);
    }

    @Override
    public void signup(Account newAccount) {
        accountRepo.signup(newAccount);
    }
}
