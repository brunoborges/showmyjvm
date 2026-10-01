# Show My JVM

A comprehensive Java project that demonstrates detailed JVM (Java Virtual Machine) introspection and analysis across multiple modern web frameworks. This project showcases the power of JVM runtime analysis using Java 25 features and contemporary deployment architectures.

## 🎯 Overview

Show My JVM provides a sophisticated core library that extracts comprehensive JVM runtime information and exposes it through multiple web frameworks. The project demonstrates how the same core functionality can be deployed across different architectures, from traditional web servers to modern reactive frameworks, all with standardized endpoints and comprehensive end-to-end testing.

**Key Features:**

- 🔍 **Deep JVM Introspection** - Comprehensive runtime analysis including memory, threading, GC, and performance metrics
- 🌐 **Multi-Framework Support** - 9 different web frameworks with standardized APIs
- 🔄 **Standardized Endpoints** - Consistent `/jvm/inspect` and `/jvm/inspect.json` across all implementations
- 🧪 **E2E Testing** - Comprehensive Playwright test suite covering all implementations
- 🏗️ **Modern Architecture** - Maven multi-module project with centralized dependency management
- 📊 **Rich Output Formats** - Both human-readable text and structured JSON outputs
- 🚀 **Performance Focused** - Optimized for Java 25 with advanced JVM flag introspection
- 🐳 **Container Ready** - All implementations include Jib configuration for containerization
- 🌍 **PORT Environment Variable** - All implementations support dynamic port configuration

## 🏛️ Project Architecture

This is a sophisticated Maven multi-module project with the following structure:

```
showmyjvm/
├── 📋 bom/                    # Bill of Materials (dependency management)
├── 🧠 core/                   # Core JVM introspection library
├── 🖼️ web-ui/                 # Shared web interface (index.html) reused by all frameworks
├── 🌸 spring-boot/           # Spring Boot web application
├── ⚡ sparkjava/             # SparkJava lightweight web framework
├── 🔬 micronaut/             # Micronaut framework implementation
├── 🏃 quarkus/              # Quarkus native-ready framework
├── 📡 ratpack/              # Ratpack reactive web framework
├── ☕ javalin/              # Javalin simple web framework
├── 🌊 helidon/              # Helidon SE (reactive)
├── 🌊 helidon-mp/           # Helidon MP (MicroProfile)
├── 🐱 tomcat/               # Apache Tomcat servlet implementation
├── 🧪 e2e-tests/            # Playwright end-to-end test suite
└── 📁 serverless/           # Serverless implementations (deprecated, moved here)
```

## 🔬 Core Functionality

The `core` module provides comprehensive JVM introspection capabilities using Java 25 features:

### 🎛️ JVM Information Extracted

- **Runtime Properties**: VM name, version, vendor, PID, input arguments
- **Memory Analysis**: Heap and non-heap memory usage, memory pools, GC pressure
- **Class Loading**: Total loaded/unloaded classes, current loaded classes
- **Compilation**: JIT compiler details, compilation time analysis
- **Threading**: Thread count, peak threads, total started threads, CPU time
- **Garbage Collection**: GC algorithm identification (G1GC, ZGC, Parallel, etc.)
- **Operating System**: OS details, CPU usage, system load, physical memory
- **JVM Flags**: Advanced flags extraction with origins and writability
- **Environment**: Complete system properties and environment variables

## 🌐 Web Framework Implementations

All framework modules provide **standardized endpoints** and run on **port 8080** (configurable via PORT environment variable):

| Framework          | Key Features                                                  |
| ------------------ | ------------------------------------------------------------- |
| 🌸 **Spring Boot** | Actuator integration, Jackson pretty printing                 |
| ⚡ **SparkJava**   | Ultra-lightweight, minimal dependencies, fast startup         |
| 🔬 **Micronaut**   | Compile-time DI, reflection-free, native-ready                |
| 🏃 **Quarkus**     | Supersonic subatomic Java, Kubernetes-native, GraalVM support |
| 📡 **Ratpack**     | Reactive programming model, asynchronous non-blocking         |
| ☕ **Javalin**     | Simple and lightweight, easy to use                           |
| 🌊 **Helidon SE**  | Reactive microservices framework                              |
| 🌊 **Helidon MP**  | MicroProfile implementation                                   |
| 🐱 **Tomcat**      | Traditional servlet-based (Tomcat 11, Jakarta EE 11)          |

### Standard Endpoints

All implementations support:

- **`/jvm/inspect`** - Returns JVM details in plain text format (`text/plain`)
- **`/jvm/inspect.json`** - Returns JVM details in JSON format (`application/json`)
- **`/`** and **`/index.html`** - Shared web dashboard that calls `/jvm/inspect.json` from the browser

### Shared Web UI

The web interface lives in a single place: the `web-ui` module. It is a resource-only JAR that packages
`index.html` under `META-INF/resources/` (the Servlet 3.0+ resource JAR / WebJars convention), and every
framework module simply depends on `showmyjvm-web-ui`. Serving it requires little or no code:

| Framework              | How `META-INF/resources/index.html` is served                                  |
| ---------------------- | ------------------------------------------------------------------------------ |
| Spring Boot, Quarkus, Tomcat | Automatically (built-in support for `META-INF/resources` on the classpath) |
| Micronaut              | `micronaut.router.static-resources` in `application.yml`                       |
| Helidon SE             | `StaticContentFeature` classpath service registered in `Main`                  |
| Helidon MP             | `server.features.static-content` in `application.yaml`                         |
| Javalin                | `config.staticFiles.add("/META-INF/resources", Location.CLASSPATH)`            |
| Ratpack, SparkJava     | A small handler that streams the classpath resource                            |

The page uses relative URLs, so it also works when deployed under a servlet context path.

### Port Configuration

All implementations:

- Default to **port 8080**
- Support the **`PORT` environment variable** for dynamic configuration
- Example: `PORT=9000 mvn spring-boot:run -pl spring-boot`

## 🧪 End-to-End Testing

The project includes a comprehensive **Playwright** test suite that validates all implementations:

### Test Coverage

- ✅ Endpoint availability (both `/jvm/inspect` and `/jvm/inspect.json`)
- ✅ Shared web UI (`/index.html`) renders data fetched from the REST API
- ✅ Content-Type headers validation
- ✅ Response format verification
- ✅ Data consistency between text and JSON outputs
- ✅ JVM information presence and accuracy

### Running E2E Tests

```bash
# Install dependencies (first time only)
cd e2e-tests
npm install
npx playwright install

# Run all tests (requires a showmyjvm implementation to be running)
npm test

# Run tests with UI
npm run test:ui
```

The `test-all.sh` script automatically starts each implementation, runs the tests, and stops the server.

## 📁 Serverless Implementations (Deprecated)

Serverless implementations have been moved to the `/serverless` directory and are no longer actively maintained:

- Azure Functions (Java 21)
- AWS Lambda

These implementations are preserved for reference but not included in the main build.

## 🛠️ Prerequisites

- **Java**: 25 - _Required for all modules_
- **Maven**: 3.9.1 or higher (enforced by project)
- **Node.js**: 18+ (for E2E tests)
- **Operating System**: Any (macOS, Linux, Windows)
- **Memory**: Minimum 2GB RAM for building all modules
- **Docker**: Optional, for containerization features

## 🔨 Building the Project

### 📦 Build All Modules

```bash
# Clean build of entire project
./mvnw clean compile

# Build and run all tests
./mvnw clean verify

# Build with dependency analysis
./mvnw clean compile dependency:analyze
```

### 🎯 Build Specific Module

```bash
# Build Spring Boot module with dependencies
./mvnw clean compile -pl spring-boot -am

# Build and package Quarkus for native compilation
./mvnw clean package -pl quarkus -am -Pnative
```

### 📊 Generate Reports

```bash
# Generate dependency update reports
./mvnw versions:display-dependency-updates

# Generate security analysis
./mvnw org.owasp:dependency-check-maven:check
```

## 🚀 Running the Applications

All implementations support the **PORT environment variable** for dynamic port configuration.

### Quick Start Commands

| Framework          | Command                               |
| ------------------ | ------------------------------------- |
| 🌸 **Spring Boot** | `mvn spring-boot:run -pl spring-boot` |
| 🏃 **Quarkus**     | `mvn quarkus:run -pl quarkus`         |
| 🔬 **Micronaut**   | `mvn mn:run -pl micronaut`            |
| 🌊 **Helidon SE**  | `mvn exec:exec -pl helidon`           |
| 🌊 **Helidon MP**  | `mvn exec:exec -pl helidon-mp`        |
| ☕ **Javalin**     | `mvn exec:exec -pl javalin`           |
| 📡 **Ratpack**     | `mvn exec:exec -pl ratpack`           |
| ⚡ **SparkJava**   | `mvn exec:exec -pl sparkjava`         |
| 🐱 **Tomcat**      | `mvn cargo:run -pl tomcat`            |

### With Custom Port

```bash
# Run on port 9000
PORT=9000 mvn spring-boot:run -pl spring-boot

# Run Tomcat on port 3000
PORT=3000 mvn package cargo:run -pl tomcat
```

```bash
PORT=9966 mvn package exec:exec -pl ratpack
```

## 🧪 Testing the Applications

Once any web application is running, test using the **standardized endpoints**:

```bash
# Plain text output
curl http://localhost:8080/jvm/inspect

# JSON output
curl http://localhost:8080/jvm/inspect.json | jq '.'

# With custom port
PORT=9000 mvn spring-boot:run -pl spring-boot
curl http://localhost:9000/jvm/inspect
```

### Running End-to-End Tests

```bash
# Start an implementation first
mvn spring-boot:run -pl spring-boot

# In another terminal, run tests
cd e2e-tests
npm install  # First time only
npm test
```

To test all implementations automatically, ensure no other instances are running.
Then, use the provided script:

```bash
# Test all implementations automatically
./test-all.sh
```

### 📋 Sample Output Sections

The JVM analysis includes comprehensive sections:

- 🖥️ **Runtime Properties** (VM details, PID, launch arguments)
- 🧮 **Memory Settings** (heap, non-heap, memory pools)
- 📚 **Loaded Classes** (counts, compilation statistics)
- 🔄 **Thread Details** (active, peak, CPU utilization)
- ⚡ **CPU Usage** (load average, system memory analysis)
- 🗑️ **Garbage Collectors** (algorithm identification, performance metrics)
- 🏁 **JVM Final Flags** (all runtime flags with origins)
- 🌍 **Environment** (system properties, environment variables)

## 🐳 Containerization

All web framework modules include Jib configuration for containerization:

```bash
# Build Docker images for all implementations
mvn clean package -DskipTests

# Build specific implementation
mvn clean package -pl spring-boot

# Run containerized application with custom port
docker run -p 9000:8080 -e PORT=8080 showmyjvm-spring-boot

# All available images:
# - showmyjvm-spring-boot
# - showmyjvm-quarkus
# - showmyjvm-micronaut
# - showmyjvm-helidon
# - showmyjvm-helidon-mp
# - showmyjvm-javalin
# - showmyjvm-ratpack
# - showmyjvm-sparkjava
# - showmyjvm-tomcat
```

## ⭐ Key Features & Benefits

### 🏗️ **Standardized Multi-Framework Architecture**

- Demonstrates identical core functionality across 9 modern web frameworks
- **Standardized endpoints** (`/jvm/inspect` and `/jvm/inspect.json`) across all implementations
- **Consistent PORT environment variable** support for dynamic configuration
- Framework-specific optimizations and JSON handling

### 🧪 **Comprehensive Testing**

- **Playwright E2E test suite** covering all 9 implementations
- Automated testing with `test-all.sh` script
- Validates endpoint availability, content types, and data consistency

### 🐳 **Container-Ready**

- **Jib integration** for all implementations
- Optimized Docker image builds without Docker daemon
- Ready for Kubernetes, Azure Container Instances, and other container platforms

### 🔍 **Advanced JVM Analysis**

- Deep runtime introspection using Java Management Extensions (JMX)
- Sophisticated garbage collector identification and performance analysis
- Comprehensive flag extraction with proper module system access

### 👩‍💻 **Developer Experience**

- Maven reactor builds for efficient development workflow
- Centralized dependency management via Bill of Materials (BOM)
- PORT environment variable support across all implementations
- Comprehensive documentation and testing infrastructure

## 🏗️ Maven Multi-Module Architecture

The project demonstrates sophisticated Maven practices:

- **🏠 Parent POM**: Coordinates all modules with Maven 3.9.1+ enforcement
- **📋 BOM Module**: Centralizes dependency versions and plugin configurations (Java 25)
- **⚙️ Reactor Builds**: Ensures proper dependency resolution across modules
- **🎯 Focused Modules**: Each framework optimized for its specific use case
- **🔄 Plugin Management**: Consistent build lifecycle across all modules
- **🐳 Jib Integration**: Unified container image building across all implementations

## 📊 Project Statistics

- **Java Version**: 25
- **Maven Modules**: 11 modules (core, bom, 9 implementations)
- **Web Frameworks**: 9 different implementations with standardized APIs
- **Container Support**: Jib configuration for all implementations
- **Test Coverage**: Comprehensive E2E tests with Playwright
- **Standardized Endpoints**: `/jvm/inspect` and `/jvm/inspect.json` across all implementations
- **PORT Support**: All implementations support dynamic port configuration

## 🤝 Contributing

This project serves as a comprehensive reference for:

- Modern Java development practices (Java 25 features)
- Maven multi-module project organization and BOM patterns
- JVM introspection and performance analysis techniques
- Framework comparison and standardization across implementations
- End-to-end testing with Playwright
- Containerization best practices with Jib
- Environment variable configuration patterns

## 📚 Related Documentation

- [e2e-tests/README.md](e2e-tests/README.md) - E2E testing guide and implementation details

## 📄 License

This project is available under MIT open source licensing terms. See the project repository for specific licensing details.
