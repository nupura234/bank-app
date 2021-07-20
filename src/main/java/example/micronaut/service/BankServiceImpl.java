package example.micronaut.service;

import com.google.cloud.firestore.Firestore;
import example.micronaut.dto.BankDTO;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Singleton
public class BankServiceImpl implements BankService {

    private Firestore config;
    private BankDTO bankdto;
    private String collectionRef;

    private static final Logger log = LogManager.getLogger(BankServiceImpl.class.getName());

    public BankServiceImpl() throws Exception {

        this.config = new FireStoreConfig().getConf();
        this.collectionRef = "banks";
        bankdto = new BankDTO(config, collectionRef);

    }

    public Object getALLIFSC(){
             return this.bankdto.retrieveAll();
        }


    public Object getBank(String id) throws Exception {
            return this.bankdto.get(id);

    }

    @Override
    public void update(Object model) {
        this.bankdto.save((Bank) model);

    }

    @Override
    public void delete(Object model) {
        this.bankdto.delete((Bank) model);

    }



}
