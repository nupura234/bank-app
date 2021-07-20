package example.micronaut.dto;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import example.micronaut.service.Bank;
import example.micronaut.service.ExcelServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class AbstractFireStoreRepository<T> {

    private CollectionReference collectionReference;
    private String collectionName;
    private Class<T> parameterizedType;
    private List<ApiFuture<WriteResult>> banks_list = new ArrayList<>();
    private static final Logger log = LogManager.getLogger(AbstractFireStoreRepository.class.getName());



    public AbstractFireStoreRepository(Firestore firestore, String collection) throws Exception {
        this.collectionReference = firestore.collection(collection);
        this.collectionName = collection;
        this.parameterizedType = getParameterizedType();
        this.saveAll();

    }


    public void saveAll() throws Exception {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

        if(queryDocumentSnapshots.isEmpty()){
            XSSFSheet sheet = new ExcelServiceImpl().getSheet();
            int last = 6;
            int first = 1;
            for (int rownum = first; rownum <= last; rownum++) {
                XSSFRow row = sheet.getRow(rownum);
                if (row != null) {
                    Bank bank = new Bank(row);
                    Optional<Object> res = get(row.getCell(first).getStringCellValue());
                    if(res.isPresent()){
                        banks_list.add(collectionReference.document(row.getCell(first).getStringCellValue()).set(bank));
                    }
                }
            }
            ApiFutures.allAsList(banks_list);

        }


    }


    private Class<T> getParameterizedType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    public boolean save(T model){
        String documentId = getDocumentId(model);
        ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).set(model);

        try {
            log.info("{}-{} saved at{}", collectionName, documentId, resultApiFuture.get().getUpdateTime());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error saving {}={} {}", collectionName, documentId, e.getMessage());
        }

        return false;

    }

    public void delete(T model) {
        String documentId = getDocumentId(model);
        ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).delete();

    }

    public List<T> retrieveAll() {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();

        try {
            List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

            return queryDocumentSnapshots.stream()
                    .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(parameterizedType))
                    .collect(Collectors.toList());

        } catch (InterruptedException | ExecutionException e) {
           log.error(e);
        }
        return Collections.<T>emptyList();

    }


    public Optional<Object> get(String documentId) throws ExecutionException, InterruptedException, Exception {

        DocumentReference documentReference = collectionReference.document(documentId);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();

        try {
            DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

            if(documentSnapshot.exists()){
                return Optional.ofNullable(documentSnapshot.toObject(parameterizedType));
            }

        } catch (InterruptedException | ExecutionException e) {
            log.error("Exception occurred retrieving: {} {}, {}", collectionName, documentId, e.getMessage());
            return Optional.empty();
        }

        return Optional.empty();

    }


    protected String getDocumentId(T t) {
        Object key;
        Class clas = t.getClass();
        do {
            key = getKeyFromFields(clas, t);
            clas = clas.getSuperclass();
        } while (key == null && clas != null);

        if (key == null) {
            return UUID.randomUUID().toString();
        }
        return String.valueOf(key);
    }

    private Object getKeyFromFields(Class<?> clazz, Object t) {

        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DocumentId.class))
                .findFirst()
                .map(field -> getValue(t, field))
                .orElse(null);
    }

    @Nullable
    private Object getValue(Object t, java.lang.reflect.Field field) {
        field.setAccessible(true);
        try {
            return field.get(t);
        } catch (IllegalAccessException e) {
            log.error(e);
        }
        return null;
    }

    protected CollectionReference getCollectionReference() {
        return this.collectionReference;
    }

    protected Class<T> getType() {
        return this.parameterizedType;
    }
}
