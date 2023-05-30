package io.brunoborges.showmyjvm.azure;

import java.util.Optional;
import java.util.UUID;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import io.brunoborges.showmyjvm.core.ShowJVM;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    private static final UUID uuid = UUID.randomUUID();

    @FunctionName("inspect")
    public HttpResponseMessage showjvm(
        @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION)
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
