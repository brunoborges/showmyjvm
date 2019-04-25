package com.microsoft.showmyjvm.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ShowMyJVMResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/showjvm")
          .then()
             .statusCode(200);
    }

}