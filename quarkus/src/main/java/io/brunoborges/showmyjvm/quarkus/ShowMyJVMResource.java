package io.brunoborges.showmyjvm.quarkus;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jakarta.rs.annotation.JacksonFeatures;
import io.brunoborges.showmyjvm.core.JVMDetails;
import io.brunoborges.showmyjvm.core.ShowJVM;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/jvm")
public class ShowMyJVMResource {

    @GET
    @Path("/inspect")
    @Produces(MediaType.TEXT_PLAIN)
    public String showmyjvm() {
        return new ShowJVM().dumpJVMDetails();
    }

    @GET
    @Path("/inspect.json")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public JVMDetails jvmdetails() {
        return new ShowJVM().extractJVMDetails();
    }

}