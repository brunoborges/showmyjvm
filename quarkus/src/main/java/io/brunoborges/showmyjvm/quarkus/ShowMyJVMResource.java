package io.brunoborges.showmyjvm.quarkus;

import io.brunoborges.showmyjvm.core.ShowJVM;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/inspect")
@Produces(MediaType.TEXT_PLAIN)
public class ShowMyJVMResource {

    @GET
    public String showmyjvm() {
        return new ShowJVM().dumpJVMDetails();
    }

}