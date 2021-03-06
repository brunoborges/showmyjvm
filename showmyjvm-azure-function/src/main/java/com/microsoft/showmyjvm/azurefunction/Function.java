package com.microsoft.showmyjvm.azurefunction;

import java.util.*;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.showmyjvm.core.*;

public class Function {

    private static final UUID uuid = UUID.randomUUID();

    @FunctionName("showjvm")
    public HttpResponseMessage showjvm(
        @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS)
        HttpRequestMessage<Optional<String>> request,
        ExecutionContext context) {

        context.getLogger().info("Request in. Returning JVM details...");

        return request.createResponseBuilder(HttpStatus.OK)
            .body(new ShowJVM().getJVMDetails())
            .header("Content-Type", "text/plain")
            .header("X-UUID", uuid.toString())
            .build();
    }

}
