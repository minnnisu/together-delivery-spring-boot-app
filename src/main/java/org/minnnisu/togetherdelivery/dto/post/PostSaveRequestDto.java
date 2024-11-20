package org.minnnisu.togetherdelivery.dto.post;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSaveRequestDto {
    private String content;

    @NotBlank(message = "NoContentError")
    @Size(min = 2, max = 20, message = "TooShortOrLongContentError")
    private String restaurantName;

    @NotBlank(message = "NoCategoryCodeError")
    private String categoryCode;

    @NotNull(message = "NoDeliveryFeeError")
    @Min(value = 0, message = "TooLowDeliveryFeeError")
    @Max(value = 10000, message = "TooHighDeliveryFeeError")
    private int deliveryFee;

    @NotNull(message = "NoMinOrderFeeError")
    @Min(value = 0, message = "TooLowMinOrderFeeError")
    @Max(value = 100000, message = "TooHighMinOrderFeeError")
    private int minOrderFee;

    @NotNull(message = "NoMeetLocationError")
    private PostLocationDto meetLocation;
}
