package io.brunoborges.showmyjvm.sparkjava;

import io.brunoborges.showmyjvm.core.ShowJVM;

import static spark.Spark.get;
import static spark.Spark.port;
import spark.Route;

public class SparkStart {

    public static void main(String args[]) {
        port(8080);
        Route function = (req, res) -> {
            res.type("text/plain");
            return new ShowJVM().dumpJVMDetails();
        };
        get("/", function);
        get("/inspect", function);
        get("/jvminfo.json", (req, res) -> {
            res.type("application/json");
            return new ShowJVM().dumpJVMDetails();
        });
    }

}
