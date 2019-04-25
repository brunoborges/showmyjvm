package com.microsoft.showmyjvm.micronaut;

import com.microsoft.showmyjvm.core.ShowJVM;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/api/showjvm")
public class ShowController {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String showjvm() {
        return new ShowJVM().getJVMDetails();
    }

}
