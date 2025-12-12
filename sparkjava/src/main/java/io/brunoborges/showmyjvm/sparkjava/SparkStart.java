package io.brunoborges.showmyjvm.sparkjava;

import static spark.Spark.get;
import static spark.Spark.port;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.brunoborges.showmyjvm.core.ShowJVM;

public class SparkStart {

    public static void main(String args[]) {
        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
        port(port);

        get("/jvm/inspect", (req, res) -> {
            res.type("text/plain");
            return new ShowJVM().dumpJVMDetails();
        });

        get("/jvm/inspect.json", (req, res) -> {
            res.type("application/json");
            var objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(new ShowJVM().extractJVMDetails());
        });
    }

}
