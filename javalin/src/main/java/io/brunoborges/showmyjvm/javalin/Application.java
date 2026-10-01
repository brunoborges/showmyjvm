package io.brunoborges.showmyjvm.javalin;

import io.brunoborges.showmyjvm.core.ShowJVM;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.json.JavalinJackson3;

public class Application {

    public static void main(String args[]) throws Exception {
        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;

        var app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson3());
            config.staticFiles.add("/META-INF/resources", Location.CLASSPATH);
            config.routes.get("/jvm/inspect", ctx -> {
                ctx.contentType("text/plain");
                ctx.result(new ShowJVM().dumpJVMDetails());
            });
            config.routes.get("/jvm/inspect.json", ctx -> {
                ctx.contentType("application/json");
                ctx.json(new ShowJVM().extractJVMDetails());
            });
        });
        app.start(port);
    }

}
