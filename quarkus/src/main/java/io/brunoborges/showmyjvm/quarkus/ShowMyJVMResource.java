package io.brunoborges.showmyjvm.quarkus;

import io.brunoborges.showmyjvm.core.JVMDetails;
import io.brunoborges.showmyjvm.core.ShowJVM;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/jvm")
public class ShowMyJVMResource {

    @GET
    @Path("/inspect")
    public String showmyjvm() {
        return new ShowJVM().dumpJVMDetails();
    }

    @GET
    @Path("/inspect.json")
    public JVMDetails jvmdetails() {
        return new ShowJVM().extractJVMDetails();
    }

}
