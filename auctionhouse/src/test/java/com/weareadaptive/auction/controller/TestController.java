package com.weareadaptive.auction.controller;

import com.github.javafaker.Faker;
import com.weareadaptive.auction.TestData;
import com.weareadaptive.auction.service.AuctionLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class TestController {
    public static final int INVALID_ID = 99999;
    @Autowired
    AuctionLotService auctionLotService;
    @Autowired
    TestData testData;
    @LocalServerPort
    int port;
    String uri;
    final Faker faker = new Faker();

}
