package com.weareadaptive.auction.dto.response;

import com.weareadaptive.auction.model.user.User;

public record BidResponse(User user, int quantity, double price) {
}
