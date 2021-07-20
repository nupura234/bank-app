package example.micronaut.service;

public interface BankService<T>  {

    Object getALLIFSC();
    Object getBank(String id) throws Exception;
    void update(T model);
    void delete(T model);

}
