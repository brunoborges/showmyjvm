var builder = DistributedApplication.CreateBuilder(args);

// Add a containerized Tomcat application within an existing Docker image
builder.AddContainer("tomcat", "showmyjvm-tomcat:latest").WithHttpEndpoint(port: 8090, targetPort: 8080);
// All implementations have container images under showmyjvm-{name}:latest and are configured to port 8080 by default

// Add more applications here using different approaches
// What matters is that all applications expose the same HTTP endpoints
// All are configurable to run locally on port 8080 by default or with a custom env.PORT override
// Each uses a different technique for running locally

// Names of frameworks and how devs run them locally:

// helidon -> mvn exec:exec
// helidon-mp -> mvn exec:exec
// quarkus -> mvn quarkus:dev
// javalin -> mvn exec:java
// spring-boot -> mvn spring-boot:run
// micronaut -> mvn mn:run
// quarkus -> mvn quarkus:run
// ratpack -> mvn exec:java
// tomcat -> mvn cargo:run

builder.Build().Run();
