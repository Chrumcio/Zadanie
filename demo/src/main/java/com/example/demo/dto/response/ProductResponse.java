package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/*
Klasa służąca do zabezpieczenia przepływu danych pomiędzy warstwami aplikacji użytkownikowi zostają udostępnione
tylko te pola, które chcemy.
 */

@Getter
@Setter
public class ProductResponse {
    @NotNull
    private String name;
    private int discount;

}
