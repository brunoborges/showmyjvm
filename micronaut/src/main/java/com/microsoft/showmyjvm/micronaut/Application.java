package com.microsoft.showmyjvm.micronaut;

import io.micronaut.runtime.Micronaut;
import com.microsoft.showmyjvm.core.ShowJVM;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}

@Controller
class ShowController {

    @Get(uris = {"/", "/inspect"})
    @Produces(MediaType.TEXT_PLAIN)
    public String showjvm() {
        return new ShowJVM().getJVMDetails();
    }

}
