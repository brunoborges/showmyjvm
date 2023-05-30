package io.brunoborges.showmyjvm.micronaut;

import io.brunoborges.showmyjvm.core.ShowJVM;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}

@Controller
class ShowController {

    @Get(uris = {"/", "/inspect"})
    @Produces(MediaType.TEXT_PLAIN)
    public String showmyjvm() {
        return new ShowJVM().dumpJVMDetails();
    }

}
