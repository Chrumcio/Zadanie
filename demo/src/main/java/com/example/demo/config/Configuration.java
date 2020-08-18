package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    /*
    Stworzenie bean-a model mappera w celu wykorzystania go do bezpiecznego mapowania obiektów
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
