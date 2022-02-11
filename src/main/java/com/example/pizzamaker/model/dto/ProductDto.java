package com.example.pizzamaker.model.dto;

import com.example.pizzamaker.model.Ingredient;
import lombok.*;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int id;
    private transient int ingredientId;
    private int productTypeId;
    private float price;
    private String name;
    private transient String ingredientName;
    private String imagePath;
    private String currency;

    List<Ingredient> ingredients ;
}
