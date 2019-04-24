package com.microsoft.showmyjvm.azurefunction;

public class Function implements RequestHandler<Integer, String> {

    private static final UUID uuid = UUID.randomUUID();

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
