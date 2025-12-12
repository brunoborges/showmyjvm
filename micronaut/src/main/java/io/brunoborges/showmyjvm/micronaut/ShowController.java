package io.brunoborges.showmyjvm.micronaut;

import io.brunoborges.showmyjvm.core.JVMDetails;
import io.brunoborges.showmyjvm.core.ShowJVM;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/jvm")
public class ShowController {

    @Get(uris = { "/inspect" })
    @Produces(MediaType.TEXT_PLAIN)
    public String showmyjvm() {
        return new ShowJVM().dumpJVMDetails();
    }

    @Get(uris = { "/inspect.json" })
    @Produces(MediaType.APPLICATION_JSON)
    public JVMDetails jvmdetails() {
        return new ShowJVM().extractJVMDetails();
    }

}