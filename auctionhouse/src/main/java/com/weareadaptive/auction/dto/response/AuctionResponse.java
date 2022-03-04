package com.weareadaptive.auction.dto.response;

import com.weareadaptive.auction.model.user.User;

public record AuctionResponse (
        int id,
        User owner,
        String symbol,
        double minPrice,
        int quantity
) {}

