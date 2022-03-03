package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.controller.dto.AuctionResponse;
import com.weareadaptive.auction.controller.dto.BidResponse;
import com.weareadaptive.auction.controller.dto.CreateUserRequest;
import com.weareadaptive.auction.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.weareadaptive.auction.controller.Mapper.map;

@RestController
@RequestMapping("/auctions")
@PreAuthorize("hasRole('ROLE_USER')")
public class AuctionController {
    //    1. check if authenticated w/ token
    //    2. process request
    //    3. spit out result

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuctionResponse create(@RequestBody @Valid CreateUserRequest user) {
        return null;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuctionResponse get(@Valid int id ) {
        return null;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public AuctionResponse getAllAuctions() {
        return null;
    }

    //bid based on auction id
    @PostMapping("{auctionId}/bid")
    @ResponseStatus(HttpStatus.CREATED)
    public BidResponse bid(@Valid int auctionId) {
        return null;
    }

    @GetMapping("{auctionId}")
    @ResponseStatus(HttpStatus.OK)
    public BidResponse getAllBids(@Valid int auctionId) {
        return null;
    }

    @PostMapping("{id}/close")
    @ResponseStatus(HttpStatus.OK)
    public BidResponse closeAuction(@Valid int id) {
        return null;
    }


    @GetMapping("{id}/summary")
    @ResponseStatus(HttpStatus.OK)
    public BidResponse getAuctionSummary(@Valid int id) {
        return null;
    }


}
