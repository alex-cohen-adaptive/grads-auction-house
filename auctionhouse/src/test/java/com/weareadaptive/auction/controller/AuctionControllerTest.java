package com.weareadaptive.auction.controller;

import com.github.javafaker.Faker;
import com.weareadaptive.auction.TestData;
import com.weareadaptive.auction.controller.dto.CreateAuctionRequest;
import com.weareadaptive.auction.controller.dto.CreateUserRequest;
import com.weareadaptive.auction.service.AuctionLotService;
import com.weareadaptive.auction.service.UserService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.weareadaptive.auction.TestData.ADMIN_AUTH_TOKEN;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuctionControllerTest extends AbstractTestController {
    @Autowired
    private AuctionLotService auctionLotService;
    @Autowired
    private TestData testData;
    @LocalServerPort
    private int port;
    private String uri;
    private final Faker faker = new Faker();


    public AuctionControllerTest() {
        super();
    }

    @DisplayName("create should return a bad request when the auction already exists")
    @Test
    public void create_shouldReturnBadRequestIfAuctionExist() {
        var createRequest = new CreateAuctionRequest(
                testData.
                testData.user1().getUsername(),
                "dasfasdf",
                testData.user1().getFirstName(),
                testData.user1().getLastName(),
                testData.user1().getOrganisation());

        //@formatter:off
        given()
                .baseUri(uri)
                .header(AUTHORIZATION, ADMIN_AUTH_TOKEN)
                .contentType(ContentType.JSON)
                .body(createRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("message", containsString("already exist"));
        //@formatter:on
    }



    @Test
    void create() {
    }

    @Test
    void get() {
    }

    @Test
    void getAllAuctions() {
    }

    @Test
    void bid() {
    }

    @Test
    void getAllBids() {
    }

    @Test
    void getAuctionSummary() {
    }
}