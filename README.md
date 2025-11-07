# Show My JVM

A comprehensive Java project that demonstrates detailed JVM (Java Virtual Machine) introspection and analysis across multiple modern web frameworks and cloud platforms. This project showcases the power of JVM runtime analysis using Java 21 features and contemporary deployment architectures.

## 🎯 Overview

Show My JVM provides a sophisticated core library that extracts comprehensive JVM runtime information and exposes it through multiple web frameworks and cloud platforms. The project demonstrates how the same core functionality can be deployed across different architectures, from traditional web servers to modern serverless functions.

**Key Features:**
- 🔍 **Deep JVM Introspection** - Comprehensive runtime analysis including memory, threading, GC, and performance metrics
- 🌐 **Multi-Framework Support** - 5 different web frameworks with consistent APIs
- ☁️ **Cloud-Native Ready** - Serverless deployments (Azure Functions, AWS Lambda) and containerization support
- 🏗️ **Modern Architecture** - Maven multi-module project with centralized dependency management
- 📊 **Rich Output Formats** - Both human-readable text and structured JSON outputs
- 🚀 **Performance Focused** - Optimized for Java 21 with advanced JVM flag introspection

## 🏛️ Project Architecture

This is a sophisticated Maven multi-module project with the following structure:

```
showmyjvm/
├── 📋 bom/                    # Bill of Materials (dependency management)
├── 🧠 core/                   # Core JVM introspection library
├── 🌸 spring-boot/           # Spring Boot 3.4.6 web application  
├── ⚡ sparkjava/             # SparkJava lightweight web framework
├── 🔬 micronaut/             # Micronaut 3.10.4 framework implementation
├── 🏃 quarkus/              # Quarkus 3.29.1 native-ready framework
├── 📡 ratpack/              # Ratpack reactive web framework
├── ☁️ azure-functions/      # Azure Functions serverless (Java 21)
├── 🅰️ aws-lambda/           # AWS Lambda serverless implementation
└── 📜 run-spring-boot.sh    # Convenience script for running Spring Boot
```

## 🔬 Core Functionality

The `core` module provides comprehensive JVM introspection capabilities using Java 21 features:

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

### 🔧 Key Classes
- `ShowJVM`: Main orchestrator for JVM data collection and analysis
- `JVMDetails`: Comprehensive data structure containing all JVM information
- `PrintFlagsFinal`: Advanced JVM flags extraction (requires `--add-opens` for full access)
- `IdentifyGC`: Sophisticated garbage collector type identification

## 🌐 Web Framework Implementations

Each framework module demonstrates identical core functionality through different web technologies:

### 🌸 Spring Boot (`/spring-boot`)
- **Version**: Spring Boot 3.4.6 with Java 21
- **Endpoints**: `/` and `/inspect` (text), `/inspect.json` (JSON)  
- **Features**: Actuator integration, Jackson pretty printing, comprehensive JSON configuration
- **Port**: 8080

### ⚡ SparkJava (`/sparkjava`)
- **Endpoints**: `/`, `/inspect` (text), `/jvminfo.json` (JSON)
- **Features**: Ultra-lightweight, minimal dependencies, fast startup
- **Port**: 8080

### 🔬 Micronaut (`/micronaut`)
- **Version**: Micronaut 3.10.4 
- **Endpoints**: `/`, `/inspect` (text), `/inspect.json` (JSON)
- **Features**: Compile-time dependency injection, reflection-free, native-ready
- **Port**: 8080

### 🏃 Quarkus (`/quarkus`)
- **Version**: Quarkus 3.29.1
- **Endpoints**: `/jvm/inspect` (text), `/jvm/inspect.json` (JSON)
- **Features**: Supersonic subatomic Java, Kubernetes-native, GraalVM support
- **Port**: 8080

### 📡 Ratpack (`/ratpack`)
- **Endpoints**: `/`, `/inspect` (text only)
- **Features**: Reactive programming model, asynchronous non-blocking
- **Port**: 8080

## ☁️ Cloud Platform Implementations

### ☁️ Azure Functions (`/azure-functions`)
- **Runtime**: Java 21 on Linux consumption plan
- **Trigger**: HTTP GET with function-level authorization  
- **Endpoint**: `/inspect`
- **Features**: Serverless execution, unique instance UUID tracking, Azure Functions runtime 4.x
- **Versions**: Azure Functions Maven Plugin 1.40.0, Java Library 3.2.2

### 🅰️ AWS Lambda (`/aws-lambda`)
- **Handler**: `io.brunoborges.showmyjvm.awslambda.Function`
- **Features**: Serverless execution optimized for AWS Lambda runtime
- **Input**: Integer (ignored)
- **Output**: Complete JVM analysis as string

## 🛠️ Prerequisites

- **Java**: 21 (LTS) - *Required for all modules*
- **Maven**: 3.9.1 or higher (enforced by project)
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

### 🎯 Option 1: Convenience Script (Recommended)
```bash
# Run Spring Boot with optimized settings
./run-spring-boot.sh
```

### ⚙️ Option 2: Maven Reactor (Fast)
```bash
# Leverages Maven reactor for dependency resolution
./mvnw spring-boot:run -pl spring-boot -am
```

### 🔧 Option 3: Traditional Approach
```bash
# Install dependencies first, then run
./mvnw install
cd spring-boot
../mvnw spring-boot:run
```

### 🌐 Running Other Frameworks

#### 🔬 Micronaut
```bash
./mvnw compile exec:java -pl micronaut -am \
  -Dexec.mainClass="io.brunoborges.showmyjvm.micronaut.Application"
```

#### ⚡ SparkJava  
```bash
./mvnw compile exec:java -pl sparkjava -am \
  -Dexec.mainClass="io.brunoborges.showmyjvm.sparkjava.SparkStart"
```

#### 🏃 Quarkus (Development Mode)
```bash
# Hot reload development mode
./mvnw quarkus:dev -pl quarkus -am

# Production mode
./mvnw compile exec:java -pl quarkus -am \
  -Dexec.mainClass="io.quarkus.runner.GeneratedMain"
```

#### 📡 Ratpack
```bash
./mvnw compile exec:java -pl ratpack -am \
  -Dexec.mainClass="io.brunoborges.showmyjvm.ratpack.RatpackStart"
```

## 🧪 Testing the Applications

Once any web application is running, test the comprehensive endpoints:

```bash
# Basic JVM information (human-readable)
curl http://localhost:8080/inspect

# Structured JSON output (where supported)
curl http://localhost:8080/inspect.json | jq '.'

# Health check for Spring Boot
curl http://localhost:8080/actuator/health

# Quarkus-specific endpoints
curl http://localhost:8080/jvm/inspect
curl http://localhost:8080/jvm/inspect.json
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

All web framework modules support modern containerization using Google Jib:

```bash
# Build optimized Docker image for Spring Boot
./mvnw clean package jib:dockerBuild -pl spring-boot -am

# Build multi-stage container for Micronaut
./mvnw clean package jib:dockerBuild -pl micronaut -am

# Run with optimized JVM settings
docker run -p 8080:8080 -e JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75" \
  showmyjvm-springboot

# Kubernetes deployment
kubectl apply -f k8s/deployment.yaml
```

## 🔒 Advanced JVM Flags Access

For complete JVM flags information, run with additional module access:

```bash
# Enable full flag introspection
./mvnw spring-boot:run -pl spring-boot -am \
  -Dspring-boot.run.jvmArguments="--add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED"

# With GraalVM native image preparation
./mvnw quarkus:build -pl quarkus -am -Pnative \
  -Dquarkus.native.additional-build-args="--add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED"
```

## ☁️ Cloud Deployment

### ☁️ Azure Functions Deployment
```bash
cd azure-functions

# Deploy to Azure (requires Azure CLI)
../mvnw azure-functions:deploy

# Local testing with Azure Functions Core Tools
func start --java
```

### 🅰️ AWS Lambda Deployment
```bash
cd aws-lambda

# Package for Lambda deployment
../mvnw clean package

# Deploy using AWS CLI
aws lambda update-function-code \
  --function-name showmyjvm \
  --zip-file fileb://target/showmyjvm-aws-lambda-1.0.0-SNAPSHOT.jar
```

### 🐙 Azure Container Instances
```bash
# Deploy Spring Boot to Azure Container Instances
az container create \
  --resource-group showmyjvm-rg \
  --name showmyjvm-spring \
  --image showmyjvm-springboot \
  --ports 8080
```

## ⭐ Key Features & Benefits

### 🏗️ **Multi-Framework Architecture**
- Demonstrates identical core functionality across 5+ modern web frameworks
- Framework-specific optimizations and JSON handling
- Consistent REST API patterns across all implementations

### ☁️ **Cloud-Native Excellence**
- Native compilation support (Quarkus, Micronaut)
- Optimized serverless deployment (Azure Functions, AWS Lambda)
- Production-ready containerization with Jib integration

### 🔍 **Advanced JVM Analysis**
- Deep runtime introspection using Java Management Extensions (JMX)
- Sophisticated garbage collector identification and performance analysis  
- Comprehensive flag extraction with proper module system access

### 👩‍💻 **Developer Experience**
- Maven reactor builds for efficient development workflow
- Centralized dependency management via Bill of Materials (BOM)
- Convenient automation scripts and comprehensive documentation

## 🏗️ Maven Multi-Module Architecture

The project demonstrates sophisticated Maven practices:

- **🏠 Parent POM**: Coordinates all modules with Maven 3.9.1+ enforcement
- **📋 BOM Module**: Centralizes dependency versions and plugin configurations (Java 21)
- **⚙️ Reactor Builds**: Ensures proper dependency resolution across modules  
- **🎯 Focused Modules**: Each framework optimized for its specific use case
- **🔄 Plugin Management**: Consistent build lifecycle across all modules

## 🔄 Dependency Management

All dependencies are carefully managed and regularly updated:

- **Spring Boot**: 3.4.6 (latest stable)
- **Quarkus**: 3.29.1 (latest LTS)
- **Micronaut**: 3.10.4 (stable release)
- **Azure Functions**: Java Library 3.2.2, Maven Plugin 1.40.0
- **Build Tools**: Jib 3.4.6, Surefire 3.5.2, Compiler 3.13.0
- **Testing**: JUnit 5.11.4, Mockito 5.20.0

## 📊 Project Statistics

- **Java Version**: 21 (LTS)
- **Maven Modules**: 9 modules 
- **Web Frameworks**: 5 different implementations
- **Cloud Platforms**: 2 serverless platforms
- **Container Support**: Jib + Docker
- **Test Coverage**: Comprehensive unit testing
- **Documentation**: Extensive README and inline docs

## 🤝 Contributing

This project serves as a comprehensive reference for:
- Modern Java development practices (Java 21 features)
- Maven multi-module project organization
- JVM introspection and performance analysis techniques
- Framework comparison and implementation patterns
- Cloud-native application development and deployment
- Containerization best practices with modern tooling

## 📄 License

This project is available under standard open source licensing terms. See the project repository for specific licensing details.
