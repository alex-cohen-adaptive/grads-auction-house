package com.weareadaptive.auction;

import com.github.javafaker.Faker;
import com.weareadaptive.auction.dto.request.CreateBidRequest;
import com.weareadaptive.auction.dto.request.CreateUserRequest;
import com.weareadaptive.auction.model.auction.Auction;
import com.weareadaptive.auction.model.bid.Bid;
import com.weareadaptive.auction.model.user.User;
import com.weareadaptive.auction.service.AuctionService;
import com.weareadaptive.auction.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.weareadaptive.auction.controller.Mapper.map;


@Component
public class TestData {
    public static final String PASSWORD = "mypassword";
    public static final String ADMIN_AUTH_TOKEN = "Bearer ADMIN:adminpassword";
    public static final String USER_AUTH_TOKEN = "Bearer USER:userpassword";
    public static final int MAX_SIZE = 4;
    private static int count = 1;
    private final UserService userService;
    private final AuctionService auctionService;
    public final Faker faker;

    private final List<User> users = new ArrayList<>(MAX_SIZE);

    private final List<Auction> auctions = new ArrayList<>(MAX_SIZE);

    private final List<Bid> bids = new ArrayList<>(MAX_SIZE);

    public TestData(UserService userService, AuctionService auctionService) {
        this.userService = userService;
        this.auctionService = auctionService;
        faker = new Faker();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createInitData() {
        for (int i = 0; i < MAX_SIZE; i++) {
            users.add(createRandomUser());
        }
        for (int i = 0; i < MAX_SIZE; i++) {
            auctions.add(createRandomAuction());
        }
        auctions.get(3).close();
        for (int i = 0; i < MAX_SIZE; i++) {
            bids.add(createRandomBid());
        }
    }

    public User user1() {
        return users.get(0);
    }

    public User user2() {
        return users.get(1);
    }

    public User user3() {
        return users.get(2);
    }

    public User user4() {
        return users.get(3);
    }


    public Auction auction1() {
        return auctions.get(0);
    }

    public Auction auction2() {
        return auctions.get(1);
    }

    public Auction auction3() {
        return auctions.get(2);
    }

    public Auction auction4() {
        return auctions.get(3);
    }


    public Bid bid1() {
        return bids.get(0);
    }

    public Bid bid2() {
        return bids.get(1);
    }

    public Bid bid3() {
        return bids.get(2);
    }

    public Bid bid4() {
        return bids.get(3);
    }

    public String user1Token() {
        return getToken(user1());
    }

    public String user2Token() {
        return getToken(user2());
    }

    public String user3Token() {
        return getToken(user3());
    }

    public String user4Token() {
        return getToken(user4());
    }

    public User createRandomUser() {
        var name = faker.name();
        return (
                userService.create(
                        new CreateUserRequest(
                                name.username(),
                                PASSWORD,
                                name.firstName(),
                                name.lastName(),
                                faker.company().name()
                        )));
    }

    public Auction createRandomAuction() {
        var stock = faker.stock();
        return (
                auctionService.create(
                        user4(),
                        stock.nsdqSymbol(),
                        faker.number().randomDigitNotZero(),
                        faker.number().randomDouble(1, 1, 300)));

    }

    public int getRandomIndex() {

        return (int) (Math.random() * MAX_SIZE - 1);
    }

    public Bid createRandomBid() {
        var index = getRandomIndex();
        var value = (count++) % 3;
        var priceIncrement = faker.number().randomDouble(1, 300, 600);
        var bid = new Bid(
                users.get(value),
                faker.number().numberBetween(5, 200),
                auctions.get(index).getMinPrice() + priceIncrement);
        auctions.get(value).bid(
                bid.getUser(),
                bid.getQuantity(),
                bid.getPrice());
        return bid;
    }


    public String getToken(User user) {
        return "Bearer " + user.getUsername() + ":" + PASSWORD;
    }

    public enum Stock {
        APPLE("AAPL"),
        MICROSOFT("MSFT"),
        META("FB");

        private final String symbol;

        Stock(String symbol) {
            this.symbol = symbol;
        }
    }
}
