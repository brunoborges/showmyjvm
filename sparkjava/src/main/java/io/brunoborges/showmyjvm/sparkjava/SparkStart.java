package io.brunoborges.showmyjvm.sparkjava;

import io.brunoborges.showmyjvm.core.ShowJVM;

import static spark.Spark.get;
import static spark.Spark.port;
import spark.Route;

public class SparkStart {

    public static void main(String args[]) {
        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
        port(port);
        Route function = (req, res) -> {
            res.type("text/plain");
            return new ShowJVM().dumpJVMDetails();
        };
        get("/jvm/inspect", function);
        get("/jvm/inspect.json", (req, res) -> {
            res.type("application/json");
            return new ShowJVM().extractJVMDetails();
        });
    }

}
