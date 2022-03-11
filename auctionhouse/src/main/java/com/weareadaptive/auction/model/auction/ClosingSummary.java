package com.weareadaptive.auction.model.auction;

import com.weareadaptive.auction.model.bid.WinningBid;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

//@Entity(name = "ClosingSummary")
public record ClosingSummary(List<WinningBid> winningBids,
                             double remainingQuantity,
                             BigDecimal totalRevenue,
                             Instant closingTime) {
  @Override
  public String toString() {
    return "ClosingSummary{"
        + "winningBids=" + winningBids
        + ", remainingQuantity=" + remainingQuantity
        + ", totalRevenue=" + totalRevenue
        + ", closingTime=" + closingTime
        + '}';
  }
}
