package com.Foody.Foody.Food;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/food/")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    @PostMapping("/add-meal")
    public ResponseEntity<?> addMeal(
            @RequestBody rFoodRequest request
    ) {
        return foodService.addMeal(request);
    }

    @GetMapping("/get-todays-meals")
    public ResponseEntity<?> getTodayMeals(
            @RequestBody rFoodRequest request
    ) {
        return foodService.getTodayMeals(request);
    }

    @GetMapping("/get-todays-total")
    public ResponseEntity<?> totalNutriments(
            @RequestBody rFoodRequest request
    ) {
        return foodService.totalNutriments(request);
    }
}
