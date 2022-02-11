package com.example.pizzamaker.model.dto;

import com.example.pizzamaker.model.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private int tableId;

    private boolean inProcess;

    private int quantity;

    private float amount;

    private List<Product> products;
}
