#!/bin/bash

# Test all ShowMyJVM implementations sequentially
# This script starts each implementation, runs tests, then stops it

set -e

IMPLEMENTATIONS=(
  "tomcat"
  "spring-boot"
  "quarkus"
  "micronaut"
  "helidon"
  "helidon-mp"
  "javalin"
  "ratpack"
  "sparkjava"
)

# Function to get Maven command for each implementation
get_maven_command() {
  case "$1" in
    tomcat) echo "cargo:run" ;;
    spring-boot) echo "spring-boot:run" ;;
    quarkus) echo "quarkus:run" ;;
    micronaut) echo "mn:run" ;;
    helidon) echo "exec:exec" ;;
    helidon-mp) echo "exec:exec" ;;
    javalin) echo "exec:exec" ;;
    ratpack) echo "exec:exec" ;;
    sparkjava) echo "exec:exec" ;;
    *) echo "exec:exec" ;;
  esac
}

# Function to kill all processes on port 8080
kill_port_8080() {
  echo "🔍 Checking for processes on port 8080..."
  PID=$(lsof -ti :8080) || true
  if [ -n "$PID" ]; then
    echo "🛑 Killing process(es) on port 8080: $PID"
    kill -9 $PID 2>/dev/null || true
    sleep 2
  fi
}

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
MAVEN_CMD="$PROJECT_ROOT/mvnw"

# Check if mvnw exists, otherwise fall back to mvn
if [ ! -f "$MAVEN_CMD" ]; then
  MAVEN_CMD="mvn"
fi

echo "🚀 Testing all ShowMyJVM implementations..."
echo "Project root: $PROJECT_ROOT"
echo "Maven command: $MAVEN_CMD"
echo ""

# Clean up any existing processes on port 8080
kill_port_8080

FAILED_TESTS=()
PASSED_TESTS=()

for impl in "${IMPLEMENTATIONS[@]}"; do
  echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
  echo "📦 Testing: $impl"
  echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
  
  # Start the application in the background
  echo "▶️  Starting $impl..."
  cd "$PROJECT_ROOT"
  MAVEN_GOAL=$(get_maven_command "$impl")
  $MAVEN_CMD "$MAVEN_GOAL" -pl "$impl" > "/tmp/showmyjvm-$impl.log" 2>&1 &
  APP_PID=$!
  
  # Wait for the application to start (check if port 8080 is listening)
  echo "⏳ Waiting for $impl to start on port 8080..."
  for i in {1..30}; do
    if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null 2>&1; then
      echo "✅ $impl is ready!"
      break
    fi
    if [ $i -eq 30 ]; then
      echo "❌ $impl failed to start within 30 seconds"
      kill -9 $APP_PID 2>/dev/null || true
      kill_port_8080
      FAILED_TESTS+=("$impl (startup failed)")
      continue 2
    fi
    sleep 1
  done
  
  # Run the tests
  echo "🧪 Running tests for $impl..."
  cd "$SCRIPT_DIR"
  if npx playwright test --project="$impl" --reporter=list; then
    echo "✅ Tests passed for $impl"
    PASSED_TESTS+=("$impl")
  else
    echo "❌ Tests failed for $impl"
    FAILED_TESTS+=("$impl")
  fi
  
  # Stop the application
  echo "⏹️  Stopping $impl..."
  kill -9 $APP_PID 2>/dev/null || true
  kill_port_8080
  
  # Wait for port to be free
  echo "⏳ Waiting for port 8080 to be free..."
  for i in {1..10}; do
    if ! lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null 2>&1; then
      echo "✅ Port 8080 is free"
      break
    fi
    if [ $i -eq 10 ]; then
      echo "⚠️  Port 8080 still in use, forcing cleanup..."
      kill_port_8080
    fi
    sleep 1
  done
  
  echo ""
done

echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📊 Test Summary"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

if [ ${#PASSED_TESTS[@]} -gt 0 ]; then
  echo "✅ Passed (${#PASSED_TESTS[@]}):"
  for impl in "${PASSED_TESTS[@]}"; do
    echo "   - $impl"
  done
  echo ""
fi

if [ ${#FAILED_TESTS[@]} -gt 0 ]; then
  echo "❌ Failed (${#FAILED_TESTS[@]}):"
  for impl in "${FAILED_TESTS[@]}"; do
    echo "   - $impl"
  done
  echo ""
  # Final cleanup
  kill_port_8080
  exit 1
else
  echo "🎉 All tests passed!"
  # Final cleanup
  kill_port_8080
  exit 0
fi
