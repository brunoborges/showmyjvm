
package io.helidon.examples.quickstart.se;

import io.helidon.http.media.MediaContextConfig.BuilderBase;
import io.helidon.http.media.jsonb.JsonbSupport;
import io.helidon.logging.common.LogConfig;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;

public class Main {

    public static void main(String[] args) {
        LogConfig.configureRuntime();

        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;

        var server = WebServer.builder()
                .port(port)
                .mediaContext(Main::mediaSupport).routing(Main::routing).build().start();

        System.out.println("WEB server is up! http://localhost:" + server.port() + "/");
    }

    @SuppressWarnings("rawtypes")
    static void mediaSupport(BuilderBase media) {
        media.mediaSupportsDiscoverServices(false);
        media.addMediaSupport(JsonbSupport.create());
    }

    static void routing(HttpRouting.Builder routing) {
        routing.register("/jvm", new ShowMyJVMService());
    }
}