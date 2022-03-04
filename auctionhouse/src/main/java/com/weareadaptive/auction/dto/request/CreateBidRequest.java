package com.weareadaptive.auction.dto.request;

import com.weareadaptive.auction.model.user.User;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record CreateBidRequest(
        @NotNull
        @Positive
        @Min(1)
        int quantity,

        @NotNull
        @Positive
        @DecimalMin("0.1")
        double price
) {

}
