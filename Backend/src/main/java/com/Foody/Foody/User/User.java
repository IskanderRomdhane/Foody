package com.Foody.Foody.User;

import com.Foody.Foody.Biometrics.Biometrics;
import com.Foody.Foody.Food.Food;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Biometrics> biometrics;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Food> eatenFood;
}

