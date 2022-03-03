package com.weareadaptive.auction.controller.dto;

import com.weareadaptive.auction.model.User;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record CreateBidRequest(
        @NotBlank
        User user,

        @NotBlank
        @Positive
        @Min(1)
        int quantity,

        @NotBlank
        @Positive
        @DecimalMin("0.1")
        double price
) {

}
