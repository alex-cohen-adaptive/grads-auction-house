package com.weareadaptive.auction.dto.request;

import com.weareadaptive.auction.model.User;

import javax.validation.constraints.*;

public record CreateAuctionRequest(
        @NotBlank
        @Positive
        int id,

        @NotBlank
        User owner,

        @Pattern(regexp = "^[A-Za-z0-9.]+$")
        @Size(max = 4)
        String symbol,

        @NotBlank
        @Positive
        @DecimalMin(value = "0.1")
        double minPrice,

        @NotBlank
        @Positive
        @Min(1)
        int quantity
) {
}
