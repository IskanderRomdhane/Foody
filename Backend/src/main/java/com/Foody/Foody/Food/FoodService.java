package com.Foody.Foody.Food;

import com.Foody.Foody.Biometrics.Biometrics;
import com.Foody.Foody.Biometrics.BiometricsRepository;
import com.Foody.Foody.User.User;
import com.Foody.Foody.User.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final UserRepository userRepository;
    private final BiometricsRepository biometricsRepository;
    private final FoodRepository foodRepository;
    public ResponseEntity<?> addMeal(rFoodRequest request) {
        try {
            Optional<User> user = userRepository.findById(request.userId());
            if (user.isPresent()) {
                String urlString = "https://world.openfoodfacts.org/api/v3/product/" + request.barcode() + ".json";
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Float grams = request.gramsEaten() / 100 ;
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.toString());

                    JsonNode productNode = rootNode.path("product");
                    JsonNode nutrimentsNode = productNode.path("nutriments");

                    String productName = productNode.path("product_name").asText("Not Available");

                    Float proteins_100g = Float.valueOf(nutrimentsNode.path("proteins_100g").asText("Not Available"));
                    Float proteins = proteins_100g * grams;

                    Float fat_100g = Float.valueOf(nutrimentsNode.path("fat_100g").asText("Not Available"));
                    Float fat = fat_100g * grams;

                    Float calories_100g = Float.valueOf(nutrimentsNode.path("energy-kcal_100g").asText("Not Available"));
                    Float calories = calories_100g * grams;

                    Float carbs_100g = Float.valueOf(nutrimentsNode.path("carbohydrates_100g").asText("Not Available"));
                    Float carbs = carbs_100g * grams;

                    Food meal = Food.builder()
                            .barcode(request.barcode())
                            .proteins_100g(proteins_100g)
                            .calories(calories)
                            .calories_100g(calories_100g)
                            .fat(fat)
                            .fat_100g(fat_100g)
                            .protein(proteins)
                            .proteins_100g(proteins_100g)
                            .carbs(carbs)
                            .carbs_100g(carbs_100g)
                            .productName(productName)
                            .cDate(LocalDate.now())
                            .build();
                    foodRepository.save(meal);

                    User foundUser = user.get();
                    List<Food> eatenFoodList = foundUser.getEatenFood();
                    if(eatenFoodList == null){
                        eatenFoodList = new ArrayList<>();
                    }
                    eatenFoodList.add(meal);
                    foundUser.setEatenFood(eatenFoodList);

                    userRepository.save(foundUser);
                    return ResponseEntity.ok(user);

                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Post request failed. Response Code: " + responseCode);
                }
            } else {return ResponseEntity.notFound().build();}
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Post request failed. Error: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getTodayMeals(rFoodRequest request) {
        Optional<User> user = userRepository.findById(request.userId());
        if(user.isPresent()) {
            try {
                User foundUser = user.get();
                List<Food> eatenFood = foundUser.getEatenFood();
                List<Food> eatenToday = new ArrayList<>();

                for (Food meal : eatenFood) {
                    if(meal.getCDate().equals(request.Date())) {
                        eatenToday.add(meal);
                    }
                }

                return ResponseEntity.ok(eatenToday);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Get request failed. Error: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User Not found");
        }
    }

    public ResponseEntity<?> totalNutriments(rFoodRequest request) {
        Optional<User> user = userRepository.findById(request.userId());
        if(user.isPresent()) {
            try {
                ResponseEntity<?> response = getTodayMeals(request);

                if(response.getStatusCode() == HttpStatus.OK && response.getBody() instanceof List<?>) {
                    List<Food> eatenFood = (List<Food>) response.getBody();

                    Float calories = 0F;
                    Float protein = 0F;
                    Float fat = 0F;
                    Float carbs = 0F;

                    for (Food meal : eatenFood) {
                        calories += meal.getCalories();
                        protein += meal.getProtein();
                        fat += meal.getFat();
                        carbs += meal.getCarbs();
                    }

                    Map<String, Float> nutrientMap = new HashMap<>();
                    nutrientMap.put("calories", calories);
                    nutrientMap.put("protein", protein);
                    nutrientMap.put("fat", fat);
                    nutrientMap.put("carbs", carbs);

                    return ResponseEntity.ok(nutrientMap);

                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No meals found for today.");
                }

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Get request failed. Error: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User Not found");
        }
    }

    public ResponseEntity<?> getTodayNeededNutriments(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            try {
                User foundUser = user.get();
                List<Biometrics> biometricsList = foundUser.getBiometrics();

                Biometrics currentBiometrics = biometricsList.get(biometricsList.size() - 1);

                rFoodRequest request = new rFoodRequest(foundUser.getId(), 0L,0F,LocalDate.now() , "");

                ResponseEntity<?> response = totalNutriments(request);
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() instanceof Map) {
                    Map<String, Float> nutrientMap = (Map<String, Float>) response.getBody();
                    Float caloriesLeft = 0F;
                    Float proteinLeft = 0F;
                    Float fatLeft = 0F;
                    Float carbsLeft = 0F;

                    for (String key : nutrientMap.keySet()) {
                        float value = nutrientMap.get(key);
                        switch (key.toLowerCase()) {
                            case "calories" -> caloriesLeft = currentBiometrics.getCaloriesIntake() - value;
                            case "protein" -> proteinLeft = currentBiometrics.getProteinIntake() - value;
                            case "carbs" -> carbsLeft = currentBiometrics.getCarbsIntake() - value;
                            case "fat" -> fatLeft = currentBiometrics.getFatsIntake() - value;
                        }
                    }
                    Map<String, Float> nutrimentsLeft = new HashMap<>();
                    nutrimentsLeft.put("calories", caloriesLeft);
                    nutrimentsLeft.put("protein", proteinLeft);
                    nutrimentsLeft.put("fat", fatLeft);
                    nutrimentsLeft.put("carbs", carbsLeft);

                    return ResponseEntity.ok(nutrimentsLeft);
                }
                else {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Get request failed. ");}
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Get request failed. Error: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    public ResponseEntity<?> getFoodByName(rFoodRequest request) {
        try {
            Optional<User> user = userRepository.findById(request.userId());
            if (user.isPresent()) {
                String BASE_URL = "https://api.spoonacular.com/recipes/complexSearch";
                String API_KEY = "57782fb00840413ebfd5b818d7f781a2";

                String encodedQuery = URLEncoder.encode(request.foodName(), "UTF-8");
                String apiUrl = BASE_URL + "?query=" + encodedQuery + "&addRecipeNutrition=true&number=1&apiKey=" + API_KEY;

                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String response = in.lines().collect(Collectors.joining());
                    in.close();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response);
                    JsonNode results = rootNode.path("results").get(0);
                    JsonNode nutritionNode = results.path("nutrition");

                    // Handle gramsEaten safely
                    Float gramsEaten = request.gramsEaten();
                    Float grams = (gramsEaten != null && gramsEaten > 0) ? gramsEaten / 100f : 1f; // Default to 1 if null or zero

                    Float proteins_100g = findNutrient(nutritionNode, "Protein");
                    Float fat_100g = findNutrient(nutritionNode, "Fat");
                    Float calories_100g = findNutrient(nutritionNode, "Calories");
                    Float carbs_100g = findNutrient(nutritionNode, "Carbohydrates");

                    // Calculate nutrients based on grams
                    Float proteins = proteins_100g * grams;
                    Float fat = fat_100g * grams;
                    Float calories = calories_100g * grams;
                    Float carbs = carbs_100g * grams;

                    Food meal = Food.builder()
                            .barcode(null)
                            .proteins_100g(proteins_100g)
                            .calories(calories)
                            .calories_100g(calories_100g)
                            .fat(fat)
                            .fat_100g(fat_100g)
                            .protein(proteins)
                            .carbs(carbs)
                            .carbs_100g(carbs_100g)
                            .productName(request.foodName())
                            .cDate(LocalDate.now())
                            .build();
                    foodRepository.save(meal);

                    User foundUser = user.get();
                    List<Food> eatenFoodList = Optional.ofNullable(foundUser.getEatenFood()).orElse(new ArrayList<>());
                    eatenFoodList.add(meal);
                    foundUser.setEatenFood(eatenFoodList);

                    userRepository.save(foundUser);

                    return ResponseEntity.ok(meal);

                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(List.of("API request failed. Response Code: " + responseCode));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of("Error occurred: " + e.getMessage()));
        }
    }

    private Float findNutrient(JsonNode nutritionNode, String nutrientName) {
        JsonNode nutrientsNode = nutritionNode.path("nutrients");

        for (int i = 0; i < nutrientsNode.size(); i++) {
            JsonNode nutrient = nutrientsNode.get(i);
            String name = nutrient.path("name").asText();

            if (name.equalsIgnoreCase(nutrientName)) {
                return nutrient.path("amount").floatValue();
            }
        }
        return 0.0f;
    }



}

