# ShowMyJVM E2E Tests

End-to-end tests for all ShowMyJVM framework implementations using Playwright.

## Prerequisites

- Node.js 18+ installed
- One of the ShowMyJVM implementations running on port 8080

## Setup

```bash
npm install
npx playwright install
```

## Running Tests

### Test a specific implementation

First, start the implementation you want to test:

```bash
# In terminal 1: Start an implementation
mvn cargo:run -pl tomcat
```

Then run the tests for that specific implementation:

```bash
# In terminal 2: Run tests
npm run test:tomcat
```

### Available test commands

```bash
npm run test:tomcat        # Test Tomcat implementation
npm run test:spring-boot   # Test Spring Boot implementation
npm run test:quarkus       # Test Quarkus implementation
npm run test:micronaut     # Test Micronaut implementation
npm run test:helidon       # Test Helidon SE implementation
npm run test:helidon-mp    # Test Helidon MP implementation
npm run test:javalin       # Test Javalin implementation
npm run test:ratpack       # Test Ratpack implementation
npm run test:sparkjava     # Test SparkJava implementation
```

### Test all implementations automatically

A convenient script is provided to test all implementations sequentially:

```bash
./test-all.sh
```

This script will:
1. Start each implementation on port 8080
2. Run the tests for that implementation
3. Stop the implementation
4. Move to the next one
5. Provide a summary of results

This is useful for CI/CD pipelines or comprehensive local testing.

### Run tests with UI mode
```bash
npm run test:ui            # Interactive UI mode
```

### Run tests in headed mode (see browser)
```bash
npm run test:headed        # See browser execution
```

## Testing Each Implementation

Each implementation should be started individually on port 8080 before running its tests:

### Tomcat
```bash
mvn cargo:run -pl tomcat
```

### Spring Boot
```bash
mvn spring-boot:run -pl spring-boot
```

### Quarkus
```bash
mvn quarkus:run -pl quarkus
```

### Micronaut
```bash
mvn mn:run -pl micronaut
```

### Helidon SE
```bash
mvn exec:java -pl helidon
```

### Helidon MP
```bash
mvn exec:java -pl helidon-mp
```

### Javalin
```bash
mvn exec:java -pl javalin
```

### Ratpack
```bash
mvn exec:java -pl ratpack
```

### SparkJava
```bash
mvn exec:java -pl sparkjava
```

## Test Coverage

The tests verify:

1. **GET /jvm/inspect** - Returns plain text with JVM details
2. **GET /jvm/inspect.json** - Returns JSON with JVM details
3. **Data consistency** - Both endpoints return the same information

## Expected Behavior

All implementations must:
- Run on port 8080
- Respond with 200 OK status
- Return correct content types (text/plain and application/json)
- Include JVM information (version, vendor, OS details)
- Have consistent data between both endpoints
