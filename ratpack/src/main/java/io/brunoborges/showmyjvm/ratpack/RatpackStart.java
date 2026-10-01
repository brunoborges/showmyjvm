package io.brunoborges.showmyjvm.ratpack;

import io.brunoborges.showmyjvm.core.JVMDetails;
import io.brunoborges.showmyjvm.core.ShowJVM;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import ratpack.handling.Context;
import ratpack.http.Headers;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.jackson.Jackson;

public class RatpackStart {

    public static void main(String args[]) throws Exception {
        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;

        RatpackServer.start(server -> {
            server.serverConfig(ServerConfig.embedded().port(port)).handlers(new RouterChain());
        });
    }

}

class RouterChain implements Action<Chain> {

    @Override
    public void execute(Chain chain) throws Exception {
        var webUi = new WebUiHandler();
        chain.path("jvm/inspect", new ShowMyJVMHandler()).path("jvm/inspect.json", new ShowMyJVMJsonHandler())
                .path("", webUi).path("index.html", webUi);
    }
}

/**
 * Serves the shared web UI (index.html) packaged in the showmyjvm-web-ui JAR.
 */
class WebUiHandler implements ratpack.handling.Handler {
    private static final String INDEX = "META-INF/resources/index.html";

    public void handle(Context ctx) throws Exception {
        try (var in = RatpackStart.class.getClassLoader().getResourceAsStream(INDEX)) {
            if (in == null) {
                ctx.clientError(404);
                return;
            }
            ctx.getResponse().contentType("text/html;charset=UTF-8").send(in.readAllBytes());
        }
    }
}

class ShowMyJVMHandler implements ratpack.handling.Handler {
    public void handle(Context ctx) {
        ctx.header("Content-type: text/plain");
        ctx.render(new ShowJVM().dumpJVMDetails());
    }
}

class ShowMyJVMJsonHandler implements ratpack.handling.Handler {
    public void handle(Context ctx) {
        JVMDetails details = new ShowJVM().extractJVMDetails();
        ctx.header("Content-type: application/json");
        ctx.render(Jackson.json(details));
    }
}