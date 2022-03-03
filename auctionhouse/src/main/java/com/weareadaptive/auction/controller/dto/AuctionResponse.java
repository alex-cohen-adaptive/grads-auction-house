package com.weareadaptive.auction.controller.dto;

import com.weareadaptive.auction.model.User;

import javax.validation.constraints.*;

public record AuctionResponse (
        int id,
        User owner,
        String symbol,
        double minPrice,
        int quantity
) {}

