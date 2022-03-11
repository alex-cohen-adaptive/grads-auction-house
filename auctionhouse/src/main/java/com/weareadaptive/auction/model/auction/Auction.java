package com.weareadaptive.auction.model.auction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "auction")
public class Auction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
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

  @Column(name = "closing_summary")
  private String closingSummary;

  public boolean isClosed() {
    return this.status.equals(Status.CLOSED.toString());
  }

  public void close() {
    this.status = Status.CLOSED.toString();
  }

  public enum Status {
    CLOSED, OPENED;
  }

}
