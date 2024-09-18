package com.Foody.Foody.Food;

import java.time.LocalDate;

public record rFoodRequest(
        Integer userId,
        Long barcode,
        Float gramsEaten,

        LocalDate Date
) {
}
