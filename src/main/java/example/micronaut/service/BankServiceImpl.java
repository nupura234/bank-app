package example.micronaut.service;

import example.micronaut.dao.BankDao;
import example.micronaut.dao.Dao;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.*;

@Singleton
public class BankServiceImpl implements BankService {


    private BankDao bankDao;

    public BankServiceImpl() throws IOException {

        bankDao = new BankDao();


    }

    public List<Bank> getALLIFSC(){

        return this.bankDao.getAll();

    }


    public Object getBank(String id) throws IOException {

            return this.bankDao.get(id);

    }


    public void update(Bank bank) throws IOException {

        this.bankDao.update(bank);

    }

    public void delete(String id){

        this.bankDao.delete(id);
    }

}
