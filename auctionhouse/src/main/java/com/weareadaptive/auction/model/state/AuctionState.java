package com.weareadaptive.auction.model.state;

import java.util.List;

import com.weareadaptive.auction.model.Auction;
import com.weareadaptive.auction.model.User;
import com.weareadaptive.auction.model.bid.LostBid;
import com.weareadaptive.auction.model.bid.WonBid;
import org.springframework.stereotype.Component;

@Component
public class AuctionState extends State<Auction> {
  public List<LostBid> findLostBids(User user) {
    if (user == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
    return stream()
        .filter(auction -> Auction.Status.CLOSED == auction.getStatus())
        .flatMap(auction -> auction.getLostBids(user).stream()
            .map(b -> new LostBid(
                auction.getId(),
                auction.getSymbol(),
                b.getQuantity(),
                b.getPrice()))
        ).toList();
  }

  public List<WonBid> findWonBids(User user) {
    if (user == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
    return stream()
        .filter(auction -> Auction.Status.CLOSED == auction.getStatus())
        .flatMap(auction -> auction.getWonBids(user).stream()
            .map(winningBod -> new WonBid(
                auction.getId(),
                auction.getSymbol(),
                winningBod.quantity(),
                winningBod.originalBid().getQuantity(),
                winningBod.originalBid().getPrice()))
        ).toList();
  }
}
