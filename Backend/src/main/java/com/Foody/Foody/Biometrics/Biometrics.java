package com.Foody.Foody.Biometrics;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Biometrics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Biometrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer age;
    private String gender;

    private Float weight;
    private Float height;
    private Float ibm;

    private LocalDate cDate;

    private Integer user_id;

    //Daily Intake
    private Float proteinIntake;
    private Float caloriesIntake;
    private Float carbsIntake;
    private Float fatsIntake;

    private String goal;

    private String activityLevel;
    public String getBodyType() {
        if (ibm < 18.5) {
            return "Underweight";
        } else if (ibm >= 18.5 && ibm < 24.9) {
            return "Normal weight";
        } else if (ibm >= 25 && ibm < 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }
}

