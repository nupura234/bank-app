package example.micronaut.service;

import org.mortbay.util.IO;

import java.io.IOException;
import java.util.List;

public interface BankService {

    List getALLIFSC() throws IOException;
    Object getBank(String id) throws IOException;
    void update(Bank bank) throws IOException;
    void delete(String id);

}
