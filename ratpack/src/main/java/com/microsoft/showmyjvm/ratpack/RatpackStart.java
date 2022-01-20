package com.microsoft.showmyjvm.ratpack;

import com.microsoft.showmyjvm.core.ShowJVM;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import ratpack.handling.Handler;
import ratpack.handling.Context;
import ratpack.func.Action;
import ratpack.handling.Chain;


public class RatpackStart {

    public static void main(String args[]) throws Exception {
        RatpackServer.start(server -> {
            server.serverConfig(ServerConfig.embedded().port(8080));
            server.handlers(new RouterChain());
        });
    }

}

class RouterChain implements Action<Chain> {
    private final Handler rootPathHandler = new ShowMyJVMHandler();
    private final Handler inspectPathHandler = new ShowMyJVMHandler();

    @Override
    public void execute(Chain chain) throws Exception {
        chain.path("", rootPathHandler);
        chain.path("inspect", inspectPathHandler);
    }
}

class ShowMyJVMHandler implements ratpack.handling.Handler {
    public void handle(Context ctx) {
        ctx.header("Content-type: text/plain");
        ctx.render(new ShowJVM().getJVMDetails());
    }
}