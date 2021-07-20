package example.micronaut.dao;

import example.micronaut.service.Bank;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class BankDao implements Dao{

    private List<Bank> banks = new ArrayList<>();

    private String formatCell(Cell cell){

            DataFormatter dataFormatter = new DataFormatter();

            String cellvalue = dataFormatter.formatCellValue(cell);

            return cellvalue;
    }

    public BankDao() throws IOException, ExecutionException, InterruptedException {

            String fileName = "src/main/data/68774.xlsx";
            int name_cell = 0;
            int id_cell = 1;
            int branch_cell = 3;

            XSSFWorkbook workbook = new XSSFWorkbook(fileName);

            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

            for(int rownum = 1; rownum <= 6; rownum++){
                XSSFRow row = sheet.getRow(rownum);
                if(row != null){
                    Cell id = row.getCell(id_cell);
                    Cell branch = row.getCell(branch_cell);
                    Cell name = row.getCell(name_cell);

                    // add to list
                    banks.add(new Bank(formatCell(id),formatCell(name),formatCell(branch)));

                }
            }

    }

    @Override
    public Object get(String id) {

        try {
            return banks.stream().filter(p -> p.getIfsc().equals(id)).findFirst().get();
        }
        catch (NoSuchElementException e) {
            return "None Present";
        }


    }
        @Override
        public List<Bank> getAll () {
            return banks;
        }

        @Override
        public void update (Bank bank){
            banks.add(bank);
        }

        @Override
        public void delete (String id){

        Bank bank = banks.stream().filter(p -> p.getIfsc().equals(id))
                .findFirst()
                .get();

        banks.remove(bank);

    }





}
