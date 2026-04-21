# Copilot Instructions for ShowMyJVM

## Overview

ShowMyJVM is a sophisticated Maven multi-module Java project that demonstrates comprehensive JVM introspection across 9 different web frameworks. The core library provides deep runtime analysis capabilities, and each framework module implements standardized endpoints in a framework-specific way.

## Project Structure

- **bom/** - Bill of Materials module for centralized dependency management (Java 25, plugins)
- **core/** - Core JVM introspection library (JVMDetails, ShowJVM classes)
- **{framework}/** - Nine framework implementations (spring-boot, quarkus, micronaut, helidon, helidon-mp, javalin, ratpack, sparkjava, tomcat)
- **e2e-tests/** - Playwright test suite validating all implementations
- **serverless/** - Deprecated serverless implementations (Azure Functions, AWS Lambda)

Each framework module depends on `core` and implements the same two endpoints:
- `/jvm/inspect` - Returns JVM analysis as plain text
- `/jvm/inspect.json` - Returns JVM analysis as JSON

## Build & Test Commands

### Clean Build
```bash
./mvnw clean compile
```

### Full Build with Tests
```bash
./mvnw clean verify
```

### Build Single Module with Dependencies
```bash
./mvnw clean compile -pl spring-boot -am
```

### Run Tests for Core Module Only
```bash
./mvnw test -pl core
```

### Run Specific Test Class
```bash
./mvnw test -pl core -Dtest=JVMDetailsTest
```

### Run Single Test Method
```bash
./mvnw test -pl core -Dtest=JVMDetailsTest#testMemoryMetrics
```

### Verify with Dependency Analysis
```bash
./mvnw clean verify dependency:analyze
```

## Running Applications

All implementations support the `PORT` environment variable (default 8080).

### Quick Start Examples
```bash
# Spring Boot
mvn spring-boot:run -pl spring-boot

# Quarkus
mvn quarkus:run -pl quarkus

# Micronaut
mvn mn:run -pl micronaut

# Helidon SE
mvn exec:exec -pl helidon

# Helidon MP
mvn exec:exec -pl helidon-mp

# Javalin
mvn exec:exec -pl javalin

# Ratpack
mvn exec:exec -pl ratpack

# SparkJava
mvn exec:exec -pl sparkjava

# Tomcat
mvn cargo:run -pl tomcat
```

### Custom Port
```bash
PORT=9000 mvn spring-boot:run -pl spring-boot
PORT=3000 mvn cargo:run -pl tomcat
```

## E2E Testing

### Setup (First Time)
```bash
cd e2e-tests
npm install
npx playwright install
```

### Run All Tests Against Running Instance
```bash
cd e2e-tests
npm test
```

### Run Tests with UI (Interactive)
```bash
cd e2e-tests
npm run test:ui
```

### Auto-Test All Implementations
```bash
./test-all.sh
```

This script starts each implementation, runs tests, and stops the server automatically.

## Architecture Patterns

### Core Module (JVM Introspection)
The `core` module extracts JVM information using JMX and Java APIs:
- **JVMDetails.java** - Main facade class with static analysis methods
- **ShowJVM.java** - Comprehensive runtime property aggregation
- **IdentifyGC.java** - Garbage collector algorithm detection
- **PrintFlagsFinal.java** - JVM flag extraction with origins
- **MemoryPoolDetails.java** - Memory pool analysis

The core module is a pure library with no web framework dependencies.

### Framework Implementations
Each framework module:
1. Extends the `core` library with a dependency
2. Creates a controller/handler class (e.g., `JvmInspectController`)
3. Maps endpoints to JVM introspection methods
4. Handles content negotiation (text vs JSON)
5. Integrates framework-specific features (Spring Actuator, etc.)

Framework modules follow this naming pattern:
- `io.brunoborges.showmyjvm.{framework}` package
- Main entry point: `Application.java`
- Controller/Handler: `JvmInspect{Framework}Handler.java`

### Dependency Management
- Parent POM enforces Maven 3.9.1+
- BOM module manages all versions centrally
- Framework modules use `<relativePath>../bom</relativePath>` for parent
- All modules target Java 25 (maven.compiler.release property)

### Container Building
All framework modules use Jib plugin for container builds:
```bash
mvn clean package -DskipTests
# Creates images like: showmyjvm-spring-boot, showmyjvm-quarkus, etc.
```

## Key Conventions

### Port Configuration
All implementations must:
- Default to port 8080
- Respect the `PORT` environment variable
- Make endpoints available on the configured port

### Standardized Endpoints
All implementations provide:
- `/jvm/inspect` with `Content-Type: text/plain`
- `/jvm/inspect.json` with `Content-Type: application/json`

### Endpoint Output
- Plain text format: Multi-line formatted output with section headers
- JSON format: Structured object with keys like `runtime`, `memory`, `gc`, `threadDetails`, `environment`

### Module Artifacts
Framework module artifacts follow pattern: `showmyjvm-{framework}`
- Maven groupId: `io.brunoborges.showmyjvm`
- Version: Always `1.0.0-SNAPSHOT` (inherited from parent)

### Logging
- Use SLF4J for logging (`org.slf4j:slf4j-api`)
- Tests use slf4j-simple binding
- Avoid framework-specific logging APIs in core module

### Testing
- Use JUnit 5 (Jupiter) for unit tests
- Use Mockito for mocking if needed
- Test classes placed in `src/test/java` mirroring main structure
- E2E tests validate both endpoints and content types across all implementations

## Common Development Tasks

### Adding a New Framework Implementation
1. Create new module directory: `{framework}/`
2. Create `pom.xml` with parent pointing to `../bom`
3. Add dependency: `showmyjvm-core`
4. Add framework dependencies from BOM
5. Create package: `io.brunoborges.showmyjvm.{framework}`
6. Implement controller/handler with both endpoints
7. Implement `Application.java` entry point
8. Add module to parent `pom.xml` `<modules>` section
9. Verify E2E tests pass

### Modifying Core JVM Introspection
When changing `core` module:
1. Update relevant class (JVMDetails, ShowJVM, etc.)
2. Add/update unit tests in `core/src/test/java`
3. Run: `./mvnw test -pl core`
4. Run E2E tests to ensure all frameworks still work
5. Update endpoint response format in all framework implementations if output format changes

### Updating Dependency Versions
1. Edit `bom/pom.xml` dependencyManagement section
2. All modules inherit via parent relationship
3. Verify with: `./mvnw versions:display-dependency-updates`

## JSON Response Format

Framework implementations should produce consistent JSON structures. Example:
```json
{
  "runtime": { "vmName": "...", "version": "...", ... },
  "memory": { "heapUsed": "...", "nonHeapUsed": "...", ... },
  "gc": { "algorithms": [...], "collectors": [...] },
  "threadDetails": { "count": ..., "peakCount": ..., ... },
  "environment": { "systemProperties": {...}, "variables": {...} }
}
```

Text format should mirror this structure with clear section headers (🖥️ **Runtime Properties**, 💾 **Memory Settings**, etc.).

## Notes for Contributors

- Java 25 is required; use Maven Release = 25 configuration
- Each framework module should be independently runnable
- Port environment variable must be respected, not hardcoded
- E2E tests verify both endpoints against running instances
- The `test-all.sh` script is the source of truth for testing all implementations
- Containerization uses Jib (no Dockerfile required, though some modules have `docker/` dirs)
