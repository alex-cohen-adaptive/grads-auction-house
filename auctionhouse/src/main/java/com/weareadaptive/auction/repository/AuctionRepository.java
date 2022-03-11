package com.weareadaptive.auction.repository;

import com.weareadaptive.auction.model.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
  boolean existsBySymbol(String symbol);

  Optional<Auction> getBySymbol(String symbol);

  Optional<Auction> getById(int id);
}
