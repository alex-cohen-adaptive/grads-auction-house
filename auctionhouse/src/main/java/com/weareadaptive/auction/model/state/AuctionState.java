package com.weareadaptive.auction.model.state;

import static java.lang.String.format;

import com.weareadaptive.auction.exception.auction.AuctionCreated;
import com.weareadaptive.auction.model.auction.Auction;
import com.weareadaptive.auction.model.bid.LostBid;
import com.weareadaptive.auction.model.bid.WonBid;
import com.weareadaptive.auction.model.user.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AuctionState extends State<Auction> {
  private final Map<String, Auction> auctionIndex;

  public AuctionState() {
    this.auctionIndex = new HashMap<>();
  }

  @Override
  protected void onAdd(Auction auction) {
    if (auctionIndex.containsKey(auction.getSymbol())) {
      throw new AuctionCreated(format("Auction \"%s\" already exist", auction.getSymbol()));
    }
    auctionIndex.put(auction.getSymbol(), auction);
  }

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
