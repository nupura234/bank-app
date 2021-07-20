package example.micronaut.errors;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.micronaut.http.annotation.Produces;

import javax.inject.Singleton;


@Produces
@Singleton
public class NotFoundErrorResponse implements CustomErrors {

    private String message;
    private int errorCode;
    private long time;


    public NotFoundErrorResponse(long time) {
        this.message = "No record found";
        this.errorCode = 404;
        this.time = time;
    }


}
