package com.microsoft.showmyjvm.sparkjava;

import com.microsoft.showmyjvm.core.ShowJVM;

import static spark.Spark.get;
import static spark.Spark.port;
import spark.Route;

public class SparkStart {

    public static void main(String args[]) {
        port(8080);
        Route function = (req, res) -> {
            res.type("text/plain");
            return new ShowJVM().getJVMDetails();
        };
        get("/", function);
        get("/inspect", function);
    }

}
