package com.Foody.Foody.Food;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Food")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private Long barcode ;

    private String productName;

    private Float calories;
    private Float calories_100g;

    private Float protein ;
    private Float proteins_100g;

    private Float fat;
    private Float fat_100g;

    private Float carbs;
    private Float carbs_100g;

    private LocalDate cDate;
}
