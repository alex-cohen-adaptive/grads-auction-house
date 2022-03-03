package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.dto.request.CreateAuctionRequest;
import com.weareadaptive.auction.dto.response.AuctionResponse;
import com.weareadaptive.auction.dto.response.BidResponse;
import com.weareadaptive.auction.dto.request.CreateBidRequest;
import com.weareadaptive.auction.service.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.util.stream.Stream;

import static com.weareadaptive.auction.controller.Mapper.map;
import static org.apache.coyote.http11.Constants.a;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/auctions")
@PreAuthorize("hasRole('ROLE_USER')")
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuctionResponse createAuction(@RequestBody @Valid
                        CreateAuctionRequest createAuctionRequest) {
      return map(
              auctionService.create(
                createAuctionRequest.id(),
                createAuctionRequest.symbol(),
                createAuctionRequest.quantity(),
                createAuctionRequest.minPrice()
        ));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuctionResponse getAuction(@PathVariable @Valid int id ) {
        return map(
                auctionService.get(id)
        );
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Stream<AuctionResponse> getAllAuctions() {
        return auctionService.getAllAuctions()
                .map(Mapper::map);
    }

    //bid based on auction id
    @PostMapping("{auctionId}/bid")
    @ResponseStatus(HttpStatus.CREATED)
    public BidResponse bidAuction(@PathVariable  @Valid int auctionId,
                                  @RequestBody @Valid CreateBidRequest createBidRequest)
    {
        return null;
    }

    @GetMapping("{auctionId}")
    @ResponseStatus(HttpStatus.OK)
    public BidResponse getAllBids(@PathVariable @Valid int auctionId) {
        return null;
    }

    @PostMapping("{id}/close")
    @ResponseStatus(HttpStatus.OK)
    public BidResponse closeAuction(@PathVariable @Valid int id) {
        return null;
    }


    @GetMapping("{id}/summary")
    @ResponseStatus(HttpStatus.OK)
    public BidResponse getAuctionSummary(@PathVariable @Valid int id) {
        return null;
    }


}
