package com.microsoft.showmyjvm.sparkjava;

import com.microsoft.showmyjvm.core.ShowJVM;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;


public class RatpackStart {

    public static void main(String args[]) throws Exception {
        RatpackServer.start(server -> {
            server.serverConfig(ServerConfig.embedded().port(8080));
            server.handlers(chain -> {
                chain.get("api/showjvm", ctx -> {
                    ctx.header("Content-type: text/plain");
                    ctx.render(new ShowJVM().getJVMDetails());
                });
            });
        });
    }

}
