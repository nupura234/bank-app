package example.micronaut.service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ExcelServiceImpl implements ExcelService {

    public ExcelServiceImpl() { }


    @Override
    public XSSFSheet getSheet() throws IOException {


        Firestore db = FirestoreClient.getFirestore();

        CollectionReference banks = db.collection("banks");

        String fileName = "src/main/data/68774.xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook(fileName);

        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

        return sheet;
    }
}
