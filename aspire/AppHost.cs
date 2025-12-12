var builder = DistributedApplication.CreateBuilder(args);

// Add a containerized Tomcat application within an existing Docker image
builder.AddContainer("tomcat", "showmyjvm-tomcat:latest").WithHttpEndpoint(port: 8090, targetPort: 8080);
// All implementations have container images under showmyjvm-{name}:latest and are configured to port 8080 by default

// Add more applications here using different approaches for running locally
// All applications expose the same HTTP endpoints
// They are configurable to run locally on port 8080 by default or with a custom environment variable PORT to override

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

// It may also be possible to run using java -jar {jarfile}.jar if the framework supports it (meaning, it produces an executable jar)

// Services like e.g. Redis, databases, message brokers, etc. can also be added here if needed
// Each framework may have its own way of connecting to these services using environment variables or configuration files
// Some frameworks have configuration files that allow overriding using environment variables folllowing naming conventions within each framework
// For example, Spring Boot supports application.properties or application.yml files that can read environment variables using the ${ENV_VAR_NAME} syntax
// QUarkus also supports application.properties or application.yml files with similar syntax for environment variables
// But Quarkus and Spring use different naming conventions for environment variables to override specific configurations. 
// For example, Spring Boot uses SERVER_PORT to override the server port, while Quarkus uses QUARKUS_HTTP_PORT
// Other frameworks are very manual and depend on how developers coded them to read environment variables

builder.Build().Run();
