package com.weareadaptive.auction.model.auction;

import static java.lang.Math.min;
import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

import com.weareadaptive.auction.model.bid.Bid;
import com.weareadaptive.auction.model.user.AuctionUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "auction")
public class Auction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name= "auction_id")
  int id;

  @Column(name = "symbol")
  private String symbol;

  @Column(name = "min_price")
  private double minPrice;

  @Column(name = "quantity")
  private int quantity;

  @ColumnDefault("OPEN")
  private String status = "OPEN";

  @Column(name = "owner")
  private String owner;

  @Column(name = "bid_id")
  private int bidId;

  @Column(name = "time_provider")
  private Instant TimeProvider;



/*  private Set<Bid> bids;
  private Status status;
  private ClosingSummary closingSummary;
  private Supplier<Instant> timeProvider;*/


  public Bid bid(AuctionUser bidder, int quantity, double price) {
/*    if (status == Status.CLOSED) {
      throw new BusinessException("Cannot close an already closed.");
    }

    if (bidder == owner) {
      throw new BusinessException("User cannot bid on his own auctions");
    }

    if (quantity < 0) {
      throw new BusinessException("quantity must be be above 0");
    }

    if (price < minPrice) {
      throw new BusinessException(format("price needs to be above %s", minPrice));
    }

    var bid = new Bid(bidder, quantity, price);
    bids.add(bid);
    return bid;*/
    return null;
  }

/*
  public void close() {
    if (status == Status.CLOSED) {
      throw new AuctionClose("Cannot close because already closed.");
    }

    status = Status.CLOSED;

    var orderedBids = bids.stream().sorted(reverseOrder(comparing(Bid::getPrice))
        .thenComparing(reverseOrder(comparingInt(Bid::getQuantity)))).toList();
    var availableQuantity = this.quantity;
    var revenue = BigDecimal.ZERO;
    var winningBids = new ArrayList<WinningBid>();

    for (Bid bid : orderedBids) {
      if (availableQuantity > 0) {
        var bidQuantity = min(availableQuantity, bid.getQuantity());

        winningBids.add(new WinningBid(bidQuantity, bid));
        bid.win(bidQuantity);
        availableQuantity -= bidQuantity;
        revenue = revenue.add(valueOf(bidQuantity).multiply(valueOf(bid.getPrice())));
      } else {
        bid.lost();
      }
    }

    closingSummary = new ClosingSummary(
        unmodifiableList(winningBids),
        this.quantity - availableQuantity,
        revenue,
        timeProvider.get());
  }*/
/*
  public int getId() {
    return id;
  }

  public Instant getTimeProvider() {
    return timeProvider.get();
  }

  public double getMinPrice() {
    return minPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setTimeProvider(Supplier<Instant> timeProvider) {
    this.timeProvider = timeProvider;
  }*/
/*

  public List<Bid> getLostBids(User user) {
    return bids
        .stream()
        .filter(bid -> bid.getUser() == user
            && closingSummary.winningBids().stream().noneMatch(wb -> wb.originalBid() == bid))
        .toList();
  }

  public List<WinningBid> getWonBids(User user) {
    return closingSummary.winningBids()
        .stream()
        .filter(b -> b.originalBid().getUser() == user)
        .toList();
  }
  public List<Bid> getBids() {
    return unmodifiableList(bids);
  }

  @Override
  public String toString() {
    return "AuctionLot{"
        + "owner=" + owner
        + ", title='" + symbol + '\''
        + ", status=" + status
        + '}';
  }
*/

  public enum Status {
    OPENED,
    CLOSED
  }


}
