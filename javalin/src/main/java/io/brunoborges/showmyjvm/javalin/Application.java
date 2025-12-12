package io.brunoborges.showmyjvm.javalin;

import io.brunoborges.showmyjvm.core.ShowJVM;
import io.javalin.Javalin;

public class Application {

    public static void main(String args[]) throws Exception {
        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
        
        var app = Javalin.create();
        app.get("/jvm/inspect", ctx -> {
            ctx.contentType("text/plain");
            ctx.result(new ShowJVM().dumpJVMDetails());
        });
        app.get("/jvm/inspect.json", ctx -> {
            ctx.contentType("application/json");
            ctx.json(new ShowJVM().extractJVMDetails());
        });
        app.start(port);
    }

}
