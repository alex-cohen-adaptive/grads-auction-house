package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.dto.request.CreateAuctionRequest;
import com.weareadaptive.auction.dto.request.CreateBidRequest;
import com.weareadaptive.auction.dto.response.AuctionResponse;
import com.weareadaptive.auction.dto.response.BidResponse;
import com.weareadaptive.auction.model.auction.ClosingSummary;
import com.weareadaptive.auction.service.AuctionService;

import java.util.stream.Stream;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.weareadaptive.auction.controller.Mapper.map;

@RestController
@RequestMapping("/auction")
@PreAuthorize("hasRole('ROLE_USER')")
public class AuctionController {
  @Autowired
  private IAuthentication authentication;

  private final AuctionService auctionService;

  public AuctionController(AuctionService auctionService) {
    this.auctionService = auctionService;
  }

  @PostMapping("create")
  @ResponseStatus(HttpStatus.CREATED)
  public AuctionResponse createAuction(@RequestBody @Valid
                                         CreateAuctionRequest createAuctionRequest) {
    return map(
      auctionService.create(
        createAuctionRequest.symbol(),
        createAuctionRequest.quantity(),
        createAuctionRequest.minPrice()
      ));
  }

  @GetMapping("get/{id}")
  @ResponseStatus(HttpStatus.OK)
  public AuctionResponse getAuction(@PathVariable @Valid int id) {
    return map(
      auctionService.get(id)
    );
  }

  @GetMapping("get-all")
  @ResponseStatus(HttpStatus.OK)
  public Stream<AuctionResponse> getAllAuctions() {
//    todo check if empty if so then throw
    return auctionService.getAllAuctions()
      .map(Mapper::map);
  }


  //bid based on auction id
  @PostMapping("{id}/bid/create")
  @ResponseStatus(HttpStatus.CREATED)
  public BidResponse bidAuction(@PathVariable @Valid int id,
                                @RequestBody @Valid CreateBidRequest createBidRequest) {
    return map(
      auctionService.bid(
        id,
        createBidRequest.price(),
        createBidRequest.quantity()
      ));
  }

  @GetMapping("{id}/bid/get-all")
  @ResponseStatus(HttpStatus.OK)
  public Stream<BidResponse> getAllBids(@PathVariable @Valid int id) {
    return auctionService.getAllBids(id)
      .map(Mapper::map);

  }

  @PostMapping("{id}/close")
  @ResponseStatus(HttpStatus.OK)
  public ClosingSummary closeAuction(@PathVariable @Valid int id) {
    return auctionService.closeAuction(id);
  }


  @GetMapping("{id}/summary")
  @ResponseStatus(HttpStatus.OK)
  public ClosingSummary getAuctionSummary(@PathVariable @Valid int id) {
    return auctionService.getAuctionSummary(id);
  }


}
