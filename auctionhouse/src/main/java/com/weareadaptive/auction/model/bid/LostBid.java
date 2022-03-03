package com.weareadaptive.auction.model.bid;

public record LostBid(int auctionLotId, String symbol, int quantity, double price) {
}
