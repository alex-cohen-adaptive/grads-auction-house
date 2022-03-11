package com.weareadaptive.auction.model.auction;

import com.weareadaptive.auction.model.bid.WinningBid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity(name = "ClosingSummary")
public class ClosingSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    List<WinningBid> winningBids;
    BigDecimal totalRevenue;
    Instant closingTime;
}
