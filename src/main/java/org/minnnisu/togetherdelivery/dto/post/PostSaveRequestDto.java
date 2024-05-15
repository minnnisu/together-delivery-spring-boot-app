package org.minnnisu.togetherdelivery.dto.post;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSaveRequestDto {
    @NotBlank
    @Size(min = 2, max = 100, message = "TooShortOrLongContentError")
    private String content;

    @NotNull
    private String restaurantName;

    @NotBlank
    private String categoryCode;

    @NotNull
    @Min(value = 0, message = "TooLowDeliveryFeeError")
    @Max(value = 10000, message = "TooHighDeliveryFeeError")
    private int deliveryFee;

    @NotNull
    @Min(value = 0, message = "TooLowMinOrderFeeError")
    @Max(value = 100000, message = "TooHighMinOrderFeeError")
    private int minOrderFee;

    private PostLocationDto meetLocation;
}
