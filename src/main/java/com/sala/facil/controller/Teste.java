package com.sala.facil.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Teste {

    @GetMapping
    public String isAlivve(){
        return "Aplicação no ar";
    }

    @PutMapping
    public String isOkPut(){
        return "Put ok";
    }
    
}
