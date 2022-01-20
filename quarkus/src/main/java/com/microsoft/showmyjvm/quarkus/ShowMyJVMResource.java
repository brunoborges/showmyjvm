package com.microsoft.showmyjvm.quarkus;

import com.microsoft.showmyjvm.core.ShowJVM;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/inspect")
@Produces(MediaType.TEXT_PLAIN)
public class ShowMyJVMResource {

    @GET
    public String showmyjvm() {
        return new ShowJVM().getJVMDetails();
    }

}