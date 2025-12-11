package io.helidon.examples.quickstart.se;

import io.brunoborges.showmyjvm.core.JVMDetails;
import io.brunoborges.showmyjvm.core.ShowJVM;
import io.helidon.http.HeaderNames;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

public class ShowMyJVMService implements HttpService {

    @Override
    public void routing(HttpRules rules) {
        rules.get("/inspect", this::jvmDetailsDumpHandler);
        rules.get("/inspect.json", this::jvmDetailsJSONHandler);
    }

    private void jvmDetailsDumpHandler(ServerRequest request, ServerResponse response) {
        String jvmDetails = new ShowJVM().dumpJVMDetails();
        response.headers().add(HeaderNames.CONTENT_TYPE, "text/plain");
        response.send(jvmDetails);
    }

    private void jvmDetailsJSONHandler(ServerRequest request, ServerResponse response) {
        JVMDetails jvmDetails = new ShowJVM().extractJVMDetails();
        response.headers().add(HeaderNames.CONTENT_TYPE, "application/json");
        response.send(jvmDetails);
    }

}
