package com.Foody.Foody.Food;

import com.Foody.Foody.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/food/")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final JwtService jwtService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add-meal")
    public ResponseEntity<?> addMeal(
            @RequestBody rFoodRequest request
    ) {
        return foodService.addMeal(request);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-todays-meals")
    public ResponseEntity<?> getTodayMeals(
            @RequestBody rFoodRequest request
    ) {
        return foodService.getTodayMeals(request);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-todays-total")
    public ResponseEntity<?> totalNutriments(
            @RequestBody rFoodRequest request
    ) {
        return foodService.totalNutriments(request);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-todays-needed-nutriments")
    public ResponseEntity<?> getTodayNeededNutriments() {
        String email = jwtService.getCurrentUserEmail();
        return foodService.getTodayNeededNutriments(email);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-food-by-name")
    public ResponseEntity<?> getFoodByName(
            @RequestBody rFoodRequest rFood
    ) {
        return foodService.getFoodByName(rFood);
    }
}
