package com.weareadaptive.auction.model.bid;

import com.weareadaptive.auction.exception.business.BusinessException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.weareadaptive.auction.model.auction.Auction;
import com.weareadaptive.auction.model.user.AuctionUser;
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

  private String auctionUser;

  private int quantity;

  private double price;

  private State state;

  @ManyToOne
  private Auction auction;

  private int winQuantity;

  public int getQuantity() {
    return quantity;
  }

  public String getAuctionUser() {
    return auctionUser;
  }

  public double getPrice() {
    return price;
  }

  public int getWinQuantity() {
    return winQuantity;
  }

  public State getState() {
    return state;
  }

 /* public void lost() {
    if (state != State.PENDING) {
      throw new BusinessException("Must be a pending bid");
    }
    state = State.LOST;
  }*/
/*

  public void win(int winQuantity) {
    if (state != State.PENDING) {
      throw new BusinessException("Must be a pending bid");
    }

    if (quantity < winQuantity) {
      throw new BusinessException("winQuantity must be lower or equal to to the bid quantity");
    }

    state = State.WIN;
    this.winQuantity = winQuantity;
  }
*/

  @Override
  public String toString() {
    return "user=" + auctionUser
        + ", price=" + price
        + ", quantity=" + quantity;
  }

  public enum State {
    PENDING,
    LOST,
    WIN
  }
}
