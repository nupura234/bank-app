package example.micronaut.service;

import java.io.IOException;

public interface ExcelService {

    Object getSheet() throws IOException;

}
