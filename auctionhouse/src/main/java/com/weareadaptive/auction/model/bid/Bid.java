package com.weareadaptive.auction.model.bid;

import com.weareadaptive.auction.exception.business.BusinessException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "price")
  private double price;

  @Column(name = "state")
  private String state;

  @Column(name = "username")
  private String username;

  @Column(name = "auction_id")
  private int auctionId;

  @Column(name = "win_quantity")
  private int winQuantity;

  public int getQuantity() {
    return quantity;
  }

  public String getAuctionUser() {
    return username;
  }

  public double getPrice() {
    return price;
  }

  public int getWinQuantity() {
    return winQuantity;
  }

  public String getState() {
    return state;
  }

  public void lost() {
    if (state.equals(State.PENDING.toString())) {
      throw new BusinessException("Must be a pending bid");
    }
    state = State.LOST.toString();
  }

  public void win(int winQuantity) {
    if (state.equals(State.PENDING.toString())) {
      throw new BusinessException("Must be a pending bid");
    }

    if (quantity < winQuantity) {
      throw new BusinessException("winQuantity must be lower or equal to to the bid quantity");
    }

    state = State.WIN.toString();
    this.winQuantity = winQuantity;
  }

  @Override
  public String toString() {
    return "user=" + username
        + ", price=" + price
        + ", quantity=" + quantity;
  }

  public enum State {
    PENDING,
    LOST,
    WIN
  }
}
