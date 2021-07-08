package example.micronaut.controller;

import example.micronaut.service.Bank;
import example.micronaut.service.BankService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;


@Introspected
@Controller("/api")
public class BankController {


    @Inject
    private BankService bankService;


    @Get(value = "/get-bank/{id}", produces = MediaType.APPLICATION_JSON_STREAM)
    public HttpResponse getBank(@NotBlank String id) throws IOException {
         return HttpResponse.ok(bankService.getBank(id));
    }

    @Get(value = "/get-banks/", produces = MediaType.APPLICATION_JSON_STREAM)
    public HttpResponse getIFSC() throws IOException {
        return HttpResponse.ok(bankService.getALLIFSC());
    }


    @Post(value = "/add-bank")
    public HttpResponse<Bank> save(@Body @NotBlank Bank bank) throws IOException {
        bankService.update(bank);
        return HttpResponse.created(bank);
    }

    @Post(value = "/delete-bank", consumes = MediaType.TEXT_PLAIN)
    public HttpResponse<Bank> delete(@Body @NotNull String id) throws IOException {
        bankService.delete(id);
        return HttpResponse.ok();
    }


}
