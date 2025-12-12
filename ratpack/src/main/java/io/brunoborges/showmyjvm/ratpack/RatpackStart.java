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
        chain.path("jvm/inspect", new ShowMyJVMHandler()).path("jvm/inspect.json", new ShowMyJVMJsonHandler());
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