package example.micronaut;

import example.micronaut.model.Person;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.reactivex.Single;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.IOException;
import java.util.Iterator;

@Controller("/api")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Inject
    private HashSet<Person> personsRepository;

    public PersonController(HashSet<Person> personsRepository) {
        this.personsRepository = personsRepository;
    }

    @Post("/submitPerson")
    public Single<String> submitPerson(@Body Person person){

        return Single.just(this.personsRepository.add(person)? "success" : "failed");
    }

    @Get(value = "/persons", produces = MediaType.APPLICATION_JSON_STREAM)
    public Map persons()
    {
        Map map = new HashMap();
        for (Person person : this.personsRepository) {
            map.put("email",person.email);
            map.put("name", person.fullname);
            map.put("username", person.getName()); }

        return map;



    }

    @Get(value="/get-ifsc",produces = MediaType.APPLICATION_JSON_STREAM)
    public String ifsc() throws IOException, InvalidFormatException {
        String fileName = "src/main/data/68774.xlsx";


        try  {

            // Creating a Workbook from an Excel file (.xls or .xlsx)
            XSSFWorkbook workbook = new XSSFWorkbook(fileName);

            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            //initiate json object
            JSONObject jsonobj = new JSONObject();

            CellStyle style = sheet.getWorkbook().createCellStyle();

            for(int rownum = 1; rownum <= 10000; rownum++){
                XSSFRow row = sheet.getRow(rownum);
                if(row != null){
                    DataFormatter dataFormatter = new DataFormatter();
                    Cell cell = row.getCell(1);
                    String cellValue = dataFormatter.formatCellValue(cell);
                    jsonobj.put(rownum, cellValue); } }

            //write to file
            FileWriter file = new FileWriter("src/main/ifsc.json");
            file.write(jsonobj.toJSONString());
            file.close();


        }
        catch (Exception e)
        {
            System.out.println("Something went wrong");
            e.printStackTrace();
            return "Fail";
        }


        return "Success";
    }

    @Put("/person/delete")
    public Single<String> deletePerson(@Body Person person){
        return Single.just(personsRepository.remove(person)? "Successfully Deleted": "Failure");
    }

}







