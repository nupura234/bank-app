package example.micronaut.service;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import com.google.cloud.firestore.annotation.PropertyName;
import org.apache.poi.xssf.usermodel.XSSFRow;

@IgnoreExtraProperties
public class Bank {

    @DocumentId
    private String ifsc;
    private String name;
    private String branch;


    public Bank(){ }

    public Bank(XSSFRow row){
        int name_cell = 0;
        int id_cell = 1;
        int branch_cell = 3;


        this.name = row.getCell(name_cell).getStringCellValue();
        this.ifsc = row.getCell(id_cell).getStringCellValue();
        this.branch = row.getCell(branch_cell).getStringCellValue();


    }


    public Bank(String id, String name, String branch){
        this.ifsc = id;
        this.name = name;
        this.branch = branch;

    }

    @PropertyName("i")
    public String getIfsc(){
        return ifsc;
    }

    @PropertyName("n")
    public String getName(){
        return name;
    }

    @PropertyName("b")
    public String getBranch(){
        return branch;
    }

    @PropertyName("n")
    public void setName(String name) {
        this.name = name;
    }


    @PropertyName("i")
    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    @PropertyName("b")
    public void setBranch(String branch) {
        this.branch = branch;
    }


}
