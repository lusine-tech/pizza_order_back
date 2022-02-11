package com.example.pizzamaker.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;

    private int tableId;

    private boolean inProcess;

    private int productId;

    private int quantity;

    private float amount;
}
