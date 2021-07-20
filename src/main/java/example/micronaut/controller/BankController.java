package example.micronaut.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import example.micronaut.errors.CustomErrors;
import example.micronaut.errors.NotFoundErrorResponse;
import example.micronaut.service.Bank;
import example.micronaut.service.BankService;
import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Optional;


@Controller("/api")
public class BankController {

    private MessageSource messageSource;


    @Inject
    private BankService<Object> bankService;

    @Inject
    private CustomErrors customErrors;

    @Get(value = "/get-bank/{id}", produces = MediaType.APPLICATION_JSON)
    public Object getBank(@NotBlank String id) throws Exception {
         return bankService.getBank(id);
    }


    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public Object notFound(HttpRequest request) {
        return HttpResponse.notFound(new NotFoundErrorResponse(System.currentTimeMillis()));

    }


    @Get(value = "/get-banks/", produces = MediaType.APPLICATION_JSON)
    public Object getIFSC() {
        return bankService.getALLIFSC();
    }


    @Post(value = "/add-bank", consumes = MediaType.APPLICATION_JSON)
    public void save(@Body @NotBlank Bank bank) {
        bankService.update(bank);
    }

    @Post(value = "/delete-bank", consumes = MediaType.APPLICATION_JSON)
    public void delete(@Body @NotBlank Bank bank) throws IOException {
        bankService.delete(bank);

    }


}
