package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.dto.request.CreateAuctionRequest;
import com.weareadaptive.auction.dto.request.CreateBidRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.weareadaptive.auction.TestData.ADMIN_AUTH_TOKEN;
import static com.weareadaptive.auction.TestData.USER_AUTH_TOKEN;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuctionControllerTest extends TestController {

  public static final String END_POINT= "/auction";

  @BeforeEach
  public void initialiseRestAssuredMockMvcStandalone() {
    uri = "http://localhost:" + port + END_POINT;
  }

  //invalif token
  //create a bid
  //create a bid owner cannot place bid
  //close auction valid
  //close auction already closed
  //close auction onlt owner can do this
  //close auction summary
  //closed auction summary auction open


  @DisplayName("create should return a bad request when the auction already exists")
  @Test
  public void create_shouldReturn_400_IfAuctionExist() {
    var createRequest = new CreateAuctionRequest(
      testData.auction1().getSymbol(),
      (float) testData.auction1().getMinPrice(),
      testData.auction1().getQuantity()
    );

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .contentType(ContentType.JSON)
      .body(createRequest)
      .when()
      .post("/create")
      .then()
      .statusCode(BAD_REQUEST.value())
      .body("message", containsString("already exist"));
    //@formatter:on
  }

  @DisplayName("create should create and return the new auction")
  @Test
  public void create_shouldReturnAuctionIfCreated() {
    var name = faker.stock().nyseSymbol();

    var createRequest = new CreateAuctionRequest(
      name,
      (float) testData.auction1().getMinPrice(),
      testData.auction1().getQuantity()
    );


    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .contentType(ContentType.JSON)
      .body(createRequest)
      .when()
      .post("/create")
      .then()
      .statusCode(HttpStatus.CREATED.value())
      .body("id", greaterThan(0))
      .body("symbol", equalTo(createRequest.symbol()))
      .body("minPrice", equalTo(createRequest.minPrice()))
      .body("quantity", equalTo(createRequest.quantity())
      );
    //@formatter:on
  }


  @DisplayName("get should when return 404 when user doesn't")
  @Test
  public void get_shouldReturn_404_WhenAuctionNotFound() {
    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .pathParam("id", INVALID_ID)
      .when()
      .get("/get/{id}")
      .then()
      .statusCode(NOT_FOUND.value());
    //@formatter:on
  }


  @DisplayName("get should when return 400 if invalid id")
  @Test
  public void get_shouldReturn_400_IfInvalidId() {
    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .pathParam("id", INVALID_ID)
      .when()
      .get("/auctions/{id}")
      .then()
      .statusCode(NOT_FOUND.value());
    //@formatter:on
  }

  @DisplayName("get should return an auction")
  @Test
  public void get_shouldReturnAuctionIfExists() {
    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .pathParam("id", testData.auction1().getId())
      .when()
      .get("/get/{id}")
      .then()
      .statusCode(HttpStatus.OK.value())
      .body("id", equalTo(testData.auction1().getId()))
      .body("symbol", equalTo(testData.auction1().getSymbol()))
      .body("quantity", equalTo(testData.auction1().getQuantity()));
//    todo float error

//      .body("minPrice", equalTo((double) testData.auction1().getMinPrice()));
    //@formatter:on
  }

  @DisplayName("get should return a list of auctions")
  @Test
  public void getAllAuctions_shouldReturnIfExists() {
    var find1 = format("find { it.id == %s }.", testData.auction1().getId());
    var find2 = format("find { it.id == %s }.", testData.auction2().getId());

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .when()
      .get("/get-all")
      .then()
      .statusCode(HttpStatus.OK.value())
      .body(find1 + "id", equalTo(testData.auction1().getId()))
      .body(find1 + "symbol", equalTo(testData.auction1().getSymbol()))
      .body(find1 + "quantity", equalTo(testData.auction1().getQuantity()))
//      .body(find1 + "minPrice", equalTo(testData.auction1().getMinPrice()))
      .body(find2 + "id", equalTo(testData.auction2().getId()))
      .body(find2 + "symbol", equalTo(testData.auction2().getSymbol()))
      .body(find2 + "quantity", equalTo(testData.auction2().getQuantity()));
//      .body(find2 + "minPrice", equalTo(testData.auction2().getMinPrice()));
    //@formatter:on
  }

  @DisplayName("should be reg user to access api")
  @Test
  public void action_shouldReturn_403_WhenNotAUser() {
    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, ADMIN_AUTH_TOKEN)
      .pathParam("id", INVALID_ID)
      .when()
      .get("/get/{id}")
      .then()
      .statusCode(FORBIDDEN.value());
    //@formatter:on
  }

  @DisplayName("should create a bid if valid")
  @Test
  void bid_shouldCreateBid() {
    var bid = testData.bid2();
    var user = testData.user2();
    var auctionId = testData.auction1().getId();
    var createRequest = new CreateBidRequest(
      bid.getQuantity(),
      bid.getPrice()
    );

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user2Token())
      .pathParam("id", auctionId)
      .contentType(ContentType.JSON)
      .body(createRequest)
      .when()
      .post("/{id}/bid/create")
      .then()
      .statusCode(CREATED.value());
    //.body("message", containsString("already exist"));
    //@formatter:on
  }

  /*  @Test
    void bid_shouldReturn_404_IfInvalidAuctionId() {
    }
*/
  @Test
  void bid_shouldReturn_403_IfOwner() {
    var bid = testData.bid1();
    var user = testData.user4();
    var auctionId = testData.auction1().getId();
    var createRequest = new CreateBidRequest(
      user,
      bid.getQuantity(),
      bid.getPrice()
    );

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user4Token())
      .contentType(ContentType.JSON)
      .body(createRequest)
      .pathParam("auctionId", auctionId)
      .when()
      .post("/auctions/{auctionId}")
      .then()
      .statusCode(UNAUTHORIZED.value());
    //.body("message", containsString("already exist"));
    //@formatter:on
  }

  @Test
  void getAllBids_shouldReturnIfExists() {
    var bid = testData.bid1();
    var user = testData.user4();
    var auctionId = testData.auction1().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user4Token())
      .contentType(ContentType.JSON)
      .pathParam("auctionId", auctionId)
      .when()
      .get("/auctions/{auctionId}")
      .then()
      .statusCode(OK.value());
    //.body("message", containsString("already exist"));
    //@formatter:on
  }

  @Test
  void getAllBids_shouldReturn_403_IfNotOwner() {
    var bid = testData.bid1();
    var user = testData.user1();
    var auctionId = testData.auction1().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .contentType(ContentType.JSON)
      .pathParam("auctionId", auctionId)
      .when()
      .get("/auctions/{auctionId}")
      .then()
      .statusCode(FORBIDDEN.value());
    //.body("message", containsString("already exist"));
  }

  @Test
  void getAllBids_shouldReturn_404_IfNoBids() {
    var bid = testData.bid1();
    var user = testData.user1();
    var auctionId = testData.auction1().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .contentType(ContentType.JSON)
      .pathParam("auctionId", auctionId)
      .when()
      .get("/auctions/{auctionId}")
      .then()
      .statusCode(FORBIDDEN.value());
    //.body("message", containsString("already exist"));
  }


  @Test
  void close_shouldClose() {
    var bid = testData.bid1();
    var user = testData.user1();
    var auctionId = testData.auction1().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .contentType(ContentType.JSON)
      .pathParam("auctionId", auctionId)
      .when()
      .get("/auctions/{auctionId}/close")
      .then()
      .statusCode(FORBIDDEN.value());
  }


  @Test
  void close_shouldReturn_403_IfNotOwner() {
  }

  @Test
  void close_shouldReturn_403_IfAlreadyClosed() {
  }

  @Test
  void close_shouldReturn_400_IfInvalidId() {
  }

  @Test
  void close_shouldReturn_404_IfAuctionNotFound() {
  }

  @Test
  void getAuctionSummary() {
  }
}