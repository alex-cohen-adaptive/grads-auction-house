package com.weareadaptive.auction;

import com.github.javafaker.Faker;
import com.weareadaptive.auction.model.Auction;
import com.weareadaptive.auction.model.bid.Bid;
import com.weareadaptive.auction.model.User;
import com.weareadaptive.auction.service.AuctionService;
import com.weareadaptive.auction.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestData {
    public static final String PASSWORD = "mypassword";
    public static final String ADMIN_AUTH_TOKEN = "Bearer ADMIN:adminpassword";
    public static final String USER_AUTH_TOKEN = "Bearer USER:userpassword";
    public static final int MAX_SIZE = 4;
    private final UserService userService;
    private final AuctionService auctionService;
    private final Faker faker;

    private List<User> users = new ArrayList<>(MAX_SIZE);

    private List<Auction> auctions = new ArrayList<>(MAX_SIZE);

    private List<Bid> bids = new ArrayList<>(MAX_SIZE);

    public TestData(UserService userService, AuctionService auctionService) {
        this.userService = userService;
        this.auctionService = auctionService;
        faker = new Faker();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createInitData() {
        for (int i = 0; i < MAX_SIZE; i++) {
            users.add(createRandomUser());
            auctions.add(createRandomAuction());
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


    public Auction auction1() {return auctions.get(0);}

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
        var user = userService.create(
                name.username(),
                PASSWORD,
                name.firstName(),
                name.lastName(),
                faker.company().name()
        );
        return user;
    }

    public Auction createRandomAuction() {
        var stock = faker.stock();
        var auctionLot = auctionService.create(
                user4(),
                stock.nsdqSymbol(),
                faker.number().randomDigitNotZero(),
                faker.number().randomDouble(1, 1, 300));
        return auctionLot;

    }

    public Bid createRandomBid() {
        var index = (int) (Math.random() * MAX_SIZE - 1);
        var priceIncrement = faker.number().randomDouble(1, 1, 300);
        var bid = new Bid(
                users.get(index),
                faker.number().randomDigitNotZero(),
                auctions.get(index).getMinPrice() + priceIncrement);
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
