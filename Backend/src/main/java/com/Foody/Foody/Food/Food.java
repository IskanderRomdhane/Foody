package com.Foody.Foody.Food;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "Food")
@Getter
@Setter
public class Food {
    @Id
    private Integer barcode ;

    private String name;

    private Float calories;

    private Float protein ;

    private Float fat;
}
