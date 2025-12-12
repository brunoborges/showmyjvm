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
    quarkus) echo "quarkus:dev" ;;
    micronaut) echo "mn:run" ;;
    helidon) echo "exec:exec" ;;
    helidon-mp) echo "exec:exec" ;;
    javalin) echo "exec:exec" ;;
    ratpack) echo "exec:exec" ;;
    sparkjava) echo "exec:exec" ;;
    *) echo "exec:exec" ;;
  esac
}

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

echo "🚀 Testing all ShowMyJVM implementations..."
echo "Project root: $PROJECT_ROOT"
echo ""

FAILED_TESTS=()
PASSED_TESTS=()

for impl in "${IMPLEMENTATIONS[@]}"; do
  echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
  echo "📦 Testing: $impl"
  echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
  
  # Start the application in the background
  echo "▶️  Starting $impl..."
  cd "$PROJECT_ROOT"
  MAVEN_CMD=$(get_maven_command "$impl")
  mvn "$MAVEN_CMD" -pl "$impl" > "/tmp/showmyjvm-$impl.log" 2>&1 &
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
      kill $APP_PID 2>/dev/null || true
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
  kill $APP_PID 2>/dev/null || true
  
  # Wait for port to be free
  for i in {1..10}; do
    if ! lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null 2>&1; then
      break
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
  exit 1
else
  echo "🎉 All tests passed!"
  exit 0
fi
