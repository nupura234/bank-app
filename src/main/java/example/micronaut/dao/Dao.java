package example.micronaut.dao;

import example.micronaut.service.Bank;

import java.util.List;

public interface Dao<T> {

    Object get(String id);
    List<Bank> getAll();
    void update(Bank bank);
    void delete(String id);

}
