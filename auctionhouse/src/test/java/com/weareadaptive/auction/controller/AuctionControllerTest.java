package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.dto.request.CreateAuctionRequest;
import com.weareadaptive.auction.dto.request.CreateBidRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.weareadaptive.auction.TestData.ADMIN_AUTH_TOKEN;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuctionControllerTest extends TestController {

  public static final String END_POINT = "/auction";

  @BeforeEach
  public void initialiseRestAssuredMockMvcStandalone() {
    uri = "http://localhost:" + port + END_POINT;
  }


  //Bid
  //valid
  //create a bid with negative quantity
  //create bid with negative amount
  //create a bid owner cannot place bid
  //close auction
  //close auction valid
  //close the auction not owner
  //close the auction already closed
  //close auction summary
  //close auction summary valid
  //close auction is open


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


  @DisplayName("Should return bad request if the user provides a negative/0 price for an auction ")
  @ParameterizedTest()
  @ValueSource(strings = {" ", "", "     " })
  public void create_shouldReturn_400_IfInvalidSymbol(String argument) {
    var name = faker.stock().nyseSymbol();

    var createRequest = new CreateAuctionRequest(
      argument,
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
      .statusCode(BAD_REQUEST.value());

  }


  @DisplayName("Should return bad request if the user provides a negative/0 price for an auction ")
  @ParameterizedTest()
  @ValueSource(floats = {-0.1f, 0.0f, -1f})
  public void create_shouldReturn_400_IfInvalidPrice(float argument) {
    var name = faker.stock().nyseSymbol();

    var createRequest = new CreateAuctionRequest(
      name,
      argument,
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
      .statusCode(BAD_REQUEST.value());

  }

  @DisplayName("Should return bad request if the user provides a negative/0 quantity for the auction")
  @ParameterizedTest()
  @ValueSource(ints = {0, -1})
  public void create_shouldReturn_400_IfNegativeQuantity(int argument) {
    var name = faker.stock().nyseSymbol();
    var createRequest = new CreateAuctionRequest(
      name,
      (float) testData.auction1().getMinPrice(),
      argument
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
      .statusCode(BAD_REQUEST.value());

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
      .get("/get/{id}")
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
    var auctionId = testData.auction2().getId();
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
      .statusCode(CREATED.value())
      .body("quantity", equalTo(bid.getQuantity()));
//      .body("price", equalTo(bid.getPrice()));
//      .body("message", containsString("already exist"));
    //@formatter:on
  }


  @DisplayName("should create a bid if valid")
  @Test
  void bid_shouldReturn_400_IfInvalidBid() {
    var bid = testData.bid2();
    var user = testData.user2();
    var auctionId = testData.auction1().getId();
    var createRequest = new CreateBidRequest(
      bid.getQuantity(),
      1);

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
      .statusCode(BAD_REQUEST.value());
//      .body("message", containsString("already exist"));
    //@formatter:on
  }


  @Test
  void bid_shouldReturn_403_IfOwner() {
    var bid = testData.bid1();
    var user = testData.user4();
    var auctionId = testData.auction1().getId();
    var createRequest = new CreateBidRequest(
      bid.getQuantity(),
      bid.getPrice()
    );

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user4Token())
      .contentType(ContentType.JSON)
      .body(createRequest)
      .pathParam("id", auctionId)
      .when()
      .post("/{id}/bid/create")
      .then()
      .statusCode(FORBIDDEN.value())
      .body("message", containsString("cannot bid"));
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
      .pathParam("id", auctionId)
      .when()
      .get("{id}/bid/get-all")
      .then()
      .statusCode(OK.value());
//    todo body return bids
//      .body("message", containsString("already exist"));
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
      .pathParam("id", auctionId)
      .when()
      .get("{id}/bid/get-all")
      .then()
      .statusCode(FORBIDDEN.value())
      .body("message", containsString("get all bids"));
  }

/*  @Test
  void getAllBids_shouldReturn_404_IfNoBids() {
    var bid = testData.bid1();
    var user = testData.user1();
    var auctionId = testData.auction1().getId();



    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user4Token())
      .contentType(ContentType.JSON)
      .pathParam("id", auctionId)
      .when()
      .get("{id}/bid/get-all")
      .then()
      .statusCode(FORBIDDEN.value());
    //.body("message", containsString("already exist"));
  }*/

  @Test
  void close_shouldClose() {
    var bid = testData.bid1();
    var user = testData.user1();
    var auctionId = testData.auction1().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user4Token())
      .contentType(ContentType.JSON)
      .pathParam("id", auctionId)
      .when()
      .post("/{id}/close")
      .then()
      .statusCode(OK.value());
  }


  @Test
  void close_shouldReturn_403_IfNotOwner() {
    var auctionId = testData.auction1().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user1Token())
      .contentType(ContentType.JSON)
      .pathParam("id", auctionId)
      .when()
      .post("/{id}/close")
      .then()
      .statusCode(FORBIDDEN.value())
      .body("message", containsString("close"));
  }

  @Test
  void close_shouldReturn_403_IfAlreadyClosed() {
    var auctionId = testData.auction4().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user4Token())
      .contentType(ContentType.JSON)
      .pathParam("id", auctionId)
      .when()
      .post("/{id}/close")
      .then()
      .statusCode(FORBIDDEN.value());
  }


  @Test
  void getAuctionSummary_shouldGetSummary() {
    var auctionId = testData.auction4().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user4Token())
      .contentType(ContentType.JSON)
      .pathParam("id", auctionId)
      .when()
      .get("/{id}/summary")
      .then()
      .statusCode(OK.value());
  }

  @Test
  void getAuctionSummary_shouldReturn_403_IfAuctionOpen() {
    var auctionId = testData.auction2().getId();

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.user4Token())
      .contentType(ContentType.JSON)
      .pathParam("id", auctionId)
      .when()
      .get("/{id}/summary")
      .then()
      .statusCode(FORBIDDEN.value());
  }
}