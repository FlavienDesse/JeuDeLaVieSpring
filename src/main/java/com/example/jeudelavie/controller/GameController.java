package com.example.jeudelavie.controller;


import com.example.jeudelavie.model.Game;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GameController {


    @PostMapping("/game")
    public int[][] getTestData(Game model) {
        return model.calculateNextStep();

    }

}
