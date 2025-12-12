package io.brunoborges.showmyjvm.ratpack;

import io.brunoborges.showmyjvm.core.JVMDetails;
import io.brunoborges.showmyjvm.core.ShowJVM;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import ratpack.handling.Handler;
import ratpack.handling.Context;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.jackson.Jackson;

public class RatpackStart {

    public static void main(String args[]) throws Exception {
        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
        
        RatpackServer.start(server -> {
            server.serverConfig(ServerConfig.embedded().port(port));
            server.handlers(new RouterChain());
        });
    }

}

class RouterChain implements Action<Chain> {

    private Handler inspectPathHandler = new ShowMyJVMHandler();
    private Handler inspectJsonHandler = new ShowMyJVMJsonHandler();

    @Override
    public void execute(Chain chain) throws Exception {
        chain.path("/jvm/inspect", inspectPathHandler);
        chain.path("/jvm/inspect.json", inspectJsonHandler);
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
        ctx.render(Jackson.json(details));
    }
}