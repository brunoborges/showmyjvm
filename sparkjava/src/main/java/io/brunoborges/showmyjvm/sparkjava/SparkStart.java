package io.brunoborges.showmyjvm.sparkjava;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.halt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import spark.Request;
import spark.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.brunoborges.showmyjvm.core.ShowJVM;

public class SparkStart {

    public static void main(String args[]) {
        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
        port(port);

        // Spark's static file handler can't serve resources packaged inside JARs, so serve the shared UI explicitly.
        get("/", SparkStart::webUi);
        get("/index.html", SparkStart::webUi);

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

    private static Object webUi(Request req, Response res) throws IOException {
        try (var in = SparkStart.class.getClassLoader().getResourceAsStream("META-INF/resources/index.html")) {
            if (in == null) {
                throw halt(404);
            }
            res.type("text/html;charset=UTF-8");
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

}
