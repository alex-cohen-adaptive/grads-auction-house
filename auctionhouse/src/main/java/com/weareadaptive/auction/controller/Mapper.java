package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.controller.dto.AuctionResponse;
import com.weareadaptive.auction.controller.dto.UserResponse;
import com.weareadaptive.auction.model.AuctionLot;
import com.weareadaptive.auction.model.User;

public class Mapper {
    private Mapper() {
    }

    public static UserResponse map(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getOrganisation());
    }

    public static AuctionResponse map(AuctionLot auction) {
        return new AuctionResponse(auction.getId(),auction.getOwner(),auction.getSymbol(),
                auction.getMinPrice(), auction.getQuantity());
    }

}
