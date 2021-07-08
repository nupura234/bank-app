package example.micronaut.dao;

import example.micronaut.service.Bank;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.*;

public class BankDao implements Dao{

    private List<Bank> banks = new ArrayList<>();

    public String formatCell(Cell cell){

            DataFormatter dataFormatter = new DataFormatter();

            String cellvalue = dataFormatter.formatCellValue(cell);

            return cellvalue;
    }

    public BankDao() throws IOException {

        String fileName = "src/main/data/68774.xlsx";
        int name_cell = 0;
        int id_cell = 1;
        int branch_cell = 3;

        XSSFWorkbook workbook = new XSSFWorkbook(fileName);

        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

        for(int rownum = 1; rownum <= 10000; rownum++){
            XSSFRow row = sheet.getRow(rownum);
            if(row != null){
                Cell id = row.getCell(id_cell);
                Cell branch = row.getCell(branch_cell);
                Cell name = row.getCell(name_cell);
                banks.add(new Bank(formatCell(id),formatCell(name),formatCell(branch)));

            }
        }

    }

    @Override
    public Object get(String id) {

        Iterator iterator = banks.iterator();
        while(iterator.hasNext()) {

            Bank obj = (Bank) iterator.next();
            if(obj.getId().equals(id)) {
                return obj; } }

        return "None";

    }

    @Override
    public List<Bank> getAll() {
        return banks;
    }

    @Override
    public void update(Bank bank) {
           banks.add(bank);
    }

    @Override
    public void delete(String id) {

        Iterator iterator = banks.iterator();
        while(iterator.hasNext()) {

            Bank obj = (Bank) iterator.next();

            if(obj.getId().equals(id)) {
                iterator.remove(); } }
    }


}
