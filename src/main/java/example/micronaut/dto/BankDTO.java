package example.micronaut.dto;

import com.google.cloud.firestore.*;
import example.micronaut.service.Bank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

/* TODO
 *  add singleton annotatino for all classes//
 *  separate service for firestore initialisation, inject in to dao class//
 *  create diff excel service layer. return sheet//
 *  ==== === handle exceptions at dao,service layer. return standard error response. write logger to log exceptions
 *  add utility file for firestore//
 *  write code to initialize db only once//
 *  check factory pattern//
 *  add validation for existing ids if id exists.. dont populate//
 */

@Singleton
public class BankDTO extends AbstractFireStoreRepository<Bank>{

    private static final Logger log = LogManager.getLogger(AbstractFireStoreRepository.class.getName());


    public BankDTO(Firestore firestore, String collectionRef) throws Exception {
        super(firestore, collectionRef);

    }

    @Override
    public Optional<Object> get(String id) throws Exception {
            return super.get(id);
    }

    @Override
    public List<Bank> retrieveAll () {
            return super.retrieveAll();
        }

    @Override
    public boolean save (Bank bank){
            return super.save(bank);
    }

    @Override
    public void delete (Bank bank){
         super.delete(bank);

        }

    }


