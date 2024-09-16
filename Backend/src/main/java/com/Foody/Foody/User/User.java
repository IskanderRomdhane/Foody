package com.Foody.Foody.User;

import com.Foody.Foody.Biometrics.Biometrics;
import com.Foody.Foody.Food.Food;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;
    private String lastname;

    // Properly mapped one-to-many relationship
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Biometrics> biometrics;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Food> eatenFood;
}

